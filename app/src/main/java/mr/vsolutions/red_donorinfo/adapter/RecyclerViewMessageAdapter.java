package mr.vsolutions.red_donorinfo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import mr.vsolutions.red_donorinfo.Chat_Screen_Activity;
import mr.vsolutions.red_donorinfo.LoginActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.model.PlacesItem;
import mr.vsolutions.red_donorinfo.util.MessageViewHolder;

public class RecyclerViewMessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    ArrayList<PlacesItem> pictureList;
    Activity pictureContx;

    public RecyclerViewMessageAdapter(ArrayList<PlacesItem> pictureList, Activity pictureContx) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.message_itemlist_layout, parent, false);
        return new MessageViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, final int position) {

        Glide.with(pictureContx)
                .load("http://www.freeinfo.in/admin/photos/Kinjal_Dave_503%20(Mr.%20V%20Solutions).jpg")
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgprofilephoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(pictureContx.getApplicationContext(), Chat_Screen_Activity.class);
                pictureContx.startActivity(i);
            }
        });
        if (position == 1 || position == 4 || position == 8)
        {
            holder.imgmedia.setVisibility(View.VISIBLE);
            holder.txtdesription.setText("Photo");
        }
        else
        {
            holder.imgmedia.setVisibility(View.GONE);
            holder.txtdesription.setText("On October 21, 2020, Mr. mondell,of Wyoming ,the men");
        }


    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
