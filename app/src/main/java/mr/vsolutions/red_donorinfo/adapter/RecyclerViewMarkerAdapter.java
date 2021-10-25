package mr.vsolutions.red_donorinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import mr.vsolutions.red_donorinfo.LoginActivity;
import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.WriteYourReview_Activity;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.PlacesItem;
import mr.vsolutions.red_donorinfo.util.Comman;
import mr.vsolutions.red_donorinfo.util.MarkerViewHolder;

public class RecyclerViewMarkerAdapter extends RecyclerView.Adapter<MarkerViewHolder> {

    List<DonorDataMain.Donordata> pictureList;
    Context pictureContx;
    SharedPreferences sharedpreferences;
    String LoginCompleted;
    public RecyclerViewMarkerAdapter(List<DonorDataMain.Donordata> pictureList, Context pictureContx) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
        sharedpreferences = pictureContx.getSharedPreferences(Comman.SHARED_PREFS, Context.MODE_PRIVATE);
        LoginCompleted = sharedpreferences.getString(Comman.LoginCompleted, null);
    }


    @NonNull
    @Override
    public MarkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.item_row_layout, parent, false);
        return new MarkerViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerViewHolder holder, final int position) {

        final DonorDataMain.Donordata donordata = pictureList.get(position);
//
//        holder.positionController.setBackgroundColor(pic.getSelected() ? Color.parseColor("#00000000") : Color.parseColor("#8c000000"));
        holder.txtname.setText(donordata.getDonorName());
        holder.txtage.setText(donordata.getDonorAge());
        holder.txtaddress.setText(donordata.getDonorAddress());
        Glide.with(pictureContx)
                .load("http://www.freeinfo.in/admin/photos/Kinjal_Dave_503%20(Mr.%20V%20Solutions).jpg")
//              .placeholder(R.drawable.ic_profile).dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgprofilephoto);

//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //holder.card.setCardElevation(5);
//                pic.setSelected(true);
//                notifyDataSetChanged();
//                imageListerner.onImageIndicatorClicked(position);
//            }
//        });
        holder.btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginCompleted == null || LoginCompleted.isEmpty()) {
                    Intent i = new Intent(pictureContx.getApplicationContext(), LoginActivity.class);
                    pictureContx.startActivity(i);
                }
            }
        });
        holder.btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(pictureContx.getApplicationContext(), WriteYourReview_Activity.class);
                pictureContx.startActivity(i);
            }
        });
        holder.btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginCompleted == null || LoginCompleted.isEmpty()) {
                    Intent i = new Intent(pictureContx.getApplicationContext(), LoginActivity.class);
                    pictureContx.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }
}
