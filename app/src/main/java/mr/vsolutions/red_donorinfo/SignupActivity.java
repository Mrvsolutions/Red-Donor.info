package mr.vsolutions.red_donorinfo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final int REQUEST_LOCATION = 1;
    ImageView imgback;
    Button btn_Signup;
    EditText edtName, edtmobileno, edtEmail, edtpassword, edtconfpassword,edtcity, edtuserdateofbirth, edtuseraddress;
    CheckBox cb_agree;
    LocationManager locationManager;
    double latitude = Comman.Lantitude, longitude = Comman.Longitude;
    String username, usermobile, useremail, UserPass, UserConfpass, UserCity , UserGender = "",UserBirthDate,UserAddress;
    ProgressDialog mProgressDialog;
    RadioGroup radioGender;
    TextView txtselectedage,txtterms;
    private static final String TAG = SignupActivity.class.getSimpleName();
    String[] bloodgroup = {"Select Your Blood Group","A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
   // String[] MaxAge = new String[33];
    Spinner spinneruserbloodGroup;
    String strbloodgroup, strage;
    private int mYear,mMonth,mDay;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    PlacesClient placesClient;
    AutocompleteSupportFragment autocompleteFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        imgback = findViewById(R.id.imgback);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        TextView txtnewuser =  findViewById(R.id.txtnewuser);
        btn_Signup =  findViewById(R.id.btn_Signup);
        cb_agree =  findViewById(R.id.cb_agree);
        edtName =  findViewById(R.id.edtName);
        edtmobileno =  findViewById(R.id.edtmobileno);
        edtEmail =  findViewById(R.id.edtEmail);
        edtpassword =  findViewById(R.id.edtpassword);
        edtconfpassword = findViewById(R.id.edtconfpassword);
        edtcity=  findViewById(R.id.edtcity);
        txtselectedage =  findViewById(R.id.txtselectedage);
        txtterms =  findViewById(R.id.txtterms);
        spinneruserbloodGroup = findViewById(R.id.spinneruserbloodGroup);
      //  spinneruserage = findViewById(R.id.spinneruserage);
        radioGender = findViewById(R.id.radioGender);
        edtuserdateofbirth = findViewById(R.id.edtuserdateofbirth);
        edtuserdateofbirth.setKeyListener(null);
        edtuseraddress = findViewById(R.id.edtuseraddress);
        strbloodgroup = getString(R.string.str_selectBloodGroup);
        String apiKey = getString(R.string.str_PlaceAPI_Key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
         placesClient = Places.createClient(this);
        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                String Straddress = "Place: " + place.getName() + ", ID:- " + place.getId() +" , Address:- "+place.getAddress()+" , Lat:- "+place.getLatLng().latitude+" , Longt:- "+place.getLatLng().longitude;
                Log.i(TAG, Straddress);
                Toast.makeText(getApplicationContext(),Straddress,Toast.LENGTH_LONG).show();
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(getApplicationContext(),"Error: - "+status,Toast.LENGTH_LONG).show();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edtuserdateofbirth.setShowSoftInputOnFocus(false);
        }
//        for (int i = 0; i < 33; i++) {
//            int val = 18 + i;
//            MaxAge[i] = String.valueOf(val);
//        }
        Spannable spannableString = new SpannableString(getString(R.string.str_terms));
        spannableString.setSpan(linkClick, 11, txtterms.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(),11, txtterms.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtterms.setText(spannableString, TextView.BufferType.SPANNABLE);
        txtterms.setMovementMethod(LinkMovementMethod.getInstance());
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int selectedId = radioGender.getCheckedRadioButtonId();
                //find the radiobutton by retunend id
                RadioButton radioGenderButton = findViewById(checkedId);
                UserGender = radioGenderButton.getText().toString();
            }
        });
        ArrayAdapter adapterbldg = new ArrayAdapter(
                this,
                R.layout.spinner_row_layout, R.id.txtvalue,
                bloodgroup);
        spinneruserbloodGroup.setAdapter(adapterbldg);
        spinneruserbloodGroup.setOnItemSelectedListener(this);
//        ArrayAdapter adapterage = new ArrayAdapter(
//                this,
//                R.layout.spinner_row_layout, R.id.txtvalue,
//                MaxAge);
//        spinneruserage.setAdapter(adapterage);
//        spinneruserage.setOnItemSelectedListener(this);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // myCalendar.add(Calendar.DATE, 0);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                edtuserdateofbirth.setText(sdf.format(myCalendar.getTime()));

            }


        };
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(10);
        mProgressDialog.setMax(100);
        SpannableString str1 = new SpannableString("Already have an account?");
        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorbottomtext)), 0, str1.length(), 0);
        builder.append(str1);

        SpannableString str2 = new SpannableString(" Log In");
        str2.setSpan(new ForegroundColorSpan(Color.RED), 0, str2.length(), 0);
        str2.setSpan(new UnderlineSpan(),0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append(str2);

        txtnewuser.setText(builder, TextView.BufferType.SPANNABLE);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Signup.setOnClickListener(this);
        edtuserdateofbirth.setOnClickListener(this);
        edtuseraddress.setOnClickListener(this);
    }
    ClickableSpan linkClick = new ClickableSpan() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), WebViewActivity.class);
            startActivity(i);
            view.invalidate();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            if (txtterms.isPressed()) {
                ds.setColor(Color.BLUE);
            } else {
                ds.setColor(Color.RED);
            }
            txtterms.invalidate();
        }
    };
//textView.setHighlightColor(Color.TRANSPARENT);
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Signup:
                CreateUser();
                break;
            case R.id.edtuserdateofbirth:
                OpenDatepicker();
                break;
            case R.id.edtuseraddress:
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                break;

        }
    }

    private void OpenDatepicker() {
        final Calendar c = Calendar.getInstance();
        if (edtuserdateofbirth.getText().toString().isEmpty()) {
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.YEAR, -18);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox

                        if (year < mYear)
                            view.updateDate(mYear,mMonth,mDay);

                        if (monthOfYear < mMonth && year == mYear)
                            view.updateDate(mYear,mMonth,mDay);

                        if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                            view.updateDate(mYear,mMonth,mDay);

                        edtuserdateofbirth.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                        mMonth = monthOfYear + 1;
                        mDay = dayOfMonth;
                        mYear = year;
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
        dpd.show();

    }

    private void CreateUser() {
        username = edtName.getText().toString().trim();
        usermobile = edtmobileno.getText().toString().trim();
        useremail = edtEmail.getText().toString().trim();
        UserPass = edtpassword.getText().toString().trim();
        UserConfpass = edtconfpassword.getText().toString().trim();
        UserCity = edtcity.getText().toString().trim();
        UserBirthDate= edtuserdateofbirth.getText().toString().trim();
        UserAddress = edtuseraddress.getText().toString().trim();
        if (ValidateUser()) {
            try {
                if (!mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                String currentDate  = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
                String currentdatetime =  currentDate+" "+currentTime;
                Call<DefaultResponse> call = apiService.CreateUserCall(username, UserCity, useremail, strage, UserGender, strbloodgroup, UserPass, usermobile, String.valueOf(latitude), String.valueOf(longitude),UserAddress,UserBirthDate,currentdatetime);
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        DefaultResponse defaultResponse = response.body();
                        if (defaultResponse.getSuccess() == 1) {
                            Toast.makeText(SignupActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            SharedPreferences  sharedpreferences = getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Comman.signupComplete,getString(R.string.str_signupComplete));
                            editor.putString(Comman.VerificationOtpComplete,getString(R.string.str_OtpNotValidated));
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(),VerifyEmailCodeActivity.class);
                            intent.putExtra("useremail", useremail);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        // Log error here since request failed
                        Toast.makeText(SignupActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.toString());
                        if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } catch (Exception ex) {
                if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e(TAG, ex.toString());
            }
        }
    }


    private boolean ValidateUser() {
        String MobilePattern = "[0-9]{10}";
        try {
            if (username.isEmpty()) {
                edtName.setError(getString(R.string.str_entername));
                edtName.requestFocus();
                return false;
            }
            if (usermobile.isEmpty()) {
                edtmobileno.setError(getString(R.string.str_enterMobile));
                edtmobileno.requestFocus();
                return false;
            }
            if (!usermobile.isEmpty() && !usermobile.matches(MobilePattern)) {
                edtmobileno.setError(getString(R.string.str_enterValidMobile));
                edtmobileno.requestFocus();
                return false;
            }
            if (useremail.isEmpty()) {
                edtEmail.setError(getString(R.string.str_enterEmail));
                edtEmail.requestFocus();
                return false;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
                edtEmail.setError(getString(R.string.str_validemail));
                edtEmail.requestFocus();
                return false;
            }
            if (UserBirthDate.isEmpty()) {
                Toast.makeText(this,getString(R.string.str_selectdateofbirth),Toast.LENGTH_LONG).show();
                edtuserdateofbirth.requestFocus();
                return false;
            }
            if (UserGender.isEmpty()) {
                Toast.makeText(this,getString(R.string.str_selectgendererror),Toast.LENGTH_LONG).show();
                edtEmail.requestFocus();
                return false;
            }
            if (strbloodgroup.equals(getString(R.string.str_selectBloodGroup))) {
                Toast.makeText(this,getString(R.string.str_selectBloodGroupError),Toast.LENGTH_LONG).show();
                edtEmail.requestFocus();
                return false;
            }
            if (UserCity.isEmpty()) {
                edtcity.setError(getString(R.string.str_entrCity));
                edtcity.requestFocus();
                return false;
            }
            if (UserAddress.isEmpty()) {
                edtuseraddress.setError(getString(R.string.str_enteraddress));
                edtuseraddress.requestFocus();
                return false;
            }
            if (UserPass.isEmpty()) {
                edtpassword.setError(getString(R.string.str_enterPass));
                edtpassword.requestFocus();
                return false;
            }
            if (!UserPass.isEmpty() && !Comman.isValidPassword((UserPass)))
            {
                int unicode = 0x2022;
                String textIcon =  new String(Character.toChars(unicode));
                String strpass = textIcon+" "+getString(R.string.str_entervalidPass)+"\n";
                String strpass1 = textIcon+" "+getString(R.string.str_entervalidPass1)+"\n";
                String strpass2 =  textIcon+" "+getString(R.string.str_entervalidPass2)+"\n";
                String strpass3 =  textIcon+" "+getString(R.string.str_entervalidPass3);
                edtpassword.setError(strpass + strpass1 + strpass2 + strpass3);
                edtpassword.requestFocus();
                return false;
            }
            if (UserConfpass.isEmpty()) {
                edtconfpassword.setError(getString(R.string.str_enterConfPass));
                edtconfpassword.requestFocus();
                return false;
            }
            if (!UserPass.equals(UserConfpass)) {
                edtconfpassword.setError(getString(R.string.str_passwordnotmatch));
                edtconfpassword.requestFocus();
                return false;
            }
            if (!cb_agree.isChecked())
            {
                Toast.makeText(this, getString(R.string.str_agreetems), Toast.LENGTH_LONG).show();
                return false;
            }
            if (!UserBirthDate.isEmpty())
            {
                strage = getAge(mYear,mMonth,mDay);
                int age = Integer.parseInt(strage);
                if (age < 18 || age > 65)
                {
                    Toast.makeText(getApplicationContext(),"allowed aged between 18 and 65.",Toast.LENGTH_LONG).show();
                    return false;
                }
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            Spinner spinblg = (Spinner) parent;
            if (spinblg.getId() == R.id.spinneruserbloodGroup) {
                strbloodgroup = bloodgroup[position];
            }
//            else if (spinblg.getId() == R.id.spinneruserage) {
//                strage = MaxAge[position];
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    String Straddress = "Place: " + place.getName() + ", " + place.getId() +" , "+place.getAddress()+" , "+place.getLatLng();
                    Log.i(TAG, Straddress);
                    Toast.makeText(this,Straddress,Toast.LENGTH_LONG).show();
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i(TAG, status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
                return;
            }
        }catch (Exception ex)
        {
            Log.e(TAG, "OnActivityResult:- "+ex.toString());
        }
    }
}