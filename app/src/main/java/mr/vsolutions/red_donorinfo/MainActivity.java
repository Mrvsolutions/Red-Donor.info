package mr.vsolutions.red_donorinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.List;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.fragment.MessageFragment;
import mr.vsolutions.red_donorinfo.fragment.HomeFragment;
import mr.vsolutions.red_donorinfo.fragment.SettingsFragment;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.UserDetail;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static mr.vsolutions.red_donorinfo.util.Comman.checkAndRequestLocationPermissions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    public ImageView imgtoolprofilephoto,imgfilter;
    public LinearLayout llcustomesearchview,llsearch;
    public  boolean IsFromFilter;
    public List<DonorDataMain.Donordata> FilterItemArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            getSupportActionBar().hide();
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            GetToken();
            IsFromFilter = getIntent().getBooleanExtra("IsFromFilter", false);
            if (checkAndRequestLocationPermissions(this))
                if (IsFromFilter) {
                    FilterItemArrayList = Comman.FilterItemArrayList;//(List<DonorDataMain.Donordata>) getIntent().getExtras().getSerializable("filterlist");
                }
            imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
            llcustomesearchview = findViewById(R.id.llcustomesearchview);
            imgfilter = findViewById(R.id.imgfilter);
            llsearch = findViewById(R.id.llsearch);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationview);
            bottomNavigationView.setOnNavigationItemSelectedListener(navlistner);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
            SharedPreferences sharedpreferences = getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
            String usrdata = sharedpreferences.getString(Comman.strCommanuserdetai, "");
            if (!usrdata.isEmpty()) {
                Gson gson = new Gson();
                UserDetail obj = gson.fromJson(usrdata, UserDetail.class);
                Comman.CommanUserDetail = obj;
                Glide.with(this)
                        .load(Comman.CommanUserDetail.getDonorProfilePic())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .apply(new RequestOptions().centerCrop())
                        .error(R.drawable.ic_person_placeholder)
                        .into(imgtoolprofilephoto);
                UserDeviceRegisterCall(Comman.CommanToken, Comman.CommanUserDetail.getDonorId());
            }

            llsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), Fillter_Activity.class);
                    startActivity(i);
                }
            });
            imgtoolprofilephoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!usrdata.isEmpty()) {
                        Intent i = new Intent(getApplicationContext(), Profile_Activity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
        catch (Exception ex)
        {
            Log.e("MainActivity oncreate-",ex.getMessage());
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlistner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            try {
                Fragment SelectedFragment = null;
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        SelectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_chate:
                        SelectedFragment = new MessageFragment();
                        break;
//                    case R.id.nav_notifications:
//                        SelectedFragment = new NotificationFragment();
//                        break;
                    case R.id.nav_setting:
                        SelectedFragment = new SettingsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,SelectedFragment).commit();
                return  true;
            }
            catch (Exception ex)
            {
                Log.e("MainActivity error",ex.getMessage());
            }
            return false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Comman.FilterItemArrayList = null;
    }


    void GetToken() {
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }
                // Get new FCM registration token
                String token = task.getResult();
                Comman.CommanToken = token;

                // Log and toast
                // String msg = token);
                Log.d(TAG, "Token: - "+token);
            //    Toast.makeText(MainActivity.this, "Token: - "+token, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if(f instanceof HomeFragment)
        {
           if (((HomeFragment) f).markerRecyclerhorizontal.getVisibility() == View.VISIBLE)
           {
               ((HomeFragment) f).markerRecyclerhorizontal.setVisibility(View.GONE);
               ((HomeFragment) f).imgremove.setVisibility(View.GONE);
               ((HomeFragment) f).rlremoveview.setVisibility(View.GONE);
           }
           else
           {
               super.onBackPressed();
           }
        }
        else
        {
            super.onBackPressed();
        }
    }
    private void UserDeviceRegisterCall(String Token,String DonorID) {
        try {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<DefaultResponse> call = apiService.DeviceRegisterAsync(Token, DonorID);
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    DefaultResponse defaultResponse = response.body();
                    if (defaultResponse.getSuccess() == 1) {
                       // Toast.makeText(MainActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                     //   Toast.makeText(MainActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    // Log error here since request failed
                 //   Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, t.toString());
                }
            });
        }
        catch (Exception ex)
        {
            Log.e(TAG,"UserDeviceRegisterCall - "+ ex.toString());
        }
    }
}