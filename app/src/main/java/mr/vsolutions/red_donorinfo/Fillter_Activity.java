package mr.vsolutions.red_donorinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import mr.vsolutions.red_donorinfo.adapter.RecyclerViewChateAdapter;
import mr.vsolutions.red_donorinfo.model.PlacesItem;

public class Fillter_Activity extends AppCompatActivity {

    public ImageView imgtoolprofilephoto,imgback;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome,rltoolbar;
    TextView title;
    private RecyclerView ChatelistRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);
        getSupportActionBar().hide();
        imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        rltoolbarhome = findViewById(R.id.rltoolbarhome);
        rltoolbar = findViewById(R.id.rltoolbar);
        title = findViewById(R.id.title);
        imgback = findViewById(R.id.imgback);
        ChatelistRecycler = findViewById(R.id.ChatelistRecycler);
        title.setText(getString(R.string.str_filter));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
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