package org.atctech.sms_accountant.fragment;


import android.app.AlertDialog;
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
public class EditFragment extends Fragment {

    AlertDialog.Builder builder;
    ApiRequest service;

    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit");

        builder = new AlertDialog.Builder(getContext());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        ViewDialouge();

        return view;
    }

    public void ViewDialouge()
    {
        Bundle bundle = getArguments();

        final String cat_id = bundle.getString("C_ID");
        final String cat_name = bundle.getString("C_NAME");

        LayoutInflater inflater = this.getLayoutInflater();

        View dview = inflater.inflate(R.layout.dialouge_charge_name_edit, null);
        final EditText edtFieldCharge = dview.findViewById(R.id.editfieldChargeList);
        final Button submitButton = dview.findViewById(R.id.editcharSubmitBtn);



        builder.setView(dview);
        builder.setCancelable(true);

        final AlertDialog alertDialog = builder.create();

        edtFieldCharge.setText(cat_name);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c_name = edtFieldCharge.getText().toString();

                Call<ResponseBody> responseBodyCall = service.EditChargeList(c_name,cat_id);

                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            alertDialog.dismiss();

                            Snackbar snackbar = Snackbar
                                    .make(getView(), "Name Successfully Changed", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ChargeListFragment fragment = new ChargeListFragment();
                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.main_content,fragment)
                                                .commitAllowingStateLoss();
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

                                    ViewDialouge();

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
