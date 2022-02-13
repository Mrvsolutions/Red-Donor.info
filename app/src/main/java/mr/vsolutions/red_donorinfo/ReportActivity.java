package mr.vsolutions.red_donorinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewReportAdapter;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.DonorReviewSummary;
import mr.vsolutions.red_donorinfo.model.Donordatum;
import mr.vsolutions.red_donorinfo.model.Donorsummary;
import mr.vsolutions.red_donorinfo.model.PlacesItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener{

    public ImageView imgback;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome,rltoolbar;
    TextView title;
    RecyclerView reportlistRecycler;
    String Donor_id = null;
    private static final String TAG = ReportActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().hide();
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        rltoolbarhome = findViewById(R.id.rltoolbarhome);
        rltoolbar = findViewById(R.id.rltoolbar);
        title = findViewById(R.id.title);
        reportlistRecycler = findViewById(R.id.reportlistRecycler);
        imgback = findViewById(R.id.imgback);
        title.setText(getIntent().getStringExtra("titleheading"));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        Donor_id = getIntent().getStringExtra("Donor_Id");
        ArrayList<PlacesItem> allPlaces = new ArrayList<PlacesItem>();

        imgback.setOnClickListener(this);
        GetDonorReportList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgback:
                finish();
                break;
        }
    }

    private void GetDonorReportList() {
        try {
            try {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<DonorReviewSummary> call = apiService.GetDonorReViewSummaryList(Donor_id);
                call.enqueue(new Callback<DonorReviewSummary>() {
                    @Override
                    public void onResponse(Call<DonorReviewSummary> call, Response<DonorReviewSummary> response) {
                        DonorReviewSummary Response = response.body();
                        if (Response.getSuccess() == 1) {
                            //Toast.makeText(getApplicationContext(), Response.getMessage(), Toast.LENGTH_SHORT).show();
                            List<Donordatum> lstuserdetail = Response.getDonordata();
                            Donorsummary donorsummary = Response.getDonorsummary().get(0);
                          //  placesItemArrayList = lstuserdetail;
                           // SetMarkerLocations();
                            SetAdapterData(lstuserdetail,donorsummary);
                        } else {
                         //   Toast.makeText(getApplicationContext(), Response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DonorReviewSummary> call, Throwable t) {
                        // Log error here since request failed
                     //   Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.toString());
                    }
                });
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG,"GetDonorReportList - "+ ex.toString());
        }
    }

    private void SetAdapterData(List<Donordatum> lstuserdetail,Donorsummary donorsummary) {
        try {
            RecyclerView.Adapter indicatorAdapter = new RecyclerViewReportAdapter(lstuserdetail,donorsummary,this);
            reportlistRecycler.setLayoutManager(new GridLayoutManager(this,1, RecyclerView.VERTICAL,false));
            reportlistRecycler.setAdapter(indicatorAdapter);
        }
        catch (Exception ex)
        {
            Log.e(TAG,"SetAdapterData - "+ ex.toString());
        }
    }
}