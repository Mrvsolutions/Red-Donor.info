package mr.vsolutions.red_donorinfo.Retrofit;

import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.LoginUser;
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
//    @GET("AllAudio.php?id=14")
//    Call<AudioItems> getTopAllAudioGuruRandhawa();
}
