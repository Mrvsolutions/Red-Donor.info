package mr.vsolutions.red_donorinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import mr.vsolutions.red_donorinfo.Chat_Screen_Activity;
import mr.vsolutions.red_donorinfo.Complain_Activity;
import mr.vsolutions.red_donorinfo.LoginActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.ReportActivity;
import mr.vsolutions.red_donorinfo.WriteYourReview_Activity;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
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
        if (donordata != null)
        {
            holder.txtname.setText(donordata.getDonorName());
            holder.txtage.setText("Age: "+donordata.getDonorAge());
            holder.txtbloodgroup.setText("Blood Group: "+donordata.getDonorBloodGroup());
            holder.txtCity.setText("City: "+donordata.getDonorCity());
            if (!donordata.getAvgRating().isEmpty())
            {
                holder.txtavgrating.setText(donordata.getAvgRating());
            }
            else
            {
                holder.txtavgrating.setText("0");
            }
            String strRating = " Rating", strReview = " Review";
            if (!donordata.getTotRating().equals("0") && !donordata.getTotRating().equals("1"))
            {
                strRating = " Ratings";
            }
            if (!donordata.getTotReview().equals("0") && !donordata.getTotReview().equals("1"))
            {
                strReview = " Reviews";
            }
            holder.txtratingdetail.setText((donordata.getTotRating() + strRating+" & "+donordata.getTotReview() +strReview));
            Glide.with(pictureContx)
                    .load(donordata.getDonorProfilePic())
                    .error(R.drawable.ic_person_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.imgprofilephoto);

            if (Comman.CommanUserDetail != null && donordata.getDonorId().equals(Comman.CommanUserDetail.getDonorId()))
            {
                holder.btnRate.setEnabled(false);
                holder.btnchat.setEnabled(false);
                holder.btnReport.setEnabled(false);
                holder.btnReport.setAlpha(0.5f);
                holder.btnRate.setAlpha(0.5f);
                holder.btnchat.setAlpha(0.5f);
            }
            else
            {
                holder.btnRate.setEnabled(true);
                holder.btnchat.setEnabled(true);
                holder.btnReport.setEnabled(true);
                holder.btnReport.setAlpha(1f);
                holder.btnRate.setAlpha(1f);
                holder.btnchat.setAlpha(1f);
            }
        }

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
                    String strRating = " Rating", strReview = " Review", strtitle;
                    if (!donordata.getTotRating().equals("0") && !donordata.getTotRating().equals("1"))
                    {
                        strRating = " Ratings";
                    }
                    if (!donordata.getTotReview().equals("0") && !donordata.getTotReview().equals("1"))
                    {
                        strReview = " Reviews";
                    }
                    strtitle = (donordata.getTotRating() + strRating+" & "+donordata.getTotReview() +strReview);
                    i.putExtra("titleheading",strtitle);
                    pictureContx.startActivity(i);
            }
        });
        holder.txtratingdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pictureContx.getApplicationContext(), ReportActivity.class);
                i.putExtra("Donor_Id",donordata.getDonorId());
                String strRating = " Rating", strReview = " Review", strtitle;
                if (!donordata.getTotRating().equals("0") && !donordata.getTotRating().equals("1"))
                {
                    strRating = " Ratings";
                }
                if (!donordata.getTotReview().equals("0") && !donordata.getTotReview().equals("1"))
                {
                    strReview = " Reviews";
                }
                strtitle = (donordata.getTotRating() + strRating+" & "+donordata.getTotReview() +strReview);
                i.putExtra("titleheading",strtitle);
                pictureContx.startActivity(i);
            }
        });
        holder.btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
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
                catch (Exception ex)
                {

                }
            }
        });
        holder.btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginCompleted == null || LoginCompleted.isEmpty()) {
                    Intent i = new Intent(pictureContx.getApplicationContext(), LoginActivity.class);
                    pictureContx.startActivity(i);
                }
                else {
                    Intent i = new Intent(pictureContx.getApplicationContext(), WriteYourReview_Activity.class);
                    i.putExtra("Donor_Id", donordata.getDonorId());
                    i.putExtra("Donor_Name", donordata.getDonorName());
                    i.putExtra("Donor_address", donordata.getDonorAddress());
                    i.putExtra("Donor_userimage", donordata.getDonorProfilePic());
                    i.putExtra("Donor_Age", donordata.getDonorAge());
                    pictureContx.startActivity(i);
                }
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
