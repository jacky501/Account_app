package org.atctech.sms_accountant.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacky on 4/9/2018.
 */

public class StudentRollSearchResult {

    @SerializedName("fname")
    @Expose
    private String fname;


    @SerializedName("lname")
    @Expose
    private String lname;

    @SerializedName("u_id")
    @Expose
    private String u_id;


    @SerializedName("className")
    @Expose
    private String className;


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

