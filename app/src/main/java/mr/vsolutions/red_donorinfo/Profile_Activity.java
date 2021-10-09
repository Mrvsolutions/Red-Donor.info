package mr.vsolutions.red_donorinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Profile_Activity extends AppCompatActivity {

    public ImageView imgtoolprofilephoto,imgback,imgprofilephoto;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome,rltoolbar;
    TextView title;
    private RecyclerView ChatelistRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        getSupportActionBar().hide();
        imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
        imgprofilephoto = findViewById(R.id.imgprofilephoto);
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        rltoolbarhome = findViewById(R.id.rltoolbarhome);
        rltoolbar = findViewById(R.id.rltoolbar);
        title = findViewById(R.id.title);
        imgback = findViewById(R.id.imgback);
        ChatelistRecycler = findViewById(R.id.ChatelistRecycler);
        title.setText(getString(R.string.str_Profile));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load("http://www.freeinfo.in/admin/photos/Kinjal_Dave_503%20(Mr.%20V%20Solutions).jpg")
                .apply(new RequestOptions().centerCrop())
                .into(imgprofilephoto);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
       super.onBackPressed();
        finish();
    }
}