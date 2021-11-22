package mr.vsolutions.red_donorinfo.adapter;

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

import mr.vsolutions.red_donorinfo.LoginActivity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.model.PlacesItem;
import mr.vsolutions.red_donorinfo.util.MarkerViewHolder;
import mr.vsolutions.red_donorinfo.util.NotificationViewHolder;

public class RecyclerViewnotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    ArrayList<PlacesItem> pictureList;
    Context pictureContx;

    public RecyclerViewnotificationAdapter(ArrayList<PlacesItem> pictureList, Context pictureContx) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.notification_item_layout, parent, false);
        return new NotificationViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, final int position) {

        Glide.with(pictureContx)
                .load("https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI")
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgprofilephoto);


    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
