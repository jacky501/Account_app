package org.atctech.sms_accountant.navigationdrawer;

import android.view.View;
import android.widget.TextView;

import org.atctech.sms_accountant.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by miki on 7/7/17.
 */

public class SubTitleViewHolder extends ChildViewHolder {
    private TextView subTitleTextView;

    public SubTitleViewHolder(View itemView) {
        super(itemView);
        subTitleTextView = itemView.findViewById(R.id.main_nav_submenu_item_title);
    }

    public void setSubTitletName(String name) {
        subTitleTextView.setText(name);
    }
}
