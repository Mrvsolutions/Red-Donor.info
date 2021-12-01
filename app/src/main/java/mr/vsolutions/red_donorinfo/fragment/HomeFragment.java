package mr.vsolutions.red_donorinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import mr.vsolutions.red_donorinfo.LoginActivity;
import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewMarkerAdapter;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.LoginUser;
import mr.vsolutions.red_donorinfo.model.PlacesItem;
import mr.vsolutions.red_donorinfo.model.UserDetail;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener{
    private GoogleMap mMap;
    public RecyclerView markerRecycler,listRecycler;
    View viewborder;
    RadioButton radioMap,radioList;
    public RelativeLayout rltoptabView,rlmainlistview,rlremoveview;
    LinearLayout llsortview;
    public ImageView imgremove;
    List<DonorDataMain.Donordata> placesItemArrayList;
    private static final String TAG = HomeFragment.class.getSimpleName();
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null, false);
        markerRecycler = view.findViewById(R.id.markerRecycler);
        listRecycler = view.findViewById(R.id.listRecycler);
        radioMap = view.findViewById(R.id.radioMap);
        radioList = view.findViewById(R.id.radioList);
        imgremove = view.findViewById(R.id.imgremove);
        rltoptabView = view.findViewById(R.id.rltoptabView);
        rlmainlistview = view.findViewById(R.id.rlmainlistview);
        rlremoveview = view.findViewById(R.id.rlremoveview);
        llsortview = view.findViewById(R.id.llsortview);
        viewborder = view.findViewById(R.id.viewborder);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(getActivity() instanceof MainActivity && ((MainActivity) getActivity()).IsFromFilter){
            placesItemArrayList = ((MainActivity) getActivity()).FilterItemArrayList;
            SetMarkerLocations();
            SetAdapterData(placesItemArrayList);
        }
        else {
            GetNearestDonorList();
        }
        markerRecycler.hasFixedSize();
        markerRecycler.setLayoutManager(new GridLayoutManager(getContext(),1, RecyclerView.HORIZONTAL,false));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markerRecycler.setVisibility(View.GONE);
                imgremove.setVisibility(View.GONE);
                viewborder.setVisibility(View.GONE);
                rlremoveview.setVisibility(View.GONE);
            }
        });
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                markerRecycler.setVisibility(View.GONE);
//                imgremove.setVisibility(View.GONE);
//                rlremoveview.setVisibility(View.GONE);
//            }
//        });
        imgremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markerRecycler.setVisibility(View.GONE);
                imgremove.setVisibility(View.GONE);
                viewborder.setVisibility(View.GONE);
                rlremoveview.setVisibility(View.GONE);
            }
        });
         radioList.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 radioMap.setChecked(false);
                 radioList.setChecked(true);
                 radioList.setTextColor(Color.WHITE);
                 radioMap.setTextColor(Color.GRAY);
                 listRecycler.setVisibility(View.VISIBLE);
                 markerRecycler.setVisibility(View.GONE);
                 imgremove.setVisibility(View.GONE);
                 viewborder.setVisibility(View.GONE);
                 rlremoveview.setVisibility(View.GONE);
                 rltoptabView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                 rlmainlistview.setBackgroundColor(getResources().getColor(R.color.ColorOffwhite));
                 llsortview.setVisibility(View.VISIBLE);
             }
         });
         radioMap.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 radioList.setChecked(false);
                 radioMap.setChecked(true);
                 radioMap.setTextColor(Color.WHITE);
                 radioList.setTextColor(Color.GRAY);
                 listRecycler.setVisibility(View.GONE);
                 markerRecycler.setVisibility(View.GONE);
                 imgremove.setVisibility(View.GONE);
                 viewborder.setVisibility(View.GONE);
                 rlremoveview.setVisibility(View.GONE);
                 rltoptabView.setBackgroundColor(getResources().getColor(R.color.colortrasnparent));
                 rlmainlistview.setBackgroundColor(getResources().getColor(R.color.colortrasnparent));
                 llsortview.setVisibility(View.GONE);
             }
         });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.isMyLocationEnabled();
        SetMarkerLocations();
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = (Integer) marker.getTag();

        if (clickCount != null) {

            marker.setTag(clickCount);
            DonorDataMain.Donordata donordata = placesItemArrayList.get(clickCount);
//            Toast.makeText(getContext(),
//                    marker.getTitle() +
//                            " has been clicked " + clickCount + " times.",
//                    Toast.LENGTH_SHORT).show();
            markerRecycler.setVisibility(View.VISIBLE);
            imgremove.setVisibility(View.VISIBLE);
            viewborder.setVisibility(View.VISIBLE);
            rlremoveview.setVisibility(View.VISIBLE);
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).llcustomesearchview.setVisibility(View.VISIBLE);
            ((MainActivity) getActivity()).imgfilter.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        markerRecycler.setVisibility(View.GONE);
        imgremove.setVisibility(View.GONE);
        viewborder.setVisibility(View.GONE);
        rlremoveview.setVisibility(View.GONE);
    }
    private void GetNearestDonorList() {
        try {
            String UserLant = "22.564518";
            String userLong = "72.928871";
                try {
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    Call<DonorDataMain> call = apiService.GetDonorList(userLong,UserLant);
                    call.enqueue(new Callback<DonorDataMain>() {
                        @Override
                        public void onResponse(Call<DonorDataMain> call, Response<DonorDataMain> response) {
                            DonorDataMain LoginResponse = response.body();
                            if (LoginResponse.getSuccess() == 1) {
                                Toast.makeText(getActivity(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                List<DonorDataMain.Donordata> lstuserdetail = LoginResponse.getDonordata();
                                placesItemArrayList = lstuserdetail;
                                SetMarkerLocations();
                                SetAdapterData(lstuserdetail);
                            } else {
                                Toast.makeText(getActivity(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DonorDataMain> call, Throwable t) {
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
            Log.e(TAG,"GetNearestDonorList - "+ ex.toString());
        }
    }

    private void SetAdapterData(List<DonorDataMain.Donordata> lstuserdetail) {
        try {
            RecyclerView.Adapter indicatorAdapter = new RecyclerViewMarkerAdapter(lstuserdetail,getContext());
            markerRecycler.setAdapter(indicatorAdapter);
            markerRecycler.hasFixedSize();
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(markerRecycler);
            listRecycler.setLayoutManager(new GridLayoutManager(getContext(),1, RecyclerView.VERTICAL,false));
            listRecycler.setAdapter(indicatorAdapter);
        }
        catch (Exception ex)
        {
            Log.e(TAG,"SetAdapterData - "+ ex.toString());
        }
    }

    private void SetMarkerLocations() {
        if (placesItemArrayList != null){
            for (int i = 0; i < placesItemArrayList.size(); i++) {
                try {
                    if (!placesItemArrayList.get(i).getDonorLatitude().isEmpty() && !placesItemArrayList.get(i).getDonorLongitude().isEmpty()) {
                        LatLng location = new LatLng(Double.parseDouble(placesItemArrayList.get(i).getDonorLatitude()), Double.parseDouble(placesItemArrayList.get(i).getDonorLongitude()));
                        MarkerOptions marker = new MarkerOptions().position(location).title(placesItemArrayList.get(i).getDonorAddress());
                        marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location));
                        mMap.addMarker(marker).setTag(i);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

                        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                    }
                }
                catch (Exception ex)
                {
                    Log.e(TAG,"SetMarkerLocations - "+ ex.toString());
                }
            }
        }
    }
}
