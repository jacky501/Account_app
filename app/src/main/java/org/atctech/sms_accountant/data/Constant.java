package org.atctech.sms_accountant.data;

import org.atctech.sms_accountant.R;
import org.atctech.sms_accountant.fragment.BalanceSheetFragment;
import org.atctech.sms_accountant.fragment.ChargeListFragment;
import org.atctech.sms_accountant.fragment.DashboardFragment;
import org.atctech.sms_accountant.fragment.DonationFeeFragment;
import org.atctech.sms_accountant.fragment.ExpenseFragment;
import org.atctech.sms_accountant.fragment.StudentFeeFragment;
import org.atctech.sms_accountant.navigationdrawer.NavMenuModel;

import java.util.ArrayList;

/**
 * Created by miki on 7/7/17.
 */

public class Constant {

    public static ArrayList<NavMenuModel> getNavigationMenu(){
        ArrayList<NavMenuModel> menu = new ArrayList<>();

        menu.add(new NavMenuModel("Dashboard", R.drawable.ic_dashboard_black_48dp, DashboardFragment.newInstance()));
        menu.add(new NavMenuModel("Balance Sheet", R.drawable.ic_account_balance_wallet_black_48dp, BalanceSheetFragment.newInstance()));
        menu.add(new NavMenuModel("Charges List", R.drawable.ic_assignment_black_48dp, ChargeListFragment.newInstance()));
        menu.add(new NavMenuModel("Expense", R.drawable.ic_content_cut_black_48dp, ExpenseFragment.newInstance()));
        menu.add(new NavMenuModel("Income", R.drawable.ic_monetization_on_black_48dp,
                new ArrayList<NavMenuModel.SubMenuModel>() {{
                    add(new NavMenuModel.SubMenuModel("Student Fee", StudentFeeFragment.newInstance()));
                    add(new NavMenuModel.SubMenuModel("Donation Fee", DonationFeeFragment.newInstance()));
                }}));




        return menu;
    }
}
