package mr.vsolutions.red_donorinfo.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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

import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewMarkerAdapter;
import mr.vsolutions.red_donorinfo.model.PlacesItem;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener{
    private GoogleMap mMap;
    public RecyclerView markerRecycler,listRecycler;
    View viewborder;
    RadioButton radioMap,radioList;
    public RelativeLayout rltoptabView,rlmainlistview,rlremoveview;
    LinearLayout llsortview;
    public ImageView imgremove;
    ArrayList<LatLng> latlnglst = new ArrayList<LatLng>();
    ArrayList<PlacesItem> placesItemArrayList = new ArrayList<PlacesItem>();
    LatLng latlnghome = new LatLng(22.5592083, 72.9186319);
    LatLng latlngzydushospital = new LatLng(22.564139, 72.9126945);
    LatLng latlngvadtalSwaminarayan = new LatLng(22.5936319, 72.8700107);
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
        PlacesItem placesItem = new PlacesItem();
        placesItem.setPlacelatlng(latlngvadtalSwaminarayan);
        placesItem.setPlacetitle("Vadtal Swaminarayan Temple");
        PlacesItem placesItem1 = new PlacesItem();
        placesItem1.setPlacelatlng(latlngzydushospital);
        placesItem1.setPlacetitle("Zydus Hospital");
        PlacesItem placesItem2 = new PlacesItem();
        placesItem2.setPlacelatlng(latlnghome);
        placesItem2.setPlacetitle("Tirth 2 Society");
       placesItemArrayList.add(placesItem);
        placesItemArrayList.add(placesItem1);
        placesItemArrayList.add(placesItem2);
        markerRecycler.hasFixedSize();
        markerRecycler.setLayoutManager(new GridLayoutManager(getContext(),1, RecyclerView.HORIZONTAL,false));
        ArrayList<PlacesItem> allPlaces = new ArrayList<PlacesItem>();
        RecyclerView.Adapter indicatorAdapter = new RecyclerViewMarkerAdapter(allPlaces,getContext());
        markerRecycler.setAdapter(indicatorAdapter);
        markerRecycler.hasFixedSize();
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(markerRecycler);
        listRecycler.setLayoutManager(new GridLayoutManager(getContext(),1, RecyclerView.VERTICAL,false));
        listRecycler.setAdapter(indicatorAdapter);
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
        for (int i = 0; i < placesItemArrayList.size(); i++) {
            MarkerOptions marker = new MarkerOptions().position(placesItemArrayList.get(i).getPlacelatlng()).title(placesItemArrayList.get(i).getPlacetitle());
            marker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location));
            mMap.addMarker(marker).setTag(i);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(placesItemArrayList.get(i).getPlacelatlng()));

            mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        }

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = (Integer) marker.getTag();

        if (clickCount != null) {

            marker.setTag(clickCount);
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
}
