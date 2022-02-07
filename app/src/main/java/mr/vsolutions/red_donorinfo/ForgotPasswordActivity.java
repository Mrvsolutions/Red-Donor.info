package mr.vsolutions.red_donorinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imgback;
    Button btn_confirmmail;
    EditText edtEmail;
    String useremail ;
    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        getSupportActionBar().hide();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        edtEmail = findViewById(R.id.edtEmail);
        btn_confirmmail = findViewById(R.id.btn_confirmmail);
        imgback = findViewById(R.id.imgback);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(10);
        mProgressDialog.setMax(100);

        btn_confirmmail.setOnClickListener(this);
        imgback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmmail:
                ForgotMailCall();
                break;
            case  R.id.txtnewuser:
                Intent i = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
                break;
            case R.id.imgback:
                finish();
                break;
        }
    }

    private void ForgotMailCall() {
        try {
            useremail = edtEmail.getText().toString().trim();
            if (ValidateEmail()) {
                try {
                    if (!mProgressDialog.isShowing()) {
                        mProgressDialog.show();
                    }
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<DefaultResponse> call = apiService.ForgotPassWordCall(useremail);
                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            DefaultResponse defaultResponse = response.body();
                            if (defaultResponse.getSuccess() == 1) {
                                Toast.makeText(ForgotPasswordActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            // Log error here since request failed
                           // Toast.makeText(ForgotPasswordActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
            Log.e(TAG,"ForgotMailCall - "+ ex.toString());
        }
    }
    private boolean ValidateEmail() {
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
        } catch (Exception ex) {
            Log.e(TAG, "ValidateEmail :- "+ex.toString());
        }
        return true;
    }
}