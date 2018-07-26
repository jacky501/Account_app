package org.atctech.sms_accountant.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.atctech.sms_accountant.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceSheetFragment extends Fragment{

    TextView date1,date2;
    Button calBtn1,calBtn2,dateSubmitBtn;

    public static BalanceSheetFragment newInstance() {
        BalanceSheetFragment fragment = new BalanceSheetFragment();
        return fragment;
    }

    public BalanceSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balance_sheet, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Balance Sheet");
        date1 = view.findViewById(R.id.date1);
        date2 = view.findViewById(R.id.date2);
        calBtn1 = view.findViewById(R.id.calPicker1);
        calBtn2 = view.findViewById(R.id.calPicker2);
        dateSubmitBtn = view.findViewById(R.id.dateSubmitBtn);
        //From
        calBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    date1.setText(String.format("%d-%d-%d", year, monthOfYear+1, dayOfMonth));
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                    dpd.setTitle("Choose From Date");
                    dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

            }
        });

        //To
        calBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                date2.setText(String.format("%d-%d-%d", year, monthOfYear+1, dayOfMonth));
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setTitle("Choose To Date");
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        //Submit button click event

        dateSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BalanceSheetShowFragment fragment = new BalanceSheetShowFragment();
                Bundle bundle = new Bundle();
                bundle.putString("DATE1",date1.getText().toString());
                bundle.putString("DATE2",date2.getText().toString());
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.balsheetResults,fragment).addToBackStack("Balance Sheet").commit();

            }
        });


        return view;
    }
}
