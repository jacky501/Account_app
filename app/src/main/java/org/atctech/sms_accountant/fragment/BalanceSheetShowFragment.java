package org.atctech.sms_accountant.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.atctech.sms_accountant.Adapter.ExpenseAdapter;
import org.atctech.sms_accountant.Inteface.ApiRequest;
import org.atctech.sms_accountant.Model.BalanceSheetModel;
import org.atctech.sms_accountant.Model.Donation;
import org.atctech.sms_accountant.Model.Expense;
import org.atctech.sms_accountant.Model.TotalExpenses;
import org.atctech.sms_accountant.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceSheetShowFragment extends Fragment {

    private ApiRequest service;
    private RecyclerView expenseRecyclerView;
    private TextView studentFee,donationFee,totalIncome,totalExpense,fromTo;


    public BalanceSheetShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balance_sheet_show, container, false);

        expenseRecyclerView = view.findViewById(R.id.expenseRecyclerView);
        studentFee = view.findViewById(R.id.studentFeeValue);
        donationFee = view.findViewById(R.id.donationFeeValue);
        totalIncome = view.findViewById(R.id.totalAmountValue);
        totalExpense = view.findViewById(R.id.totalExpense);
        fromTo = view.findViewById(R.id.fromTo);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);


        Bundle bundle = getArguments();
        String fromDate = bundle != null ? bundle.getString("DATE1") : null;
        String toDate = bundle != null ? bundle.getString("DATE2") : null;

        fromTo.setText(fromDate+" To "+toDate);

        GetStudentFee(fromDate,toDate);
        GetDonationFee(fromDate,toDate);
        GetExpenseType(fromDate,toDate);
        GetTotalExpense(fromDate,toDate);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS",Context.MODE_PRIVATE);

                int sFee = sharedPreferences.getInt("S_FEE",0);
                int dFee = sharedPreferences.getInt("D_FEE",0);

                int totalAmountFees = sFee+dFee;
                totalIncome.setText(Integer.toString(totalAmountFees));
            }
        },3000);


        return view;
    }

    private void GetStudentFee(String date1,String date2)
    {
        Call<BalanceSheetModel> balanceSheetModelCall = service.getStudentFee(date1,date2);

        balanceSheetModelCall.enqueue(new Callback<BalanceSheetModel>() {
            @Override
            public void onResponse(Call<BalanceSheetModel> call, Response<BalanceSheetModel> response) {
                if (response.isSuccessful())
                {

                   BalanceSheetModel balanceSheetModel = response.body();
                    SharedPreferences prefs =getContext().getSharedPreferences("PREFS",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putInt("S_FEE",balanceSheetModel.getTotalAmount());
                    ed.apply();
                   studentFee.setText(balanceSheetModel.getTotalAmount()+"");
                }
            }

            @Override
            public void onFailure(Call<BalanceSheetModel> call, Throwable t) {

            }
        });
    }

    private void GetDonationFee(String date1,String date2)
    {
        Call<Donation> donationCall = service.getDonationFee(date1,date2);
        donationCall.enqueue(new Callback<Donation>() {
            @Override
            public void onResponse(Call<Donation> call, Response<Donation> response) {
                if (response.isSuccessful())
                {
                    Donation donation = response.body();
                    SharedPreferences prefs =getContext().getSharedPreferences("PREFS",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putInt("D_FEE",donation.getDonationTotalAmount());
                    ed.apply();
                    donationFee.setText(donation.getDonationTotalAmount()+"");
                }
            }

            @Override
            public void onFailure(Call<Donation> call, Throwable t) {

            }
        });
    }

    private void GetExpenseType(String date1,String date2)
    {
        Call<List<Expense>> expenseCall = service.getExpenseMoney(date1,date2);

        expenseCall.enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (response.isSuccessful())
                {
                    List<Expense> expenses = response.body();
                    ExpenseAdapter adapter = new ExpenseAdapter(getContext(),expenses);
                    expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    expenseRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {

            }
        });

    }

    private void GetTotalExpense(String date1,String date2)
    {
        Call<TotalExpenses> totalExpensesCall = service.getTotalExpenses(date1,date2);
        totalExpensesCall.enqueue(new Callback<TotalExpenses>() {
            @Override
            public void onResponse(Call<TotalExpenses> call, Response<TotalExpenses> response) {
                if (response.isSuccessful())
                {
                    TotalExpenses totalExpenses = response.body();
                    totalExpense.setText(totalExpenses.getTotal()+"");
                }
            }

            @Override
            public void onFailure(Call<TotalExpenses> call, Throwable t) {

            }
        });
    }

}
