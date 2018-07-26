package org.atctech.sms_accountant.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jacky on 4/5/2018.
 */

public class BalanceSheetModel {

    @SerializedName("TotalAmnt")
    @Expose
    private int totalAmount;

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}


