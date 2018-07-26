package org.atctech.sms_accountant.Model;

import android.telecom.Call;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacky on 4/7/2018.
 */

public class ChargesList {
    @SerializedName("c_name")
    @Expose
    private String c_name;

    @SerializedName("id")
    @Expose
    private String id;


    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
