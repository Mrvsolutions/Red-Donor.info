package mr.vsolutions.red_donorinfo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_LOCATION = 1;
    ImageView imgback;
    Button btn_Signup;
    EditText edtName, edtmobileno, edtEmail, edtpassword, edtconfpassword,edtcity;
    CheckBox cb_agree;
    LocationManager locationManager;
    String latitude = "22.564518", longitude = "72.928871";
    String username, usermobile, useremail, UserPass, UserConfpass, UserCity ;
    ProgressDialog mProgressDialog;
    private static final String TAG = SignupActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        imgback = findViewById(R.id.imgback);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        TextView txtnewuser =  findViewById(R.id.txtnewuser);
        btn_Signup =  findViewById(R.id.btn_Signup);
        cb_agree =  findViewById(R.id.cb_agree);
        edtName =  findViewById(R.id.edtName);
        edtmobileno =  findViewById(R.id.edtmobileno);
        edtEmail =  findViewById(R.id.edtEmail);
        edtpassword =  findViewById(R.id.edtpassword);
        edtconfpassword = findViewById(R.id.edtconfpassword);
        edtcity=  findViewById(R.id.edtcity);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            OnGPS();
//        } else {
//            getLocation();
//        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(10);
        mProgressDialog.setMax(100);
        SpannableString str1 = new SpannableString("Already have an account?");
        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorbottomtext)), 0, str1.length(), 0);
        builder.append(str1);

        SpannableString str2 = new SpannableString(" Log In");
        str2.setSpan(new ForegroundColorSpan(Color.RED), 0, str2.length(), 0);
        builder.append(str2);

        txtnewuser.setText(builder, TextView.BufferType.SPANNABLE);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Signup:
                CreateUser();
                break;
        }
    }

    private void CreateUser() {
        username = edtName.getText().toString().trim();
        usermobile = edtmobileno.getText().toString().trim();
        useremail = edtEmail.getText().toString().trim();
        UserPass = edtpassword.getText().toString().trim();
        UserConfpass = edtconfpassword.getText().toString().trim();
        UserCity = edtcity.getText().toString().trim();
        if (ValidateUser()) {
            try {
                if (!mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<DefaultResponse> call = apiService.CreateUserCall(username, UserCity, useremail, UserPass, usermobile, latitude, longitude);
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        DefaultResponse defaultResponse = response.body();
                        if (defaultResponse.getSuccess() == 1) {
                            Toast.makeText(SignupActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            SharedPreferences  sharedpreferences = getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Comman.signupComplete,getString(R.string.str_signupComplete));
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(),VerifyEmailCodeActivity.class);
                            intent.putExtra("useremail", useremail);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        // Log error here since request failed
                        Toast.makeText(SignupActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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


    private boolean ValidateUser() {
        String MobilePattern = "[0-9]{10}";
        try {
            if (username.isEmpty()) {
                edtName.setError(getString(R.string.str_entername));
                edtName.requestFocus();
                return false;
            }
            if (usermobile.isEmpty()) {
                edtmobileno.setError(getString(R.string.str_enterMobile));
                edtmobileno.requestFocus();
                return false;
            }
            if (!usermobile.isEmpty() && !usermobile.matches(MobilePattern)) {
                edtmobileno.setError(getString(R.string.str_enterValidMobile));
                edtmobileno.requestFocus();
                return false;
            }
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
            if (UserCity.isEmpty()) {
                edtcity.setError(getString(R.string.str_entrCity));
                edtcity.requestFocus();
                return false;
            }
            if (UserPass.isEmpty()) {
                edtpassword.setError(getString(R.string.str_enterPass));
                edtpassword.requestFocus();
                return false;
            }
            if (UserConfpass.isEmpty()) {
                edtconfpassword.setError(getString(R.string.str_enterConfPass));
                edtconfpassword.requestFocus();
                return false;
            }
            if (!UserPass.equals(UserConfpass)) {
                edtconfpassword.setError(getString(R.string.str_passwordnotmatch));
                edtconfpassword.requestFocus();
                return false;
            }
            if (!cb_agree.isChecked())
            {
                Toast.makeText(this, getString(R.string.str_agreetems), Toast.LENGTH_LONG).show();
                return false;
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return true;
    }

    private void OnGPS() {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    private void getLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationGPS != null) {
                    double lat = locationGPS.getLatitude();
                    double longi = locationGPS.getLongitude();
                    latitude = String.valueOf(lat);
                    longitude = String.valueOf(longi);
                    //  showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
                } else {
                    Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }
}