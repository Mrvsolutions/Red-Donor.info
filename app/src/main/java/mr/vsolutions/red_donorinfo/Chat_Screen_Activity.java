package mr.vsolutions.red_donorinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.Msgdata;
import mr.vsolutions.red_donorinfo.model.MsgdataMain;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat_Screen_Activity extends AppCompatActivity implements View.OnClickListener {

    public ImageView imgtoolprofilephoto,imgback,imgsend;
    public LinearLayout llcustomesearchview;
    EditText edtmsgtext;
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
        imgsend = findViewById(R.id.imgsend);
        edtmsgtext = findViewById(R.id.edtmsgtext);
        ChatelistRecycler = findViewById(R.id.ChatelistRecycler);
        title.setText(getIntent().getStringExtra("RecieverName"));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        donor_id = Comman.CommanUserDetail.getDonorId();
        Recieved_ID = getIntent().getStringExtra("RecieverId");

        imgback.setOnClickListener(this);
        imgsend.setOnClickListener(this);
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
                        MsgdataMain MsglistResponse = response.body();
                        if (MsglistResponse.getSuccess() == 1) {
                            Toast.makeText(getApplicationContext(), MsglistResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            List<Msgdata> lstchat = MsglistResponse.getMsgdata();
                            SetAdapterData(lstchat);
                        } else {
                            Toast.makeText(getApplicationContext(), MsglistResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void SendMessage() {
        try {
            try {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<DefaultResponse> call = apiService.SendmsgAsync(donor_id,Recieved_ID,edtmsgtext.getText().toString().trim());
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        DefaultResponse msgResponse = response.body();
                        if (msgResponse.getSuccess() == 1) {
                            Toast.makeText(getApplicationContext(), msgResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            GetMsgDataList();
                        } else {
                            Toast.makeText(getApplicationContext(), msgResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgback:
                finish();
                break;
            case R.id.imgsend:
                if (!edtmsgtext.getText().toString().trim().isEmpty())
                {
                    SendMessage();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),getString(R.string.str_msgSendSuccess),Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}