package org.atctech.sms_accountant.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jacky on 4/5/2018.
 */

public class Donation {

    @SerializedName("dTotalAmnt")
    @Expose
    private int donationTotalAmount;

    public int getDonationTotalAmount() {
        return donationTotalAmount;
    }

    public void setDonationTotalAmount(int donationTotalAmount) {
        this.donationTotalAmount = donationTotalAmount;
    }
}
