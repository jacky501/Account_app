package org.atctech.sms_accountant.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.atctech.sms_accountant.Model.ChargesList;
import org.atctech.sms_accountant.R;
import org.atctech.sms_accountant.fragment.EditFragment;

import java.util.List;

/**
 * Created by Jacky on 4/7/2018.
 */

public class ChargesListAdapter extends RecyclerView.Adapter<ChargesListAdapter.ChargesListViewHolder>{

    private AlertDialog.Builder builder;
    Context context;
    List<ChargesList> chargesLists;


    public ChargesListAdapter(Context context, List<ChargesList> chargesLists) {
        this.context = context;
        this.chargesLists = chargesLists;
    }

    @Override

    public ChargesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_chargelist,null,false);
        return new ChargesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChargesListViewHolder holder, int position) {

        final ChargesList charList = chargesLists.get(position);

        holder.catName.setText(charList.getC_name());

        builder = new AlertDialog.Builder(context);

        holder.chargeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditFragment fragment = new EditFragment();
                Bundle bundle = new Bundle();
                bundle.putString("C_ID",charList.getId());
                bundle.putString("C_NAME",charList.getC_name());
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_content,fragment).addToBackStack("Charge List").commit();


            }
        });

    }


    @Override
    public int getItemCount() {
        return chargesLists != null ? chargesLists.size() : 0;
    }

    public void removeItem(int position) {
        chargesLists.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ChargesList chargesList, int position) {
        chargesLists.add(position, chargesList);
        notifyItemInserted(position);
    }

    public class ChargesListViewHolder extends RecyclerView.ViewHolder
    {

        private TextView catName;
        private ImageView chargeEdit,chargeDelete;
        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;


        public ChargesListViewHolder(View itemView) {
            super(itemView);

            catName = itemView.findViewById(R.id.categoryNames);
            chargeEdit = itemView.findViewById(R.id.chargeEdit);
            chargeDelete = itemView.findViewById(R.id.chargeDelete);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }

}
