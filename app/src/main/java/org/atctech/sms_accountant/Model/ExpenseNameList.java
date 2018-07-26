package org.atctech.sms_accountant.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacky on 4/8/2018.
 */

public class ExpenseNameList
{
    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("id")
    @Expose
    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
