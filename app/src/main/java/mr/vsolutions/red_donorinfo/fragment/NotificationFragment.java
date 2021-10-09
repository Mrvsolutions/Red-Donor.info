package mr.vsolutions.red_donorinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewMarkerAdapter;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewnotificationAdapter;
import mr.vsolutions.red_donorinfo.model.PlacesItem;

public class NotificationFragment extends Fragment {
    private RecyclerView listnotificationRecycler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        listnotificationRecycler = view.findViewById(R.id.listnotificationRecycler);

        ArrayList<PlacesItem> allPlaces = new ArrayList<PlacesItem>();
        RecyclerView.Adapter indicatorAdapter = new RecyclerViewnotificationAdapter(allPlaces,getContext());
        listnotificationRecycler.setLayoutManager(new GridLayoutManager(getContext(),1, RecyclerView.VERTICAL,false));
        listnotificationRecycler.setAdapter(indicatorAdapter);
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).llcustomesearchview.setVisibility(View.GONE);
        }
    }
}
