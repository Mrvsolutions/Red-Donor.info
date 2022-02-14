package mr.vsolutions.red_donorinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgback;
    Button btn_change;
    EditText edtoldPassWord,edtnewPassWord,edtconfrmPassWord;
    String UseroldPass,UserNewPass,UserDonorID,UserConfpass;
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().hide();

        edtoldPassWord = findViewById(R.id.edtoldPassWord);
        edtnewPassWord= findViewById(R.id.edtnewPassWord);
        edtconfrmPassWord = findViewById(R.id.edtconfrmPassWord);
        btn_change = findViewById(R.id.btn_change);
        imgback = findViewById(R.id.imgback);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(10);
        mProgressDialog.setMax(100);
        btn_change.setOnClickListener(this);
        imgback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                ChangePasswordCall();
                break;
            case R.id.imgback:
                finish();
                break;
        }
    }
    private void ChangePasswordCall() {
        try {
            UseroldPass = edtoldPassWord.getText().toString().trim();
            UserNewPass = edtnewPassWord.getText().toString().trim();
            UserConfpass = edtconfrmPassWord.getText().toString().trim();
            UserDonorID = Comman.CommanUserDetail.getDonorId();
            if (ValidatePassword()) {
                try {
                    if (!mProgressDialog.isShowing()) {
                        mProgressDialog.show();
                    }
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<DefaultResponse> call = apiService.SenChangePasswordAsync(UserDonorID,UseroldPass,UserNewPass);
                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            DefaultResponse defaultResponse = response.body();
                            if (defaultResponse.getSuccess() == 1) {
                                Toast.makeText(getApplicationContext(), defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            // Log error here since request failed
                            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
            Log.e(TAG,"ChangePasswordCall - "+ ex.toString());
        }
    }
    private boolean ValidatePassword() {
        try {
            if (UseroldPass.isEmpty()) {
                edtoldPassWord.setError(getString(R.string.str_enterOldPass));
                edtoldPassWord.requestFocus();
                return false;
            }
            if (UserNewPass.isEmpty()) {
                edtnewPassWord.setError(getString(R.string.str_enterNewPass));
                edtnewPassWord.requestFocus();
                return false;
            }
            if (!UserNewPass.isEmpty() && !Comman.isValidPassword((UserNewPass)))
            {
                int unicode = 0x2022;
                String textIcon =  new String(Character.toChars(unicode));
                String strpass = textIcon+" "+getString(R.string.str_entervalidPass)+"\n";
                String strpass1 = textIcon+" "+getString(R.string.str_entervalidPass1)+"\n";
                String strpass2 =  textIcon+" "+getString(R.string.str_entervalidPass2)+"\n";
                String strpass3 =  textIcon+" "+getString(R.string.str_entervalidPass3);
                edtnewPassWord.setError(strpass + strpass1 + strpass2 + strpass3);
                edtnewPassWord.requestFocus();
                return false;
            }
            if (UserConfpass.isEmpty()) {
                edtconfrmPassWord.setError(getString(R.string.str_enterConfPass));
                edtconfrmPassWord.requestFocus();
                return false;
            }
            if (!UserNewPass.equals(UserConfpass)) {
                edtconfrmPassWord.setError(getString(R.string.str_passwordnotmatch));
                edtconfrmPassWord.requestFocus();
                return false;
            }
        } catch (Exception ex) {
            Log.e(TAG, "ValidatePassword :- "+ex.toString());
        }
        return true;
    }
}