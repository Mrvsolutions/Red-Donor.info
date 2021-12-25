package mr.vsolutions.red_donorinfo.Retrofit;

import mr.vsolutions.red_donorinfo.model.AllMessage;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.DonorReviewSummary;
import mr.vsolutions.red_donorinfo.model.LoginUser;
import mr.vsolutions.red_donorinfo.model.MsgdataMain;
import mr.vsolutions.red_donorinfo.model.UploadImageResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("signupDonor.php")
    Call<DefaultResponse> CreateUserCall(
            @Field("donor_name") String Donor_Name,
            @Field("donor_city") String Donor_City,
            @Field("donor_email") String Donor_Email,
            @Field("donor_age") String Donor_Age,
            @Field("donor_gender") String Donor_Gender,
            @Field("donor_blood_group") String Donor_Blood_Group,
            @Field("donor_pswd") String Donor_Pswd,
            @Field("donor_mobileno") String Donor_Mobile,
            @Field("donor_latitude") String Donor_latt,
            @Field("donor_longitude") String Donor_longt,
            @Field("donor_address") String donor_Address,
            @Field("donor_dob") String Donor_Dob
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
    @POST("ResendVerifyCode.php")
    Call<DefaultResponse> ResendVerifictionCodeCall(
            @Field("donor_email") String Donor_email
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
            @Field("donor_gender") String Donor_Gender,
            @Field("donor_mobileno") String Donor_Mobile,
            @Field("donor_age") String Donor_age,
            @Field("donor_blood_group") String Donor_blood_group,
            @Field("donor_address") String Donor_address,
            @Field("donor_dob") String Donor_Dob,
            @Field("pimgUrl") String PimgUrl
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

    @FormUrlEncoded
    @POST("messagesSend.php")
    Call<DefaultResponse> SendmsgAsync(
            @Field("sender_id") String Sender_id,
            @Field("receiver_id") String Receiver_id,
            @Field("msg_text") String Msg_text
    );

    @FormUrlEncoded
    @POST("donorListFilter.php")
    Call<DonorDataMain> GetFilterDataAsync(
            @Field("donor_blood_group") String Donor_blood_group,
            @Field("donor_min_age") String Donor_min_age,
            @Field("donor_max_age") String Donor_max_age,
            @Field("donor_gender") String Donor_Gender,
            @Field("donor_distance") String Donor_distance,
            @Field("user_clat") String User_clat,
            @Field("user_clong") String User_clong
    );

    @FormUrlEncoded
    @POST("tokenStore.php")
    Call<DefaultResponse> DeviceRegisterAsync(
            @Field("token") String Token,
            @Field("donor_id") String Donor_id
    );

    @FormUrlEncoded
    @POST("signoutDonor.php")
    Call<DefaultResponse> SignoutAsync(
            @Field("donor_id") String Donor_id
    );

    @Multipart
    @POST("uploadDonorProfileImg.php")
    Call<UploadImageResponse> UploadUserImage(
            @Part("donor_id") String Donor_id,
            @Part MultipartBody.Part image
    );
    @FormUrlEncoded
    @POST("donorComplaintAdd.php")
    Call<DefaultResponse> SendDonorComplainAsync(
            @Field("cmpt_by_donor_id") String Cmpt_by_donor_id,
            @Field("cmpt_of_donor_id") String Cmpt_of_donor_id,
            @Field("cmpt_title") String Cmpt_title,
            @Field("cmpt_desc") String Cmpt_desc
    );
}
