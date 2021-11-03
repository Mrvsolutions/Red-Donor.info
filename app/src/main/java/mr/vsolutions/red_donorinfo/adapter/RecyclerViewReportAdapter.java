package mr.vsolutions.red_donorinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.model.PlacesItem;
import mr.vsolutions.red_donorinfo.util.ReportViewHolder;

public class RecyclerViewReportAdapter extends RecyclerView.Adapter<ReportViewHolder> {

    ArrayList<PlacesItem> pictureList;
    Context pictureContx;

    public RecyclerViewReportAdapter(ArrayList<PlacesItem> pictureList, Context pictureContx) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
    }


    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.report_row_layout, parent, false);
        return new ReportViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, final int position) {

        Glide.with(pictureContx)
                .load("https://i.picsum.photos/id/870/200/300.jpg?blur=2&grayscale&hmac=ujRymp644uYVjdKJM7kyLDSsrqNSMVRPnGU99cKl6Vs")
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgprofilephoto);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
