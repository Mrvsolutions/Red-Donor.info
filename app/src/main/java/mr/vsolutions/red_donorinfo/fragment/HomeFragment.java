package mr.vsolutions.red_donorinfo.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.ArrayList;
import java.util.List;

import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.Retrofit.ApiClient;
import mr.vsolutions.red_donorinfo.Retrofit.ApiInterface;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewMarkerAdapter;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.MapClusterItem;
import mr.vsolutions.red_donorinfo.util.Comman;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ClusterManager.OnClusterClickListener<MapClusterItem>, ClusterManager.OnClusterItemClickListener<MapClusterItem> {
    public GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker mClusterMarker;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public RecyclerView markerRecyclerhorizontal, listRecycler;
    View viewborder;
    RadioButton radioMap, radioList;
    public RelativeLayout rltoptabView, rlmainlistview, rlremoveview;
    LinearLayout llsortview;
    public ImageView imgremove;
    List<DonorDataMain.Donordata> placesItemArrayList;
    double Lantitude,Longitude;
    private static final String TAG = HomeFragment.class.getSimpleName();
    ClusterManager<MapClusterItem> clusterManager;
    SupportMapFragment mapFragment;
    View mapView;
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null, false);
        markerRecyclerhorizontal = view.findViewById(R.id.markerRecycler);
        listRecycler = view.findViewById(R.id.listRecycler);
        radioMap = view.findViewById(R.id.radioMap);
        radioList = view.findViewById(R.id.radioList);
        imgremove = view.findViewById(R.id.imgremove);
        rltoptabView = view.findViewById(R.id.rltoptabView);
        rlmainlistview = view.findViewById(R.id.rlmainlistview);
        rlremoveview = view.findViewById(R.id.rlremoveview);
        llsortview = view.findViewById(R.id.llsortview);
        viewborder = view.findViewById(R.id.viewborder);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
        markerRecyclerhorizontal.hasFixedSize();
        markerRecyclerhorizontal.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markerRecyclerhorizontal.setVisibility(View.GONE);
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
                markerRecyclerhorizontal.setVisibility(View.GONE);
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
                markerRecyclerhorizontal.setVisibility(View.GONE);
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
                markerRecyclerhorizontal.setVisibility(View.GONE);
                imgremove.setVisibility(View.GONE);
                viewborder.setVisibility(View.GONE);
                rlremoveview.setVisibility(View.GONE);
                rltoptabView.setBackgroundColor(getResources().getColor(R.color.colortrasnparent));
                rlmainlistview.setBackgroundColor(getResources().getColor(R.color.colortrasnparent));
                llsortview.setVisibility(View.GONE);
            }
        });
        if (getActivity() instanceof MainActivity && ((MainActivity) getActivity()).IsFromFilter) {
            placesItemArrayList = ((MainActivity) getActivity()).FilterItemArrayList;
            SetMarkerLocations();
            SetAdapterData(placesItemArrayList);
        }
//        else {
//            GetNearestDonorList();
//        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.isMyLocationEnabled();
        clusterManager = new ClusterManager(getActivity(), googleMap);
        clusterManager.setAnimation(false);
        clusterManager.setAlgorithm(new NonHierarchicalDistanceBasedAlgorithm<>());
        clusterManager.setRenderer(new CustomRenderer<MapClusterItem>(getActivity(), googleMap, clusterManager));
        clusterManager.setOnClusterClickListener(this);
        clusterManager.setOnClusterItemClickListener(this);
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        mMap.setOnInfoWindowClickListener(clusterManager);
        SetMarkerLocations();
      //  mMap.setOnMarkerClickListener(this);
//        mMap.setOnMapClickListener(this);
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Comman.checkAndRequestLocationPermissions(getActivity())) {
                buildGoogleApiClient();
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }
    private List<MapClusterItem> getItems() {
        List<MapClusterItem> clusterItemList = new ArrayList<>();
       try {
           if (placesItemArrayList != null) {
               for (int i = 0; i < placesItemArrayList.size(); i++) {
                   if (!placesItemArrayList.get(i).getDonorLatitude().isEmpty() && !placesItemArrayList.get(i).getDonorLongitude().isEmpty()) {
                       LatLng location = new LatLng(Double.parseDouble(placesItemArrayList.get(i).getDonorLatitude()), Double.parseDouble(placesItemArrayList.get(i).getDonorLongitude()));
                       clusterItemList.add(new MapClusterItem(placesItemArrayList.get(i).getDonorAddress(),location, placesItemArrayList.get(i)));
                   }
               }
           }
       }
       catch (Exception ex)
       {
           ex.getMessage();
       }
       return  clusterItemList;
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = (Integer) marker.getTag();

        if (clickCount != null) {

            marker.setTag(clickCount);
            DonorDataMain.Donordata donordata = placesItemArrayList.get(clickCount);
            markerRecyclerhorizontal.scrollToPosition(clickCount);
            markerRecyclerhorizontal.setVisibility(View.VISIBLE);
            imgremove.setVisibility(View.VISIBLE);
            viewborder.setVisibility(View.VISIBLE);
            rlremoveview.setVisibility(View.VISIBLE);
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).llcustomesearchview.setVisibility(View.VISIBLE);
            ((MainActivity) getActivity()).imgfilter.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        markerRecyclerhorizontal.setVisibility(View.GONE);
        imgremove.setVisibility(View.GONE);
        viewborder.setVisibility(View.GONE);
        rlremoveview.setVisibility(View.GONE);
    }

    private void GetNearestDonorList() {
        try {
            String UserLant = String.valueOf(Lantitude);
            String userLong = String.valueOf(Longitude);
            try {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                Call<DonorDataMain> call = apiService.GetDonorList(UserLant, userLong);
                call.enqueue(new Callback<DonorDataMain>() {
                    @Override
                    public void onResponse(Call<DonorDataMain> call, Response<DonorDataMain> response) {
                        DonorDataMain LoginResponse = response.body();
                        if (LoginResponse.getSuccess() == 1) {
                           // Toast.makeText(getActivity(), LoginResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
        } catch (Exception ex) {
            Log.e(TAG, "GetNearestDonorList - " + ex.toString());
        }
    }

    private void SetAdapterData(List<DonorDataMain.Donordata> lstuserdetail) {
        try {
            RecyclerView.Adapter indicatorAdapter = new RecyclerViewMarkerAdapter(lstuserdetail, getContext());
//            markerRecyclerhorizontal.setAdapter(indicatorAdapter);
//            markerRecyclerhorizontal.hasFixedSize();
//            SnapHelper snapHelper = new PagerSnapHelper();
//            snapHelper.attachToRecyclerView(markerRecyclerhorizontal);
            listRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false));
            listRecycler.setAdapter(indicatorAdapter);
        } catch (Exception ex) {
            Log.e(TAG, "SetAdapterData - " + ex.toString());
        }
    }

    private void SetMarkerLocations() {

        if (placesItemArrayList != null) {
          //  ClusterManager<MapClusterItem> clusterManager = new ClusterManager(getActivity(), mMap);  // 3
        //    mMap.setOnCameraIdleListener(clusterManager);
          //  mMap.setOnMarkerClickListener(clusterManager);
            List<MapClusterItem> items = getItems();
            clusterManager.addItems(items);// 4
            clusterManager.cluster();  // 5
//            for (int i = 0; i < placesItemArrayList.size(); i++) {
//                try {
//                    if (!placesItemArrayList.get(i).getDonorLatitude().isEmpty() && !placesItemArrayList.get(i).getDonorLongitude().isEmpty()) {
//                        LatLng location = new LatLng(Double.parseDouble(placesItemArrayList.get(i).getDonorLatitude()), Double.parseDouble(placesItemArrayList.get(i).getDonorLongitude()));
//                        MarkerOptions marker = new MarkerOptions().position(location).title(placesItemArrayList.get(i).getDonorAddress());
//                        marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location));
////                        mMap.addMarker(marker).setTag(i);
//                      //  clusterManager.addItem(marker);
//                       clusterManager.getMarkerCollection().addMarker(marker);
//                        clusterManager.cluster();
//                    }
//                } catch (Exception ex) {
//                    Log.e(TAG, "SetMarkerLocations - " + ex.toString());
//                }
//            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Comman.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
              //  GetCurrentLocation(location);
                GetNearestDonorList();
                break;
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        GetCurrentLocation(location);

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    private void GetCurrentLocation(@NonNull Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        Lantitude = location.getLatitude();
        Longitude = location.getLongitude();
        Comman.Lantitude = Lantitude;
        Comman.Longitude = Longitude;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                15));
        if (getActivity() instanceof MainActivity && ((MainActivity) getActivity()).IsFromFilter) {
            placesItemArrayList = ((MainActivity) getActivity()).FilterItemArrayList;
            SetMarkerLocations();
            SetAdapterData(placesItemArrayList);
        }
        else {
            GetNearestDonorList();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onClusterClick(Cluster<MapClusterItem> cluster) {
       if (cluster != null)
       {

       }
        return false;
    }

    @Override
    public boolean onClusterItemClick(MapClusterItem mapClusterItem) {
        if (mapClusterItem != null)
        {
            try {
                List<DonorDataMain.Donordata> selectormarkerlist = new ArrayList<>();
                selectormarkerlist.add(mapClusterItem.getDonordata());
                RecyclerView.Adapter indicatorAdapter = new RecyclerViewMarkerAdapter(selectormarkerlist, getContext());
                markerRecyclerhorizontal.setAdapter(indicatorAdapter);
                markerRecyclerhorizontal.hasFixedSize();
//                SnapHelper snapHelper = new PagerSnapHelper();
//                snapHelper.attachToRecyclerView(markerRecyclerhorizontal);
                markerRecyclerhorizontal.setVisibility(View.VISIBLE);
                imgremove.setVisibility(View.VISIBLE);
                viewborder.setVisibility(View.VISIBLE);
                rlremoveview.setVisibility(View.VISIBLE);
            } catch (Exception ex) {
                Log.e(TAG, "onClusterItemClick - " + ex.toString());
            }
        }
        return false;
    }
}
class CustomRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T> {
    public CustomRenderer(Context context, GoogleMap map, ClusterManager<T> clusterManager) {
        super(context, map, clusterManager);

    }

    @Override
    protected void onBeforeClusterItemRendered(T item, MarkerOptions markerOptions) {
        // TODO: consider adding anchor(.5, .5) (Individual markers will overlap more often)
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<T> cluster) {
        //start clustering if at least 2 items overlap
        return cluster.getSize() > 1;
    }
}