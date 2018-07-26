package org.atctech.sms_accountant.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.atctech.sms_accountant.Inteface.ApiRequest;
import org.atctech.sms_accountant.Model.ChargesList;
import org.atctech.sms_accountant.R;
import org.w3c.dom.Text;

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
public class StudentFeesAddFragment extends Fragment {

    ApiRequest service;
    private Spinner chargesName,chargesFrom,chargesTo;
    private EditText chargesAmount,chargesComments;
    private TextView studentName,studentClass;
    private Button chargesSubmitBtn;
    List<ChargesList> chargesLists;

    public StudentFeesAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_fees_add, container, false);

        studentName = view.findViewById(R.id.studentName);
        studentClass = view.findViewById(R.id.studentClass);
        chargesName = view.findViewById(R.id.charges_spinner);
        chargesFrom = view.findViewById(R.id.chargesFrom);
        chargesTo = view.findViewById(R.id.chargesTo);
        chargesAmount = view.findViewById(R.id.chargesAmount);
        chargesComments = view.findViewById(R.id.chargesComment);
        chargesSubmitBtn = view.findViewById(R.id.chargesSubmitBtn);


        Bundle bundle = getArguments();
        String firstname= bundle.getString("S_FNAME");
        String lastname= bundle.getString("S_LNAME");
        String className= bundle.getString("S_CLASS");
        final String studentId= bundle.getString("S_UID");

        studentName.setText(firstname+" "+lastname);
        studentClass.setText(className);

        String[] from = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String[] to = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        ArrayAdapter<String> fromadapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,from);
        chargesFrom.setAdapter(fromadapter);

        ArrayAdapter<String> toadapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,to);
        chargesTo.setAdapter(toadapter);

        Call<List<ChargesList>> listCall = service.getChargesList();
        listCall.enqueue(new Callback<List<ChargesList>>() {
            @Override
            public void onResponse(Call<List<ChargesList>> call, Response<List<ChargesList>> response) {
                if (response.isSuccessful())
                {
                    chargesLists = response.body();
                    String[] names;
                    if (chargesLists != null) {
                        names = new String[chargesLists.size()];

                        for (int i = 0; i < chargesLists.size(); i++) {
                            names[i] = chargesLists.get(i).getC_name();
                        }
                        chargesName.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, names));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ChargesList>> call, Throwable t) {

            }
        });

        chargesSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = chargesName.getSelectedItemPosition();
                final String charNameId = chargesLists.get(position).getId();
                final String charFromName = chargesFrom.getSelectedItem().toString();
                final String charToName = chargesTo.getSelectedItem().toString();
                final String amount = chargesAmount.getText().toString();
                String comments = chargesComments.getText().toString();

                if(TextUtils.isEmpty(amount))
                {
                    chargesAmount.setError("Amount Field Must Not Empty");
                    return;
                }

               Call<ResponseBody> responseBodyCall = service.SendStudentFeeToServer(charNameId,charFromName,charToName,amount,comments,studentId);

               responseBodyCall.enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       if (response.isSuccessful())
                       {
                           Snackbar snackbar = Snackbar
                                   .make(getView(),chargesName.getSelectedItem().toString()+" : "+amount+" ( "+charFromName+"-"+charToName+" ) Has Been Added Successfully", Snackbar.LENGTH_INDEFINITE);
                           snackbar.setAction("Ok", new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                    chargesName.setSelection(0);
                                    chargesFrom.setSelection(0);
                                    chargesTo.setSelection(0);
                                    chargesAmount.setText("");
                                    chargesComments.setText("");
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

        chargesName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

}
