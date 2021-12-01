package mr.vsolutions.red_donorinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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

public class Profile_Activity extends AppCompatActivity implements View.OnClickListener {

    public ImageView imgtoolprofilephoto,imgback,imgprofilephoto;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome,rltoolbar;
    TextView title,txtuser_name,txtEdit;
    EditText edtCity,edtEmail,edtmobileno,edtAge,edtBloodGroup,edtAddress;
    String username, usermobile, useremail, userAge, userBloodGroup, userCity, userAddress;
    ProgressDialog mProgressDialog;
    private static final String TAG = Profile_Activity.class.getSimpleName();
    CardView cardviewCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        getSupportActionBar().hide();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(10);
        mProgressDialog.setMax(100);

        imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
        imgprofilephoto = findViewById(R.id.imgprofilephoto);
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        rltoolbarhome = findViewById(R.id.rltoolbarhome);
        rltoolbar = findViewById(R.id.rltoolbar);
        title = findViewById(R.id.title);
        imgback = findViewById(R.id.imgback);
        edtCity = findViewById(R.id.edtCity);
        edtEmail = findViewById(R.id.edtEmail);
        edtmobileno = findViewById(R.id.edtmobileno);
        edtAge = findViewById(R.id.edtAge);
        edtBloodGroup = findViewById(R.id.edtBloodGroup);
        edtAddress = findViewById(R.id.edtAddress);
        txtuser_name = findViewById(R.id.txtuser_name);
        cardviewCamera = findViewById(R.id.cardviewCamera);
        txtEdit = findViewById(R.id.txtEdit);
        txtEdit.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.str_Profile));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load("https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI")
                .apply(new RequestOptions().centerCrop())
                .into(imgprofilephoto);
        if (Comman.CommanUserDetail != null)
        {
            txtEdit.setVisibility(View.VISIBLE);
            txtuser_name.setText(Comman.CommanUserDetail.getDonorName());
            edtAge.setText(Comman.CommanUserDetail.getDonorAge());
            edtAddress.setText(Comman.CommanUserDetail.getDonorAddress());
            edtBloodGroup.setText(Comman.CommanUserDetail.getDonorBloodGroup());
            edtCity.setText(Comman.CommanUserDetail.getDonorCity());
            edtmobileno.setText(Comman.CommanUserDetail.getDonorMobileno());
            edtEmail.setText(Comman.CommanUserDetail.getDonorEmail());
        }
        txtEdit.setOnClickListener(this);
        imgback.setOnClickListener(this);
        cardviewCamera.setOnClickListener(this);
        SetEnableDisable();
    }

    private void SetEnableDisable() {
        if (txtEdit.getText().equals(getString(R.string.str_Edit)))
        {
            edtAge.setEnabled(false);
            edtEmail.setEnabled(false);
            edtAddress.setEnabled(false);
            edtCity.setEnabled(false);
            edtmobileno.setEnabled(false);
            edtBloodGroup.setEnabled(false);
        }
        else
        {
            edtAge.setEnabled(true);
            edtEmail.setEnabled(true);
            edtAddress.setEnabled(true);
            edtCity.setEnabled(true);
            edtmobileno.setEnabled(true);
            edtBloodGroup.setEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtEdit:
                if (txtEdit.getText().equals(getString(R.string.str_Edit))) {
                    txtEdit.setText(getString(R.string.str_Save));
                }
                else
                {
                    EditUser();
                    txtEdit.setText(getString(R.string.str_Edit));
                }
                SetEnableDisable();
                break;
            case R.id.imgback:
                finish();
                break;
            case R.id.cardviewCamera:
                finish();
                break;
        }
    }
    private void EditUser() {
        username = txtuser_name.getText().toString().trim();
        usermobile = edtmobileno.getText().toString().trim();
        useremail = edtEmail.getText().toString().trim();
        userAge = edtAge.getText().toString().trim();
        userBloodGroup = edtBloodGroup.getText().toString().trim();
        userCity = edtCity.getText().toString().trim();
        userAddress = edtAddress.getText().toString().trim();
        try {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<LoginUser> call = apiService.EditUserDataCall(Comman.CommanUserDetail.getDonorId(),username, userCity,useremail,usermobile, userAge, userBloodGroup, userAddress);
            call.enqueue(new Callback<LoginUser>() {
                @Override
                public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                    LoginUser defaultResponse = response.body();
                    if (defaultResponse.getSuccess() == 1) {
                        List<UserDetail> lstuserdetail = defaultResponse.getUserdata();
                        Comman.CommanUserDetail = lstuserdetail.get(0);
                        Toast.makeText(Profile_Activity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Profile_Activity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<LoginUser> call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(Profile_Activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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