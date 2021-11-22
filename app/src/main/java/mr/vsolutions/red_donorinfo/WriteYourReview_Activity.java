package mr.vsolutions.red_donorinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class WriteYourReview_Activity extends AppCompatActivity implements View.OnClickListener {

    public ImageView imgtoolprofilephoto,imgback,imgprofilephoto;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome,rltoolbar;
    TextView title,txtname,txtage,txtaddress;
    RatingBar ratbar;
    EditText edtyourexperiance,edtheadline;
    Button btn_Submit;
    String reviewHeadline, reviewExperiance;
    ProgressDialog mProgressDialog;
    private static final String TAG = WriteYourReview_Activity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review_layout);
        getSupportActionBar().hide();
        imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
        imgprofilephoto = findViewById(R.id.imgprofilephoto);
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        rltoolbarhome = findViewById(R.id.rltoolbarhome);
        rltoolbar = findViewById(R.id.rltoolbar);
        title = findViewById(R.id.title);
        imgback = findViewById(R.id.imgback);
        txtname = findViewById(R.id.txtname);
        txtage = findViewById(R.id.txtage);
        txtaddress = findViewById(R.id.txtaddress);
        ratbar = findViewById(R.id.ratbar);
        btn_Submit = findViewById(R.id.btn_Submit);
        edtyourexperiance = findViewById(R.id.edtyourexperiance);
        edtheadline = findViewById(R.id.edtheadline);
        title.setText(getString(R.string.str_WriteaReview));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(10);
        mProgressDialog.setMax(100);
        if (Comman.CommanUserDetail != null)
        {
            txtname.setText(Comman.CommanUserDetail.getDonorName());
            txtage.setText("Age: "+Comman.CommanUserDetail.getDonorAge());
            txtaddress.setText("Address: "+Comman.CommanUserDetail.getDonorAddress());
        }
        Glide.with(this)
                .load("https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI")
                .apply(new RequestOptions().centerCrop())
                .into(imgprofilephoto);
        imgback.setOnClickListener(this);
        btn_Submit.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
       super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Submit:
                SendUserReviewCall();
                break;
            case R.id.imgback:
                onBackPressed();
                break;
        }
    }
    private void SendUserReviewCall() {
        try {
            reviewHeadline = edtyourexperiance.getText().toString().trim();
            reviewExperiance = edtheadline.getText().toString().trim();
            String rating = String.valueOf(ratbar.getRating());
            if (Validatereview()) {
                try {
                    if (!mProgressDialog.isShowing()) {
                        mProgressDialog.show();
                    }
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<DefaultResponse> call = apiService.SendDonorReviewCall(Comman.CommanUserDetail.getDonorId(),Comman.CommanUserDetail.getDonorName(),rating,reviewHeadline,reviewExperiance);
                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            DefaultResponse LoginResponse = response.body();
                            if (LoginResponse.getSuccess() == 1) {
                                Toast.makeText(WriteYourReview_Activity.this, LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(WriteYourReview_Activity.this, LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
                            // Log error here since request failed
                            Toast.makeText(WriteYourReview_Activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
            Log.e(TAG,"SendUserReviewCall - "+ ex.toString());
        }
    }
    private boolean Validatereview() {
        try {
            if (reviewExperiance.isEmpty()) {
                edtyourexperiance.setError(getString(R.string.str_shareyourExperiance));
                edtyourexperiance.requestFocus();
                return false;
            }
            if (reviewHeadline.isEmpty()) {
                edtheadline.setError(getString(R.string.str_shareheadline));
                edtheadline.requestFocus();
                return false;
            }

        } catch (Exception ex) {
            Log.e(TAG, "Validatereview :- "+ex.toString());
        }
        return true;
    }
}