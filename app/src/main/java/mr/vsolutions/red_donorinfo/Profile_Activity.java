package mr.vsolutions.red_donorinfo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.model.LoginUser;
import mr.vsolutions.red_donorinfo.model.UploadImageResponse;
import mr.vsolutions.red_donorinfo.model.UserDetail;
import mr.vsolutions.red_donorinfo.util.Comman;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static mr.vsolutions.red_donorinfo.util.Comman.checkAndRequestPermissions;

public class Profile_Activity extends AppCompatActivity implements View.OnClickListener {

    String[] bloodgroup = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    public ImageView imgtoolprofilephoto, imgback, imgprofilephoto;
    public LinearLayout llcustomesearchview;
    RelativeLayout rltoolbarhome, rltoolbar, rlmainimageview;
    TextView title, txtuser_name, txtEdit;
    EditText edtCity, edtEmail, edtmobileno, edtAge, edtBloodGroup, edtAddress,edtuserdateofbirth;
    String username, usermobile, useremail, userAge, userBloodGroup, userCity, userAddress, UserGender, UserBirthDate,UserPimage;
    ProgressDialog mProgressDialog;
    private static final String TAG = Profile_Activity.class.getSimpleName();
    CardView cardviewCamera;
    RadioGroup radioGender;
    RadioButton male,female;
    private int mYear,mMonth,mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        getSupportActionBar().hide();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(0);
        mProgressDialog.setMax(100);

        imgtoolprofilephoto = findViewById(R.id.imgtoolprofilephoto);
        imgprofilephoto = findViewById(R.id.imgprofilephoto);
        llcustomesearchview = findViewById(R.id.llcustomesearchview);
        rltoolbarhome = findViewById(R.id.rltoolbarhome);
        rltoolbar = findViewById(R.id.rltoolbar);
        title = findViewById(R.id.title);
        imgback = findViewById(R.id.imgback);
        edtCity = findViewById(R.id.edtCity);
        edtEmail = findViewById(R.id.edtEmail);
        edtmobileno = findViewById(R.id.edtmobileno);
        edtAge = findViewById(R.id.edtAge);
        edtBloodGroup = findViewById(R.id.edtBloodGroup);
        edtAddress = findViewById(R.id.edtAddress);
        txtuser_name = findViewById(R.id.txtuser_name);
        cardviewCamera = findViewById(R.id.cardviewCamera);
        rlmainimageview = findViewById(R.id.rlmainimageview);
        txtEdit = findViewById(R.id.txtEdit);
        txtEdit.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.str_Profile));
        llcustomesearchview.setVisibility(View.GONE);
        rltoolbarhome.setVisibility(View.GONE);
        rltoolbar.setVisibility(View.VISIBLE);
        radioGender = findViewById(R.id.radioGender);
        male= findViewById(R.id.male);
        female= findViewById(R.id.female);
        edtuserdateofbirth = findViewById(R.id.edtuserdateofbirth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edtuserdateofbirth.setShowSoftInputOnFocus(false);
        }
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int selectedId = radioGender.getCheckedRadioButtonId();
                //find the radiobutton by retunend id
                RadioButton radioGenderButton = findViewById(checkedId);
                UserGender = radioGenderButton.getText().toString();
            }
        });
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
        if (Comman.CommanUserDetail != null) {
            txtEdit.setVisibility(View.VISIBLE);
            txtuser_name.setText(Comman.CommanUserDetail.getDonorName());
            edtAge.setText(Comman.CommanUserDetail.getDonorAge());
            edtAddress.setText(Comman.CommanUserDetail.getDonorAddress());
            edtBloodGroup.setText(Comman.CommanUserDetail.getDonorBloodGroup());
            edtCity.setText(Comman.CommanUserDetail.getDonorCity());
            edtmobileno.setText(Comman.CommanUserDetail.getDonorMobileno());
            edtEmail.setText(Comman.CommanUserDetail.getDonorEmail());
            edtuserdateofbirth.setText(Comman.CommanUserDetail.getDonorDob());
            if (!Comman.CommanUserDetail.getDonorGender().isEmpty())
            {
                if (Comman.CommanUserDetail.getDonorGender().equals("Male"))
                {
                    male.setChecked(true);
                    female.setChecked(false);
                }
                else
                {
                    male.setChecked(false);
                    female.setChecked(true);
                }
            }
            if (!Comman.CommanUserDetail.getDonorProfilePic().isEmpty()) {
                UserPimage = Comman.CommanUserDetail.getDonorProfilePic();
                Glide.with(this)
                        .load(Comman.CommanUserDetail.getDonorProfilePic())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .apply(new RequestOptions().centerCrop())
                        .into(imgprofilephoto);
            }
        }
        txtEdit.setOnClickListener(this);
        imgback.setOnClickListener(this);
        cardviewCamera.setOnClickListener(this);
        edtuserdateofbirth.setOnClickListener(this);
        SetEnableDisable();
    }

    private void SetEnableDisable() {
        if (txtEdit.getText().equals(getString(R.string.str_Edit))) {
            edtAge.setEnabled(false);
            edtEmail.setEnabled(false);
            edtAddress.setEnabled(false);
            edtCity.setEnabled(false);
            edtmobileno.setEnabled(false);
            edtBloodGroup.setEnabled(false);
            rlmainimageview.setEnabled(false);
            cardviewCamera.setEnabled(false);
            radioGender.setClickable(false);
            male.setEnabled(false);
            female.setEnabled(false);
            edtuserdateofbirth.setEnabled(false);
        } else {
            edtAge.setEnabled(true);
            edtEmail.setEnabled(true);
            edtAddress.setEnabled(true);
            edtCity.setEnabled(true);
            edtmobileno.setEnabled(true);
            edtBloodGroup.setEnabled(false);
            rlmainimageview.setEnabled(true);
            cardviewCamera.setEnabled(true);
            radioGender.setClickable(true);
            male.setEnabled(true);
            female.setEnabled(true);
            edtuserdateofbirth.setEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtEdit:
                if (txtEdit.getText().equals(getString(R.string.str_Edit))) {
                    txtEdit.setText(getString(R.string.str_Save));
                } else {
                    EditUser();
                    txtEdit.setText(getString(R.string.str_Edit));
                }
                SetEnableDisable();
                break;
            case R.id.imgback:
                finish();
                break;
            case R.id.cardviewCamera:
                if (checkAndRequestPermissions(this)) {
                    chooseImage(this);
                }
                break;
            case R.id.edtBloodGroup:
                LayoutInflater layoutInflater =
                        (LayoutInflater)getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.layout_popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               // Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                Spinner popupSpinner = (Spinner)popupView.findViewById(R.id.popupspinner);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, bloodgroup);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                popupSpinner.setAdapter(adapter);

//                btnDismiss.setOnClickListener(new Button.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }});
//                popupWindow.showAsDropDowntxtinputeditbg, 50, -30);
                break;
            case R.id.edtuserdateofbirth:
                OpenDatepicker();
                break;
        }
    }
    private void OpenDatepicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
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

                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
        dpd.show();

    }
    private void EditUser() {
        username = txtuser_name.getText().toString().trim();
        usermobile = edtmobileno.getText().toString().trim();
        useremail = edtEmail.getText().toString().trim();
        userAge = edtAge.getText().toString().trim();
        userBloodGroup = edtBloodGroup.getText().toString().trim();
        userCity = edtCity.getText().toString().trim();
        userAddress = edtAddress.getText().toString().trim();
        UserBirthDate = edtuserdateofbirth.getText().toString().trim();
        try {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<LoginUser> call = apiService.EditUserDataCall(Comman.CommanUserDetail.getDonorId(), username, userCity, useremail, UserGender, usermobile, userAge, userBloodGroup, userAddress,UserBirthDate,UserPimage);
            call.enqueue(new Callback<LoginUser>() {
                @Override
                public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                    LoginUser defaultResponse = response.body();
                    if (defaultResponse.getSuccess() == 1) {
                        List<UserDetail> lstuserdetail = defaultResponse.getUserdata();
                        Comman.CommanUserDetail = lstuserdetail.get(0);
                        SharedPreferences sharedpreferences = getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Comman.LoginCompleted,getString(R.string.str_LoginCompleted));
                        Gson gson = new Gson();
                        String json = gson.toJson(lstuserdetail.get(0));
                        editor.putString(Comman.strCommanuserdetai,json);
                        editor.commit();
                        editor.apply();
                        Toast.makeText(Profile_Activity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Profile_Activity.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if ((mProgressDialog != null)) {
                        mProgressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<LoginUser> call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(Profile_Activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, t.toString());
                    if ((mProgressDialog != null)) {
                        mProgressDialog.dismiss();
                    }
                }
            });
        } catch (Exception ex) {
            if ((mProgressDialog != null)) {
                mProgressDialog.dismiss();
            }
            Log.e(TAG, ex.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imgprofilephoto.setImageBitmap(selectedImage);
                        Uri tempUri = getImageUri(getApplicationContext(), selectedImage);
                        String strimageurl = getRealPathFromURI(tempUri, this);
                        File finalFile = new File(getRealPathFromURI(tempUri, this));
                        try {
                            InputStream is = getContentResolver().openInputStream(tempUri);
                            UploadImageToServer(strimageurl);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();

                        imgprofilephoto.setImageURI(selectedImage);
                        String strimageurl = getRealPathFromURI(selectedImage, this);
                        File finalFile = new File(strimageurl);
                        try {
                            InputStream is = getContentResolver().openInputStream(selectedImage);
                            UploadImageToServer(strimageurl);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri contentURI, Activity context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = context.managedQuery(contentURI, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            // cursor.close();
            return s;
        }
        // cursor.close();
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Comman.REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(this);
                }
                break;
        }
    }

    private void chooseImage(Activity context) {
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit"}; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("Take Photo")) {
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (optionsMenu[i].equals("Choose from Gallery")) {
                    // choose from  external storage
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                    pickPhoto.setType("image/*");
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK);
                    // Sets the type as image/*. This ensures only components of type image are selected
                    pickPhoto.setType("image/*");
                    startActivityForResult(pickPhoto, 1);
                } else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    private void UploadImageToServer(String selectedImageURL) {//String selectedImageURL,String SelectedImageFilename) {
        try {
            try {
                if (!mProgressDialog.isShowing()) {
                    mProgressDialog.show();
                }
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                //Create a file object using file path
                File file = new File(selectedImageURL);
                // Parsing any Media type file
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), file);

                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("donor_profile_pic", file.getName(), requestBody);
                //  RequestBody requestDonorID = RequestBody.create(MediaType.parse("text/plain"), Comman.CommanUserDetail.getDonorId());
                Call<UploadImageResponse> call = apiService.UploadUserImage(Comman.CommanUserDetail.getDonorId(), fileToUpload);
                call.enqueue(new Callback<UploadImageResponse>() {
                    @Override
                    public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                        UploadImageResponse Response = response.body();
                        if (Response.getSuccess() == 1) {
                            Toast.makeText(getApplicationContext(), Response.getMessage(), Toast.LENGTH_SHORT).show();
                            UserPimage = Response.getPimage();
                            Glide.with(getApplicationContext())
                                    .load(UserPimage)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .apply(new RequestOptions().centerCrop())
                                    .into(imgprofilephoto);
                        } else {
                            Toast.makeText(getApplicationContext(), Response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                        // Log error here since request failed
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.toString());
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }
                });
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        } catch (Exception ex) {
            Log.e(TAG, "GetMsgDataList - " + ex.toString());
        }
    }
}