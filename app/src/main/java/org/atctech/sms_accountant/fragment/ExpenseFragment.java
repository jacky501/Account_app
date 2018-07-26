package org.atctech.sms_accountant.fragment;


import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.atctech.sms_accountant.Inteface.ApiRequest;
import org.atctech.sms_accountant.Model.ExpenseNameList;
import org.atctech.sms_accountant.R;

import java.net.ResponseCache;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    private Spinner spinner;
    private ApiRequest service;
    AlertDialog.Builder builder;
    FloatingActionButton expAddBtn;
    private Button expenseSubmitButton;
    List<ExpenseNameList> nameLists;
    private EditText expenseAmount;

    public static ExpenseFragment newInstance() {
        ExpenseFragment fragment = new ExpenseFragment();
        return fragment;
    }

    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Expense");

        spinner = view.findViewById(R.id.spinnerExpenseList);
        expAddBtn = view.findViewById(R.id.expenseAddBtn);
        expenseSubmitButton = view.findViewById(R.id.expenseSubmitButton);
        expenseAmount = view.findViewById(R.id.expenseAmount);

        builder = new AlertDialog.Builder(getContext());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLUE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        expAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddExpenseList();
            }
        });

        ExpenseListSpinner();

        expenseSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = spinner.getSelectedItemPosition();
                String exp_id = nameLists.get(position).getId();
                String amount = expenseAmount.getText().toString();

                Call<ResponseBody> responseBodyCall = service.AddExpenseAmount(exp_id,amount);

                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {

                            Snackbar snackbar = Snackbar
                                    .make(getView(), "Amount added successfully", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ExpenseListSpinner();
                                    expenseAmount.setText("");
                                }
                            });
                            snackbar.setActionTextColor(Color.YELLOW);
                            snackbar.show();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(getView(), "There was a problem", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                }
                            });
                            snackbar.setActionTextColor(Color.RED);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });



            }
        });

        return view;
    }

    public void ExpenseListSpinner()
    {
        Call<List<ExpenseNameList>> nameListCall = service.GetAllExpenseNameList();

        nameListCall.enqueue(new Callback<List<ExpenseNameList>>() {
            @Override
            public void onResponse(Call<List<ExpenseNameList>> call, Response<List<ExpenseNameList>> response) {
                if (response.isSuccessful()) {
                    nameLists = response.body();

                    String[] names;
                    if (nameLists != null) {
                        names = new String[nameLists.size()];

                        for (int i = 0; i < nameLists.size(); i++) {
                            names[i] = nameLists.get(i).getName();
                        }
                        spinner.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, names));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ExpenseNameList>> call, Throwable t) {

            }
        });
    }

    public void AddExpenseList() {
        LayoutInflater inflater = this.getLayoutInflater();

        View dview = inflater.inflate(R.layout.edit_text_dialouge_expense, null);
        final EditText edtFieldExpense = dview.findViewById(R.id.fieldExpenseList);
        final Button submitButton = dview.findViewById(R.id.expSubmitBtn);

        builder.setView(dview);
        builder.setCancelable(true);

        final AlertDialog alertDialog = builder.create();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtFieldExpense.getText().toString();

                Call<ResponseBody> responseBodyCall = service.AddExpenseName(name);
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            alertDialog.dismiss();

                            Snackbar snackbar = Snackbar
                                    .make(getView(), "Added To Expense List", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ExpenseListSpinner();
                                }
                            });
                            snackbar.setActionTextColor(Color.YELLOW);
                            snackbar.show();
                        } else {
                            alertDialog.dismiss();
                            Snackbar snackbar = Snackbar
                                    .make(getView(), "Name Already Exists", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AddExpenseList();

                                }
                            });
                            snackbar.setActionTextColor(Color.RED);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        alertDialog.show();

    }
}
