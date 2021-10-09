package mr.vsolutions.red_donorinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import mr.vsolutions.red_donorinfo.fragment.MessageFragment;
import mr.vsolutions.red_donorinfo.fragment.HomeFragment;
import mr.vsolutions.red_donorinfo.fragment.NotificationFragment;
import mr.vsolutions.red_donorinfo.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    public ImageView imgtoolprofilephoto,imgfilter;
    public LinearLayout llcustomesearchview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        imgfilter = findViewById(R.id.imgfilter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationview);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistner);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
        Glide.with(this)
                .load("http://www.freeinfo.in/admin/photos/Kinjal_Dave_503%20(Mr.%20V%20Solutions).jpg")
                .apply(new RequestOptions().centerCrop())
                .into(imgtoolprofilephoto);
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