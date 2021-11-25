package mr.vsolutions.red_donorinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewChateAdapter;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.Msgdata;
import mr.vsolutions.red_donorinfo.model.MsgdataMain;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat_Screen_Activity extends AppCompatActivity {

    public ImageView imgtoolprofilephoto,imgback;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome,rltoolbar;
    TextView title;
    private RecyclerView ChatelistRecycler;
    String donor_id,Recieved_ID;
    DonorDataMain.Donordata RecieverData;
    private static final String TAG = Chat_Screen_Activity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        getSupportActionBar().hide();
        imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        rltoolbarhome = findViewById(R.id.rltoolbarhome);
        rltoolbar = findViewById(R.id.rltoolbar);
        title = findViewById(R.id.title);
        imgback = findViewById(R.id.imgback);
        ChatelistRecycler = findViewById(R.id.ChatelistRecycler);
        title.setText(getIntent().getStringExtra("RecieverName"));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        donor_id = Comman.CommanUserDetail.getDonorId();
        Recieved_ID = getIntent().getStringExtra("RecieverId");

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        GetMsgDataList();
    }


    @Override
    public void onBackPressed() {
       super.onBackPressed();
        finish();
    }

    private void GetMsgDataList() {
        try {
            try {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<MsgdataMain> call = apiService.GetmsgdataList(donor_id,Recieved_ID);
                call.enqueue(new Callback<MsgdataMain>() {
                    @Override
                    public void onResponse(Call<MsgdataMain> call, Response<MsgdataMain> response) {
                        MsgdataMain LoginResponse = response.body();
                        if (LoginResponse.getSuccess() == 1) {
                            Toast.makeText(getApplicationContext(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            List<Msgdata> lstchat = LoginResponse.getMsgdata();
                            SetAdapterData(lstchat);
                        } else {
                            Toast.makeText(getApplicationContext(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MsgdataMain> call, Throwable t) {
                        // Log error here since request failed
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.toString());
                    }
                });
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG,"GetMsgDataList - "+ ex.toString());
        }
    }

    private void SetAdapterData(List<Msgdata> lstchat) {
        try {
            RecyclerView.Adapter indicatorAdapter = new RecyclerViewChateAdapter(lstchat, Recieved_ID,this);
            ChatelistRecycler.setLayoutManager(new GridLayoutManager(this,1, RecyclerView.VERTICAL,false));
            ChatelistRecycler.setAdapter(indicatorAdapter);
            ChatelistRecycler.scrollToPosition(lstchat.size()-1);
        }
        catch (Exception ex)
        {
            Log.e(TAG,"SetAdapterData - "+ ex.toString());
        }
    }

}