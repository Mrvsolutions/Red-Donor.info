package mr.vsolutions.red_donorinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyEmailCodeActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgback;
    Button btn_verify;
    EditText edtotp;
    String useremail,userOtp;
    TextView txtuseremail;
    private static final String TAG = VerifyEmailCodeActivity.class.getSimpleName();
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifyemail_screen);
        getSupportActionBar().hide();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        edtotp = findViewById(R.id.edtotp);
        btn_verify = findViewById(R.id.btn_verify);
        imgback = findViewById(R.id.imgback);
        txtuseremail= findViewById(R.id.txtuseremail);
        useremail = getIntent().getStringExtra("useremail");
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(10);
        mProgressDialog.setMax(100);
        txtuseremail.setText(useremail+getString(R.string.str_VerifyEmailText));
        btn_verify.setOnClickListener(this);
        imgback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verify:
                VerifyOTPCall();
                break;
            case R.id.imgback:
                finish();
                break;
        }
    }

    private void VerifyOTPCall() {
        try {
            userOtp = edtotp.getText().toString().trim();
            if (ValidateOTP()) {
                try {
                    if (!mProgressDialog.isShowing()) {
                        mProgressDialog.show();
                    }
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<DefaultResponse> call = apiService.VerifyCodeCall(useremail,userOtp);
                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            DefaultResponse defaultResponse = response.body();
                            if (defaultResponse.getSuccess() == 1) {
                                Toast.makeText(VerifyEmailCodeActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedpreferences = getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Comman.VerificationOtpComplete,getString(R.string.str_OtpValidated));
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(VerifyEmailCodeActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            // Log error here since request failed
                            Toast.makeText(VerifyEmailCodeActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
            Log.e(TAG,"VerifyOTPCall - "+ ex.toString());
        }
    }
    private boolean ValidateOTP() {
        try {
            if (userOtp.isEmpty()) {
                edtotp.setError(getString(R.string.str_enterotp));
                edtotp.requestFocus();
                return false;
            }
        } catch (Exception ex) {
            Log.e(TAG, "ValidateOTP :- "+ex.toString());
        }
        return true;
    }
}