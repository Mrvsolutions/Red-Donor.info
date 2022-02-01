package mr.vsolutions.red_donorinfo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

import mr.vsolutions.red_donorinfo.ForgotPasswordActivity;
import mr.vsolutions.red_donorinfo.LoginActivity;
import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.SignupActivity;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewMarkerAdapter;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewMessageAdapter;
import mr.vsolutions.red_donorinfo.model.AllMessage;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.Msgdonor;
import mr.vsolutions.red_donorinfo.model.PlacesItem;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageFragment extends Fragment implements View.OnClickListener {
    private RecyclerView listMessageRecycler;
    TextView txtsignup,txtsignin;
    private static final String TAG = MessageFragment.class.getSimpleName();
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 10*1000;
    RecyclerViewMessageAdapter indicatorAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chate, container, false);
        listMessageRecycler = view.findViewById(R.id.listMessageRecycler);
        txtsignup = view.findViewById(R.id.txtsignup);
        txtsignin = view.findViewById(R.id.txtsignin);
        SpannableStringBuilder builderSignup = new SpannableStringBuilder();
        SpannableStringBuilder builderSignin = new SpannableStringBuilder();
        SpannableString str1= new SpannableString("New User?");
        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorbottomtext)), 0, str1.length(), 0);
        builderSignup.append(str1);
        ((SimpleItemAnimator) listMessageRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        SpannableString str2= new SpannableString(" Sign Up");
        str2.setSpan(new ForegroundColorSpan(Color.RED), 0, str2.length(), 0);
        builderSignup.append(str2);
        txtsignup.setText( builderSignup, TextView.BufferType.SPANNABLE);

        SpannableString strlogin1 = new SpannableString("Already have an account?");
        strlogin1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorbottomtext)), 0, strlogin1.length(), 0);
        builderSignin.append(strlogin1);

        SpannableString strlogin2 = new SpannableString(" Log In");
        strlogin2.setSpan(new ForegroundColorSpan(Color.RED), 0, strlogin2.length(), 0);
        builderSignin.append(strlogin2);
        txtsignin.setText(builderSignin, TextView.BufferType.SPANNABLE);

        txtsignin.setOnClickListener(this);
        txtsignup.setOnClickListener(this);
        if (Comman.CommanUserDetail != null) {
            listMessageRecycler.setVisibility(View.VISIBLE);
            txtsignin.setVisibility(View.GONE);
            txtsignup.setVisibility(View.GONE);
            GetAllConversationList(false);
        }
        else
        {
            listMessageRecycler.setVisibility(View.GONE);
            txtsignin.setVisibility(View.VISIBLE);
            txtsignup.setVisibility(View.VISIBLE);
        }
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).imgfilter.setVisibility(View.GONE);
        }
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                GetAllConversationList(true);
                handler.postDelayed(runnable, delay);
            }
        }, delay);
    }
    private void GetAllConversationList( boolean isFromtimer) {
        try {
            try {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<AllMessage> call = apiService.GetChatConversationList(Comman.CommanUserDetail.getDonorId());
                call.enqueue(new Callback<AllMessage>() {
                    @Override
                    public void onResponse(Call<AllMessage> call, Response<AllMessage> response) {
                        AllMessage LoginResponse = response.body();
                        if (LoginResponse.getSuccess() == 1) {
                         //   Toast.makeText(getActivity(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            List<Msgdonor> lstuserdetail = LoginResponse.getMsgdonorlist();
                            if (isFromtimer && indicatorAdapter != null) {
                                indicatorAdapter.Update(lstuserdetail);
                            }
                            else
                            {
                                SetAdapterData(lstuserdetail);
                            }
                        } else {
                            Toast.makeText(getActivity(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AllMessage> call, Throwable t) {
                        // Log error here since request failed
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.toString());
                    }
                });
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG,"GetAllConversationList - "+ ex.toString());
        }
    }

    private void SetAdapterData(List<Msgdonor> lstuserdetail) {
        try {
            indicatorAdapter = new RecyclerViewMessageAdapter(lstuserdetail,getActivity());
            listMessageRecycler.setLayoutManager(new GridLayoutManager(getContext(),1, RecyclerView.VERTICAL,false));
            listMessageRecycler.setAdapter(indicatorAdapter);
        }
        catch (Exception ex)
        {
            Log.e(TAG,"SetAdapterData - "+ ex.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtsignin:
                Intent ilogin = new Intent(getContext(), LoginActivity.class);
                startActivity(ilogin);
                break;
            case  R.id.txtsignup:
                Intent i = new Intent(getContext(), SignupActivity.class);
                startActivity(i);
                break;
        }
    }
}
