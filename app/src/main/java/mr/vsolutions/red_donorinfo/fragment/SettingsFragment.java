package mr.vsolutions.red_donorinfo.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.Profile_Activity;
import mr.vsolutions.red_donorinfo.R;

public class SettingsFragment extends Fragment {
    RelativeLayout rlInviteFriend,rlMoreapp,rlEditProfile,rlRateApp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        rlInviteFriend = root.findViewById(R.id.rlInviteFriend);
        rlMoreapp = root.findViewById(R.id.rlMoreapp);
        rlEditProfile = root.findViewById(R.id.rlEditProfile);
        rlRateApp = root.findViewById(R.id.rlRateApp);
        rlRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("market://details?id=pinkal.nehakakkarcollection"));
//                startActivity(intent);
                Ratting_Dialog(getActivity());
            }
        });
        rlEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Profile_Activity.class);
                startActivity(i);
            }
        });
        rlInviteFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareMessage = getString(R.string.app_name) + ": " + "\nFree Download:\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=mr.vsolutions.red_donorinfo\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                }
            });
        rlMoreapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://search?q=pub:Mr. V Solutions"));
                    getActivity().startActivity(intent);
                }
            });
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
}
