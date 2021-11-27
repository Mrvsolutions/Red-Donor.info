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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import mr.vsolutions.red_donorinfo.fragment.MessageFragment;
import mr.vsolutions.red_donorinfo.fragment.HomeFragment;
import mr.vsolutions.red_donorinfo.fragment.NotificationFragment;
import mr.vsolutions.red_donorinfo.fragment.SettingsFragment;
import mr.vsolutions.red_donorinfo.model.UserDetail;
import mr.vsolutions.red_donorinfo.util.Comman;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    public ImageView imgtoolprofilephoto,imgfilter;
    public LinearLayout llcustomesearchview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        GetToken();
        imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        imgfilter = findViewById(R.id.imgfilter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationview);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistner);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
        Glide.with(this)
                .load("https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI")
                .apply(new RequestOptions().centerCrop())
                .into(imgtoolprofilephoto);
        SharedPreferences  sharedpreferences = getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
        String usrdata  = sharedpreferences.getString(Comman.strCommanuserdetai, "");
        if (!usrdata.isEmpty())
        {
            Gson gson = new Gson();
            UserDetail obj = gson.fromJson(usrdata, UserDetail.class);
            Comman.CommanUserDetail = obj;
        }

        imgfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Fillter_Activity.class);
                startActivity(i);
            }
        });
        imgtoolprofilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Profile_Activity.class);
                startActivity(i);
            }
        });
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
                    case R.id.nav_notifications:
                        SelectedFragment = new NotificationFragment();
                        break;
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
                // Log and toast
                // String msg = token);
                Log.d(TAG, token);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if(f instanceof HomeFragment)
        {
           if (((HomeFragment) f).markerRecycler.getVisibility() == View.VISIBLE)
           {
               ((HomeFragment) f).markerRecycler.setVisibility(View.GONE);
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
}