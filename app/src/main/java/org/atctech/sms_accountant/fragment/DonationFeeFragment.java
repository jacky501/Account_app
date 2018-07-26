package org.atctech.sms_accountant.fragment;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.atctech.sms_accountant.Inteface.ApiRequest;
import org.atctech.sms_accountant.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonationFeeFragment extends Fragment {

    ApiRequest service;
    EditText dName,dMobile,dAmount;
    Button dSubmitBtn;
    ProgressDialog progressDialog;

    public static DonationFeeFragment newInstance() {
        DonationFeeFragment fragment = new DonationFeeFragment();
        return fragment;
    }

    public DonationFeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_fee, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Donation Fee");
        dName = view.findViewById(R.id.dName);
        dMobile = view.findViewById(R.id.dMobile);
        dAmount = view.findViewById(R.id.dAmount);

        progressDialog = new ProgressDialog(getContext());

        dSubmitBtn = view.findViewById(R.id.dSubmitBtn);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        dSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Sending...");
                progressDialog.show();

                String institutionName = dName.getText().toString();
                String donarMobile = dMobile.getText().toString();
                String donateAmount = dAmount.getText().toString();


                Call<ResponseBody> responseBodyCall = service.AddDonationFee(institutionName,donateAmount,donarMobile);

                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Snackbar snackbar = Snackbar
                                    .make(getView(), "Added To Chargelist", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dName.setText("");
                                    dMobile.setText("");
                                    dAmount.setText("");
                                }
                            });
                            snackbar.setActionTextColor(Color.YELLOW);
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

}
