package org.atctech.sms_accountant.Inteface;

import org.atctech.sms_accountant.Model.BalanceSheetModel;
import org.atctech.sms_accountant.Model.ChargesList;
import org.atctech.sms_accountant.Model.Donation;
import org.atctech.sms_accountant.Model.Expense;
import org.atctech.sms_accountant.Model.ExpenseNameList;
import org.atctech.sms_accountant.Model.StudentRollSearchResult;
import org.atctech.sms_accountant.Model.TotalExpenses;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Jacky on 2/7/2018.
 */

public interface ApiRequest {

    @FormUrlEncoded
    @POST("balance_sheet_income_student_fee.php")
    Call<BalanceSheetModel> getStudentFee(@Field("date1") String fromDate,@Field("date2") String toDate);

    @FormUrlEncoded
    @POST("balance_sheet_income_donation_fee.php")
    Call<Donation> getDonationFee(@Field("date1") String fromDate, @Field("date2") String toDate);


    @FormUrlEncoded
    @POST("balance_sheet_expense_money.php")
    Call<List<Expense>> getExpenseMoney(@Field("date1") String fromDate, @Field("date2") String toDate);


    @FormUrlEncoded
    @POST("balance_sheet_total_expense.php")
    Call<TotalExpenses> getTotalExpenses(@Field("date1") String fromDate, @Field("date2") String toDate);


    @GET("charges_list.php")
    Call<List<ChargesList>> getChargesList();

    @FormUrlEncoded
    @POST("charges_list_add.php")
    Call<ResponseBody> AddChargeList(@Field("cat_name") String cat_name);


    @FormUrlEncoded
    @POST("charges_list_delete.php")
    Call<ResponseBody> DeleteChargeList(@Field("cat_id") String cat_id);


    @GET("expenselist_all.php")
    Call<List<ExpenseNameList>> GetAllExpenseNameList();

    @FormUrlEncoded
    @POST("expense_name_add.php")
    Call<ResponseBody> AddExpenseName(@Field("expense_name") String expense_name);

    @FormUrlEncoded
    @POST("expense_add.php")
    Call<ResponseBody> AddExpenseAmount(@Field("expense_id") String exp_id,@Field("amount") String amount);

    @FormUrlEncoded
    @POST("student_roll_search.php")
    Call<StudentRollSearchResult> GetSearchRollResults(@Field("roll_id") String roll_id);

    @FormUrlEncoded
    @POST("donation_fee.php")
    Call<ResponseBody> AddDonationFee(@Field("d_name") String d_name,@Field("amount") String amount,@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("charges_list_edit.php")
    Call<ResponseBody> EditChargeList(@Field("cat_name") String cat_name,@Field("cat_id") String catID);

    @FormUrlEncoded
    @POST("student_fees_post.php")
    Call<ResponseBody> SendStudentFeeToServer(@Field("cat_id[]") String cat_id,
                                        @Field("from[]") String fromDate,
                                        @Field("to[]") String toDate,
                                        @Field("amount[]") String chargeAmount,
                                        @Field("comment[]") String accountComment,
                                        @Field("student_ID") String studentIdNo);

    @FormUrlEncoded
    @POST("accountant_login.php")
    Call<ResponseBody> login(@Field("username") String userName,@Field("password") String password);

}
