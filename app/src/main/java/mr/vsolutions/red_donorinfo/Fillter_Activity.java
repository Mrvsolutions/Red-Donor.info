package mr.vsolutions.red_donorinfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fillter_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = Fillter_Activity.class.toString();
    public ImageView imgtoolprofilephoto, imgback;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome, rltoolbar;
    RadioGroup radioGender;
    TextView title;
    String strLat =  String.valueOf(Comman.Lantitude), strLong = String.valueOf(Comman.Longitude);
    Spinner spinnerbloodGroup, spinnerminage, spinnermaxage, spinnerdistance;
    String[] bloodgroup = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    String[] MinAge = new String[33];
    String[] MaxAge = new String[33];
    String[] distance = {"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000"};
    String strbloodgroup, strminage, strmaxage, strdistance, strgender;
    Button btn_Apply;

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
        btn_Apply = findViewById(R.id.btn_Apply);
        radioGender = findViewById(R.id.radioGender);
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int selectedId = radioGender.getCheckedRadioButtonId();
                //find the radiobutton by retunend id
                RadioButton radioGenderButton = findViewById(checkedId);
                strgender = radioGenderButton.getText().toString();
            }
        });
        title.setText(getString(R.string.str_filter));
        for (int i = 0; i < 33; i++) {
            int val = 18 + i;
            MinAge[i] = String.valueOf(val);
            MaxAge[i] = String.valueOf(val);
        }
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        imgback.setOnClickListener(this);
        btn_Apply.setOnClickListener(this);
        ArrayAdapter adapterbldg = new ArrayAdapter(
                this,
                R.layout.spinner_row_layout, R.id.txtvalue,
                bloodgroup);
        spinnerbloodGroup.setAdapter(adapterbldg);
        spinnerbloodGroup.setOnItemSelectedListener(this);
        ArrayAdapter adapterminage = new ArrayAdapter(
                this,
                R.layout.spinner_row_layout, R.id.txtvalue,
                MinAge);
        spinnerminage.setAdapter(adapterminage);
        spinnerminage.setOnItemSelectedListener(this);
        ArrayAdapter adaptermaxage = new ArrayAdapter(
                this,
                R.layout.spinner_row_layout, R.id.txtvalue,
                MaxAge);
        spinnermaxage.setAdapter(adaptermaxage);
        spinnermaxage.setOnItemSelectedListener(this);
        ArrayAdapter adapterdistance = new ArrayAdapter(
                this,
                R.layout.spinner_row_layout, R.id.txtvalue,
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
    protected void onDestroy() {
        super.onDestroy();
        strbloodgroup = "";
        strminage = "";
        strmaxage = "";
        strdistance = "";
        strgender = "";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            Spinner spinblg = (Spinner) parent;
            if (spinblg.getId() == R.id.spinnerbloodGroup) {
                strbloodgroup = bloodgroup[position];
            } else if (spinblg.getId() == R.id.spinnerminage) {
                strminage = MinAge[position];
            } else if (spinblg.getId() == R.id.spinnermaxage) {
                strmaxage = MaxAge[position];
            } else if (spinblg.getId() == R.id.spinnerdistance) {
                strdistance = distance[position];
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void GetFilterData() {
        try {
            try {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<DonorDataMain> call = apiService.GetFilterDataAsync(strbloodgroup, strminage, strmaxage, strgender,strdistance, strLat, strLong);
                call.enqueue(new Callback<DonorDataMain>() {
                    @Override
                    public void onResponse(Call<DonorDataMain> call, Response<DonorDataMain> response) {
                        DonorDataMain LoginResponse = response.body();
                        if (LoginResponse.getSuccess() == 1) {
                            Toast.makeText(getApplicationContext(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            List<DonorDataMain.Donordata> lstuserdetail = LoginResponse.getDonordata();
                            if (lstuserdetail.size() > 0) {
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.putExtra("IsFromFilter", true);
                                i.putExtra("filterlist", (Serializable) lstuserdetail);
                                startActivity(i);
                                finishAffinity();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DonorDataMain> call, Throwable t) {
                        // Log error here since request failed
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.toString());
                    }
                });
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        } catch (Exception ex) {
            Log.e(TAG, "GetMsgDataList - " + ex.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgback:
                finish();
                break;
            case R.id.btn_Apply:
                if (ValidateFilter())
                {
                    GetFilterData();
                }
                break;
        }
    }

    private boolean ValidateFilter() {

        try {
            if (strbloodgroup.isEmpty() && strminage.isEmpty() && strmaxage.isEmpty() && strdistance.isEmpty()) {
                Toast.makeText(this, getString(R.string.str_FilterValidation), Toast.LENGTH_LONG).show();
                return false;
            }

        } catch (Exception ex) {
            Log.e(TAG, "ValidateFilter - "+ ex.toString());
        }
        return true;
    }
}