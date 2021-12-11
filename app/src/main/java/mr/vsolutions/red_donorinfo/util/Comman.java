package mr.vsolutions.red_donorinfo.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import mr.vsolutions.red_donorinfo.model.UserDetail;

public class Comman {
    public static String SHARED_PREFS = "shared_prefs";
    public static String signupComplete = "signupComplete";
    public static String VerificationOtpComplete = "VerificationOtpComplete";
    public static String LoginCompleted = "LoginCompleted";
    public static String strCommanuserdetai = "LoginCompleted";
    public static UserDetail CommanUserDetail = null;
    public static String CommanToken = "";

    public static double Lantitude,Longitude;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 102;

    public static boolean checkAndRequestPermissions(Activity context) {
        try {
            int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int cameraPermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            }
            if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded
                        .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(context, listPermissionsNeeded
                                .toArray(new String[listPermissionsNeeded.size()]),
                        REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
        }
        catch (Exception ex)
        {
            Log.e("Comman", "checkAndRequestPermissions"+ex.toString());
        }
        return true;
    }
    public static boolean checkAndRequestLocationPermissions(Activity context) {
        try {
            int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (ACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(context, listPermissionsNeeded
                                .toArray(new String[listPermissionsNeeded.size()]),
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return false;
            }
        }
        catch (Exception ex)
        {
            Log.e("Comman", "checkAndRequestPermissions"+ex.toString());
        }
        return true;
    }
}
