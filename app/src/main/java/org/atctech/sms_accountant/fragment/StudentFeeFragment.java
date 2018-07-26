package org.atctech.sms_accountant.fragment;


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
import org.atctech.sms_accountant.Model.StudentRollSearchResult;
import org.atctech.sms_accountant.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFeeFragment extends Fragment {

    ApiRequest service;
    private EditText rollSearchField;
    private Button rollSearchBtn;

    public static StudentFeeFragment newInstance() {
        StudentFeeFragment fragment = new StudentFeeFragment();
        return fragment;
    }

    public StudentFeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_fee, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Student Fee");
        rollSearchField = view.findViewById(R.id.rollSearchField);
        rollSearchBtn = view.findViewById(R.id.rollSearchBtn);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        rollSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String roll = rollSearchField.getText().toString();

                Call<StudentRollSearchResult> rollSearchResultCall = service.GetSearchRollResults(roll);

                rollSearchResultCall.enqueue(new Callback<StudentRollSearchResult>() {
                    @Override
                    public void onResponse(Call<StudentRollSearchResult> call, Response<StudentRollSearchResult> response) {
                        if (response.isSuccessful())
                        {
                            StudentRollSearchResult result = response.body();
                            StudentFeesAddFragment fragment = new StudentFeesAddFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("S_FNAME",result.getFname());
                            bundle.putString("S_LNAME",result.getLname());
                            bundle.putString("S_UID",result.getU_id());
                            bundle.putString("S_CLASS",result.getClassName());
                            fragment.setArguments(bundle);
                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.studentFeeMainFrame,fragment)
                                    .addToBackStack("Student_fee")
                                    .commit();
                        }else
                        {
                            Snackbar snackbar = Snackbar
                                    .make(getView(), "No student found !!", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    rollSearchField.setText("");
                                }
                            });
                            snackbar.setActionTextColor(Color.RED);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentRollSearchResult> call, Throwable t) {

                    }
                });

            }
        });

        return view;
    }

}
