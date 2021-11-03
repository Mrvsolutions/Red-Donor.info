package mr.vsolutions.red_donorinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewReportAdapter;
import mr.vsolutions.red_donorinfo.model.PlacesItem;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener{

    public ImageView imgback;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome,rltoolbar;
    TextView title;
    RecyclerView reportlistRecycler;
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
        title.setText(getString(R.string.str_reporttitle));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        ArrayList<PlacesItem> allPlaces = new ArrayList<PlacesItem>();
        RecyclerView.Adapter indicatorAdapter = new RecyclerViewReportAdapter(allPlaces,this);
        reportlistRecycler.setLayoutManager(new GridLayoutManager(this,1, RecyclerView.VERTICAL,false));
        reportlistRecycler.setAdapter(indicatorAdapter);
        imgback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgback:
                finish();
                break;
        }
    }
}