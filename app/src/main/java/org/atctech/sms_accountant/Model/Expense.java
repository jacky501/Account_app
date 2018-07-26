package org.atctech.sms_accountant.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacky on 4/5/2018.
 */

public class Expense {

    @SerializedName("expName")
    @Expose
    private String expenseName;


    @SerializedName("totalAmnt")
    @Expose
    private String expenseTotalAmount;


    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseTotalAmount() {
        return expenseTotalAmount;
    }

    public void setExpenseTotalAmount(String expenseTotalAmount) {
        this.expenseTotalAmount = expenseTotalAmount;
    }
}
