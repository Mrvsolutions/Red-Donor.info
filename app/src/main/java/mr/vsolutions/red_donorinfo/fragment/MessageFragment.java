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
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewMessageAdapter;
import mr.vsolutions.red_donorinfo.adapter.RecyclerViewnotificationAdapter;
import mr.vsolutions.red_donorinfo.model.PlacesItem;

public class MessageFragment extends Fragment {
    private RecyclerView listMessageRecycler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chate, container, false);
        listMessageRecycler = view.findViewById(R.id.listMessageRecycler);

        ArrayList<PlacesItem> allPlaces = new ArrayList<PlacesItem>();
        RecyclerView.Adapter indicatorAdapter = new RecyclerViewMessageAdapter(allPlaces,getActivity());
        listMessageRecycler.setLayoutManager(new GridLayoutManager(getContext(),1, RecyclerView.VERTICAL,false));
        listMessageRecycler.setAdapter(indicatorAdapter);
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).imgfilter.setVisibility(View.GONE);
        }
    }
}
