package mr.vsolutions.red_donorinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Complain_Activity extends AppCompatActivity implements View.OnClickListener {

    public ImageView imgtoolprofilephoto,imgback,imgprofilephoto;
    public LinearLayout llcustomesearchview;
    TextView title;
    EditText edttitle, edtDescription;
    Button btn_Send,btn_Cancel;
    String Donor_Id;
    ProgressDialog mProgressDialog;
    RelativeLayout rltoolbarhome,rltoolbar;
    private static final String TAG = Complain_Activity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        getSupportActionBar().hide();
        imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
        imgprofilephoto = findViewById(R.id.imgprofilephoto);
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        rltoolbarhome = findViewById(R.id.rltoolbarhome);
        rltoolbar = findViewById(R.id.rltoolbar);
        title = findViewById(R.id.title);
        imgback = findViewById(R.id.imgback);
        btn_Send = findViewById(R.id.btn_Send);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        edttitle = findViewById(R.id.edttitle);
        edtDescription = findViewById(R.id.edtDescription);
        title.setText(getString(R.string.str_Report));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(10);
        mProgressDialog.setMax(100);
        Donor_Id = getIntent().getStringExtra("Donor_Id");

//        Glide.with(this)
//                .load(R.drawable.ic_person_placeholder)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .apply(new RequestOptions().centerCrop())
//                .into(imgprofilephoto);
        imgback.setOnClickListener(this);
        btn_Send.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Send:
                SendDonorComplainCall();
                break;
            case R.id.imgback:
                onBackPressed();
                break;
            case R.id.btn_Cancel:
                onBackPressed();
                break;
        }
    }
    private void SendDonorComplainCall() {
        try {
            if (Validatereview()) {
                try {
                    if (!mProgressDialog.isShowing()) {
                        mProgressDialog.show();
                    }
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<DefaultResponse> call = apiService.SendDonorComplainAsync(Comman.CommanUserDetail.getDonorId(),Donor_Id,edttitle.getText().toString().trim(),edtDescription.getText().toString().trim());
                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                            DefaultResponse LoginResponse = response.body();
                            if (LoginResponse.getSuccess() == 1) {
                                Toast.makeText(getApplicationContext(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            Log.e(TAG,"SendDonorComplainCall - "+ ex.toString());
        }
    }
    private boolean Validatereview() {
        try {
            if (edttitle.getText().toString().trim().isEmpty()) {
                edttitle.setError(getString(R.string.str_errortitle));
                edttitle.requestFocus();
                return false;
            }
            if (edtDescription.getText().toString().trim().isEmpty()) {
                edtDescription.setError(getString(R.string.str_errorDescription));
                edtDescription.requestFocus();
                return false;
            }

        } catch (Exception ex) {
            Log.e(TAG, "Validatereview :- "+ex.toString());
        }
        return true;
    }
}