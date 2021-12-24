package mr.vsolutions.red_donorinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mr.vsolutions.red_donorinfo.Chat_Screen_Activity;
import mr.vsolutions.red_donorinfo.Complain_Activity;
import mr.vsolutions.red_donorinfo.LoginActivity;
import mr.vsolutions.red_donorinfo.MainActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.ReportActivity;
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
        holder.txtage.setText("Age: "+donordata.getDonorAge());
        holder.txtaddress.setText(donordata.getDonorAddress());
        if (!donordata.getAvgRating().isEmpty())
        {
            holder.txtavgrating.setText(donordata.getAvgRating());
        }
        else
        {
            holder.txtavgrating.setText("0");
        }
        Glide.with(pictureContx)
                .load(donordata.getDonorProfilePic())
                .error(R.drawable.ic_person_placeholder)
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
        holder.txtavgrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(pictureContx.getApplicationContext(), ReportActivity.class);
                    i.putExtra("Donor_Id",donordata.getDonorId());
                    pictureContx.startActivity(i);
            }
        });
        holder.btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginCompleted == null || LoginCompleted.isEmpty()) {
                    Intent i = new Intent(pictureContx.getApplicationContext(), LoginActivity.class);
                    pictureContx.startActivity(i);
                }
                else if (!donordata.getDonorId().equals(Comman.CommanUserDetail.getDonorId()))
                {
                    Intent i = new Intent(pictureContx.getApplicationContext(), Chat_Screen_Activity.class);
                    i.putExtra("RecieverId",donordata.getDonorId());
                    i.putExtra("RecieverName",donordata.getDonorName());
                    pictureContx.startActivity(i);
                }
                else
                {
                    Toast.makeText(pictureContx,"This is your profile. please select other one",Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(pictureContx.getApplicationContext(), WriteYourReview_Activity.class);
                i.putExtra("Donor_Id",donordata.getDonorId());
                i.putExtra("Donor_Name",donordata.getDonorName());
                i.putExtra("Donor_address",donordata.getDonorAddress());
                i.putExtra("Donor_userimage",donordata.getDonorProfilePic());
                i.putExtra("Donor_Age",donordata.getDonorAge());
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
                else
                {
                    Intent i = new Intent(pictureContx.getApplicationContext(), Complain_Activity.class);
                    i.putExtra("Donor_Id",donordata.getDonorId());
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
