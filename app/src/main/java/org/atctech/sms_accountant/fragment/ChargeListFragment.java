package org.atctech.sms_accountant.fragment;


import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.atctech.sms_accountant.Adapter.ChargesListAdapter;
import org.atctech.sms_accountant.Inteface.ApiRequest;
import org.atctech.sms_accountant.Model.ChargesList;
import org.atctech.sms_accountant.R;
import org.atctech.sms_accountant.utils.RecyclerItemTouchHelper;

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
public class ChargeListFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    AlertDialog.Builder builder;
    RecyclerView chargeRecyclerView;
    ApiRequest service;
    List<ChargesList> chargesLists;
    ChargesListAdapter adapter;
    FloatingActionButton chargeListAddBtn;


    public static ChargeListFragment newInstance() {
        ChargeListFragment fragment = new ChargeListFragment();
        return fragment;
    }

    public ChargeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charge_list, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Charges List");
        chargeRecyclerView = view.findViewById(R.id.chargelistRecyclerView);
        chargeListAddBtn = view.findViewById(R.id.chargeListAddButton);
        builder = new AlertDialog.Builder(getContext());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

       Showchargelists();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(chargeRecyclerView);

        chargeListAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddChargeList();
            }
        });

        return view;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ChargesListAdapter.ChargesListViewHolder) {
            // get the removed item name to display it in snack bar
            //String name = chargesLists.get(viewHolder.getAdapterPosition()).getC_name();
            String id = chargesLists.get(viewHolder.getAdapterPosition()).getId();

            Call<ResponseBody> responseBodyCall = service.DeleteChargeList(id);
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful())
                    {
                        Snackbar snackbar = Snackbar
                                .make(getView(), "Delete Succesfully", Snackbar.LENGTH_LONG);
                        snackbar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                        adapter.removeItem(viewHolder.getAdapterPosition());

                            }
                        });
                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();
                    }else
                    {
                        Snackbar snackbar = Snackbar
                                .make(getView(), "There is a problem ", Snackbar.LENGTH_LONG);
                        snackbar.setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                adapter.removeItem(viewHolder.getAdapterPosition());

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
    }

    public void AddChargeList() {
        LayoutInflater inflater = this.getLayoutInflater();

        View dview = inflater.inflate(R.layout.edit_text_dialouge, null);
        final EditText edtFieldCharge = dview.findViewById(R.id.fieldChargeList);
        final Button submitButton = dview.findViewById(R.id.charSubmitBtn);

        builder.setView(dview);
        builder.setCancelable(true);

        final AlertDialog alertDialog = builder.create();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String charges = edtFieldCharge.getText().toString();
                Call<ResponseBody> responseBodyCall = service.AddChargeList(charges);



                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            alertDialog.dismiss();

                            Snackbar snackbar = Snackbar
                                    .make(getView(), "Added To Chargelist", Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Showchargelists();
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
                                    AddChargeList();

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

    public void Showchargelists()
    {
        Call<List<ChargesList>> chargesListCall = service.getChargesList();

        chargesListCall.enqueue(new Callback<List<ChargesList>>() {
            @Override
            public void onResponse(Call<List<ChargesList>> call, Response<List<ChargesList>> response) {
                if (response.isSuccessful()) {
                    chargesLists = response.body();
                    adapter = new ChargesListAdapter(getContext(), chargesLists);
                    chargeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    chargeRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    chargeRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                    chargeRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<ChargesList>> call, Throwable t) {

            }
        });
    }
}
