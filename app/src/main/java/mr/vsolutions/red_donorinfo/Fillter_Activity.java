package mr.vsolutions.red_donorinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import mr.vsolutions.red_donorinfo.adapter.RecyclerViewChateAdapter;
import mr.vsolutions.red_donorinfo.model.PlacesItem;

public class Fillter_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public ImageView imgtoolprofilephoto, imgback;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome, rltoolbar;
    TextView title;
    Spinner spinnerbloodGroup, spinnerminage, spinnermaxage, spinnerdistance;
    String[] bloodgroup = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    String[] MinAge = new String[33];
    String[] MaxAge = new String[33];
    String[] distance = {"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000"};
    String strbloodgroup,strminage,strmaxage,strdistance;
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
        spinnerbloodGroup = findViewById(R.id.spinnerbloodGroup);
        spinnerminage = findViewById(R.id.spinnerminage);
        spinnermaxage = findViewById(R.id.spinnermaxage);
        spinnerdistance = findViewById(R.id.spinnerdistance);
        title.setText(getString(R.string.str_filter));
        for (int i = 0 ; i < 33; i++ )
        {
            int val = 18 + i;
            MinAge[i] = String.valueOf(val);
            MaxAge[i] = String.valueOf(val);
        }
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ArrayAdapter adapterbldg = new ArrayAdapter(
                this,
                R.layout.spinner_row_layout,R.id.txtvalue,
                bloodgroup);
        spinnerbloodGroup.setAdapter(adapterbldg);
        spinnerbloodGroup.setOnItemSelectedListener(this);
        ArrayAdapter adapterminage = new ArrayAdapter(
                this,
                R.layout.spinner_row_layout,R.id.txtvalue,
                MinAge);
        spinnerminage.setAdapter(adapterminage);
        spinnerminage.setOnItemSelectedListener(this);
        ArrayAdapter adaptermaxage = new ArrayAdapter(
                this,
                R.layout.spinner_row_layout,R.id.txtvalue,
                MaxAge);
        spinnermaxage.setAdapter(adaptermaxage);
        spinnermaxage.setOnItemSelectedListener(this);
        ArrayAdapter adapterdistance = new ArrayAdapter(
                this,
                R.layout.spinner_row_layout,R.id.txtvalue,
                distance);
        spinnerdistance.setAdapter(adapterdistance);
        spinnerdistance.setOnItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            Spinner spinblg = (Spinner)parent;
            if (spinblg.getId() == R.id.spinnerbloodGroup) {
                strbloodgroup = bloodgroup[position];
            }
            else if (spinblg.getId() == R.id.spinnerminage)
            {
                strminage = MinAge[position];
            }
            else if (spinblg.getId() == R.id.spinnermaxage)
            {
                strmaxage = MinAge[position];
            }
            else if (spinblg.getId() == R.id.spinnerdistance)
            {
                strdistance = distance[position];
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}