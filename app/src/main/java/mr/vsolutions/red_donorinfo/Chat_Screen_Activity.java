package mr.vsolutions.red_donorinfo;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

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
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10*1000;
    private RecyclerView ChatelistRecycler;
    String donor_id,Recieved_ID,RecieverProfilepic;
    DonorDataMain.Donordata RecieverData;
    RecyclerViewChateAdapter indicatorAdapter;
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
        RecieverProfilepic = getIntent().getStringExtra("RecieverProfilepic");
        imgback.setOnClickListener(this);
        imgsend.setOnClickListener(this);
        GetMsgDataList(false,false);
    }


    @Override
    public void onBackPressed() {
       super.onBackPressed();
        finish();
    }

    private void GetMsgDataList(boolean isFromTimer,boolean IsfromSend) {
        try {
            try {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<MsgdataMain> call = apiService.GetmsgdataList(donor_id,Recieved_ID);
                call.enqueue(new Callback<MsgdataMain>() {
                    @Override
                    public void onResponse(Call<MsgdataMain> call, Response<MsgdataMain> response) {
                        MsgdataMain MsglistResponse = response.body();
                        if (MsglistResponse.getSuccess() == 1) {
                         //   Toast.makeText(getApplicationContext(), MsglistResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            List<Msgdata> lstchat = MsglistResponse.getMsgdata();
                            if (isFromTimer || IsfromSend)
                            {
                                if (IsfromSend)
                                {
                                    edtmsgtext.setText("");
                                }
                                if (indicatorAdapter != null)
                                {
                                    indicatorAdapter.Update(lstchat);
                                }
                                else
                                {
                                    SetAdapterData(lstchat);
                                }
                            }
                            else {
                                SetAdapterData(lstchat);
                            }
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
                String currentDate  = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<DefaultResponse> call = apiService.SendmsgAsync(donor_id,Recieved_ID,edtmsgtext.getText().toString().trim(),currentDate,currentTime);
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        DefaultResponse msgResponse = response.body();
                        if (msgResponse.getSuccess() == 1) {
                            Toast.makeText(getApplicationContext(), msgResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            GetMsgDataList(true,true);
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
            indicatorAdapter = new RecyclerViewChateAdapter(lstchat, Recieved_ID,RecieverProfilepic,this);
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

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                GetMsgDataList(true,false);
                handler.postDelayed(runnable, delay);
            }
        }, delay);
    }
}