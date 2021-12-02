package mr.vsolutions.red_donorinfo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
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

public class MessageFragment extends Fragment {
    private RecyclerView listMessageRecycler;
    private static final String TAG = MessageFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chate, container, false);
        listMessageRecycler = view.findViewById(R.id.listMessageRecycler);
        if (Comman.CommanUserDetail != null) {
            GetAllConversationList();
        }
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).imgfilter.setVisibility(View.GONE);
        }
    }
    private void GetAllConversationList() {
        try {
            try {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<AllMessage> call = apiService.GetChatConversationList(Comman.CommanUserDetail.getDonorId());
                call.enqueue(new Callback<AllMessage>() {
                    @Override
                    public void onResponse(Call<AllMessage> call, Response<AllMessage> response) {
                        AllMessage LoginResponse = response.body();
                        if (LoginResponse.getSuccess() == 1) {
                            Toast.makeText(getActivity(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            List<Msgdonor> lstuserdetail = LoginResponse.getMsgdonorlist();
                            SetAdapterData(lstuserdetail);
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
            RecyclerView.Adapter indicatorAdapter = new RecyclerViewMessageAdapter(lstuserdetail,getActivity());
            listMessageRecycler.setLayoutManager(new GridLayoutManager(getContext(),1, RecyclerView.VERTICAL,false));
            listMessageRecycler.setAdapter(indicatorAdapter);
        }
        catch (Exception ex)
        {
            Log.e(TAG,"SetAdapterData - "+ ex.toString());
        }
    }
}
