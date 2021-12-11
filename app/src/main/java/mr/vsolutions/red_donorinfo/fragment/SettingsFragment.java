package mr.vsolutions.red_donorinfo.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mr.vsolutions.red_donorinfo.ForgotPasswordActivity;
import mr.vsolutions.red_donorinfo.LoginActivity;
import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.Profile_Activity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.SignupActivity;
import mr.vsolutions.red_donorinfo.model.DefaultResponse;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlInviteFriend,rlMoreapp,rlEditProfile,rlRateApp;
    TextView txtLogout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        rlInviteFriend = root.findViewById(R.id.rlInviteFriend);
        rlMoreapp = root.findViewById(R.id.rlMoreapp);
        rlEditProfile = root.findViewById(R.id.rlEditProfile);
        rlRateApp = root.findViewById(R.id.rlRateApp);
        txtLogout = root.findViewById(R.id.txtLogout);
        rlRateApp.setOnClickListener(this);
        rlEditProfile.setOnClickListener(this);
        rlInviteFriend.setOnClickListener(this);
        rlMoreapp.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        if (Comman.CommanUserDetail != null)
        {
            txtLogout.setVisibility(View.VISIBLE);
        }
        else
        {
            txtLogout.setVisibility(View.GONE);
        }
        return  root;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).llcustomesearchview.setVisibility(View.GONE);
        }
    }
    public void Ratting_Dialog (Activity activity)
    {
        try {
            if (activity != null && !activity.isFinishing()) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.ratting_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView btn_Nothanks = (TextView) dialog.findViewById(R.id.txt_nothanks);
                Button btn_Continue = (Button) dialog.findViewById(R.id.btn_continue);
                final RatingBar ratbar = (RatingBar) dialog.findViewById(R.id.ratbar);
                btn_Nothanks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_Continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=mr.vsolutions.red_donorinfo"));
                        getActivity().startActivity(intent);
                        Toast.makeText(getActivity().getApplicationContext(), "Thanks for Rating is " + ratbar.getRating(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void UserSignoutCall(String DonorID) {
        try {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<DefaultResponse> call = apiService.SignoutAsync(DonorID);
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    DefaultResponse defaultResponse = response.body();
                    if (defaultResponse.getSuccess() == 1) {
                        Toast.makeText(getActivity().getApplicationContext(), defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Comman.CommanUserDetail = null;
                        Comman.CommanToken = null;
                        SharedPreferences preferences = getActivity().getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().finishAffinity();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(getActivity().getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SettingFragment", t.toString());
                }
            });
        }
        catch (Exception ex)
        {
            Log.e("SettingFragment","UserDeviceRegisterCall - "+ ex.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlRateApp:
                Ratting_Dialog(getActivity());
                break;
            case  R.id.rlEditProfile:
                if (Comman.CommanUserDetail != null){
                    Intent i = new Intent(getActivity(), Profile_Activity.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.rlInviteFriend:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = getString(R.string.app_name) + ": " + "\nFree Download:\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=mr.vsolutions.red_donorinfo\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                break;
            case R.id.rlMoreapp:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://search?q=pub:Mr. V Solutions"));
                getActivity().startActivity(intent);
                break;
            case R.id.txtLogout:
                if (Comman.CommanUserDetail != null)
                {
                    UserSignoutCall(Comman.CommanUserDetail.getDonorId());
                }
                break;
        }
    }
}
