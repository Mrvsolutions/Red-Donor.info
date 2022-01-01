package mr.vsolutions.red_donorinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.model.LoginUser;
import mr.vsolutions.red_donorinfo.model.UserDetail;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgback;
    Button btn_login;
    TextView txtforgotpassword,txtnewuser;
    EditText edtEmail,edtpassword;
    String useremail,  UserPass ;
    private static final String TAG = LoginActivity.class.getSimpleName();
    ProgressDialog mProgressDialog;
    String VerificationOtpComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        txtforgotpassword = findViewById(R.id.txtforgotpassword);
        txtnewuser = findViewById(R.id.txtnewuser);
        edtEmail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtpassword);
        btn_login = findViewById(R.id.btn_login);
        imgback = findViewById(R.id.imgback);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(10);
        mProgressDialog.setMax(100);
        SpannableString str1= new SpannableString("New User?");
        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorbottomtext)), 0, str1.length(), 0);
        builder.append(str1);

        SpannableString str2= new SpannableString(" Sign Up");
        str2.setSpan(new ForegroundColorSpan(Color.RED), 0, str2.length(), 0);
        builder.append(str2);
        SharedPreferences  sharedpreferences = getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
        VerificationOtpComplete  = sharedpreferences.getString(Comman.VerificationOtpComplete, "");
        txtnewuser.setText( builder, TextView.BufferType.SPANNABLE);

        txtnewuser.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        imgback.setOnClickListener(this);
        txtforgotpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                LoginUserCall();
                break;
            case  R.id.txtnewuser:
                Intent i = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
                break;
            case R.id.imgback:
                finish();
                break;
            case R.id.txtforgotpassword:
                Intent intent = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void LoginUserCall() {
        try {
            UserPass = edtpassword.getText().toString().trim();
            useremail = edtEmail.getText().toString().trim();
            if (Validatelogin()) {
                try {
                    if (!mProgressDialog.isShowing()) {
                        mProgressDialog.show();
                    }
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<LoginUser> call = apiService.LoginUserCall(useremail,UserPass);
                    call.enqueue(new Callback<LoginUser>() {
                        @Override
                        public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                            LoginUser LoginResponse = response.body();
                            if (LoginResponse.getSuccess() == 1) {
                                Toast.makeText(LoginActivity.this, LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                List<UserDetail> lstuserdetail = LoginResponse.getUserdata();
                                Comman.CommanUserDetail = lstuserdetail.get(0);
                                SharedPreferences sharedpreferences = getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Comman.LoginCompleted,getString(R.string.str_LoginCompleted));
                                Gson gson = new Gson();
                                String json = gson.toJson(lstuserdetail.get(0));
                                editor.putString(Comman.strCommanuserdetai,json);
                                editor.commit();
                                editor.apply();
                                if (lstuserdetail.get(0).getFrgtPswdRqst().contains("YES"))
                                {
                                    Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                               else if (lstuserdetail.get(0).getSignupVerificationcode().equals("0") || (!VerificationOtpComplete.isEmpty() && VerificationOtpComplete.equals(getString(R.string.str_OtpValidated)))) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Intent intent = new Intent(getApplicationContext(),VerifyEmailCodeActivity.class);
                                    intent.putExtra("useremail", useremail);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginUser> call, Throwable t) {
                            // Log error here since request failed
                            Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, t.toString());
                            if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                        }
                    });
                } catch (Exception ex) {
                    if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Log.e(TAG, ex.toString());
                }
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG,"LoginUserCall - "+ ex.toString());
        }
    }
    private boolean Validatelogin() {
        try {
            if (useremail.isEmpty()) {
                edtEmail.setError(getString(R.string.str_enterEmail));
                edtEmail.requestFocus();
                return false;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
                edtEmail.setError(getString(R.string.str_validemail));
                edtEmail.requestFocus();
                return false;
            }
            if (UserPass.isEmpty()) {
                edtpassword.setError(getString(R.string.str_enterPass));
                edtpassword.requestFocus();
                return false;
            }

        } catch (Exception ex) {
            Log.e(TAG, "Validatelogin :- "+ex.toString());
        }
        return true;
    }
}