package org.atctech.sms_accountant.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.atctech.sms_accountant.Model.BalanceSheetModel;
import org.atctech.sms_accountant.Model.Donation;
import org.atctech.sms_accountant.Model.Expense;
import org.atctech.sms_accountant.R;

import java.util.List;

/**
 * Created by Jacky on 4/5/2018.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.IncomeViewHolder>{

    private Context context;
    private List<Expense> expenses;

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    @Override
    public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_expense,null,false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IncomeViewHolder holder, int position) {

        holder.expenseType.setText(expenses.get(position).getExpenseName());
        holder.expenseValue.setText(expenses.get(position).getExpenseTotalAmount());

    }

    @Override
    public int getItemCount() {
        return expenses != null ? expenses.size() : 0;
    }

    public class IncomeViewHolder extends RecyclerView.ViewHolder
    {

        private TextView expenseType,expenseValue;

        public IncomeViewHolder(View itemView) {
            super(itemView);

            expenseType = itemView.findViewById(R.id.expenseType);
            expenseValue = itemView.findViewById(R.id.expenseValue);

        }
    }
}
