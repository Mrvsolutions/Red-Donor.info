package mr.vsolutions.red_donorinfo.Retrofit;

import mr.vsolutions.red_donorinfo.model.AllMessage;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.DonorReviewSummary;
import mr.vsolutions.red_donorinfo.model.LoginUser;
import mr.vsolutions.red_donorinfo.model.MsgdataMain;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("signupDonor.php")
    Call<DefaultResponse> CreateUserCall(
            @Field("donor_name") String Donor_Name,
            @Field("donor_city") String Donor_City,
            @Field("donor_email") String Donor_Email,
            @Field("donor_pswd") String Donor_Pswd,
            @Field("donor_mobileno") String Donor_Mobile,
            @Field("donor_latitude") String Donor_latt,
            @Field("donor_longitude") String Donor_longt
    );

    @FormUrlEncoded
    @POST("signinDonor.php")
    Call<LoginUser> LoginUserCall(
            @Field("donor_email") String Donor_email,
            @Field("donor_pswd") String Donor_pswd
    );

    @FormUrlEncoded
    @POST("forgotPswd.php")
    Call<DefaultResponse> ForgotPassWordCall(
            @Field("donor_email") String Donor_email
    );

    @FormUrlEncoded
    @POST("signupVerifyCode.php")
    Call<DefaultResponse> VerifyCodeCall(
            @Field("donor_email") String Donor_email,
            @Field("signup_verificationcode") String Signup_verificationcode
    );

    @FormUrlEncoded
    @POST("donorList.php")
    Call<DonorDataMain> GetDonorList(
            @Field("user_clat") String User_clat,
            @Field("user_clong") String User_clong
    );

    @FormUrlEncoded
    @POST("editDonorProfile.php")
    Call<LoginUser> EditUserDataCall(
            @Field("donor_id") String Donor_id,
            @Field("donor_name") String Donor_Name,
            @Field("donor_city") String Donor_City,
            @Field("donor_email") String Donor_Email,
            @Field("donor_mobileno") String Donor_Mobile,
            @Field("donor_age") String Donor_age,
            @Field("donor_blood_group") String Donor_blood_group,
            @Field("donor_address") String Donor_address
    );

    @FormUrlEncoded
    @POST("donorReviewAdd.php")
    Call<DefaultResponse> SendDonorReviewCall(
            @Field("donor_id") String Donor_id,
            @Field("r_name") String R_name,
            @Field("r_rating") String R_rating,
            @Field("review_title ") String Review_title,
            @Field("review_content") String Review_content
    );

    @FormUrlEncoded
    @POST("donorReviewList.php")
    Call<DonorReviewSummary> GetDonorReViewSummaryList(
            @Field("donor_id") String Donor_id
    );

    @FormUrlEncoded
    @POST("chatListAll.php")
    Call<AllMessage> GetChatConversationList(
            @Field("donor_id") String Donor_id
    );

    @FormUrlEncoded
    @POST("messagesGet.php")
    Call<MsgdataMain> GetmsgdataList(
            @Field("donor_id") String Donor_id,
            @Field("receiver_id") String Receiver_id
    );
}
