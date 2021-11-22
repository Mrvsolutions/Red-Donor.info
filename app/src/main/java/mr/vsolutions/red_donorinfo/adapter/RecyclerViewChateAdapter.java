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
import mr.vsolutions.red_donorinfo.util.ChateViewHolder;
import mr.vsolutions.red_donorinfo.util.NotificationViewHolder;

public class RecyclerViewChateAdapter extends RecyclerView.Adapter<ChateViewHolder> {

    ArrayList<PlacesItem> pictureList;
    Context pictureContx;

    public RecyclerViewChateAdapter(ArrayList<PlacesItem> pictureList, Context pictureContx) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
    }


    @NonNull
    @Override
    public ChateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.chate_item_layout, parent, false);
        return new ChateViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull ChateViewHolder holder, final int position) {

        Glide.with(pictureContx)
                .load("https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI")
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgprofilephoto);


        if (position == 0 || position == 2 || position == 4 || position == 6 || position == 8)
        {
            holder.rllefview.setVisibility(View.VISIBLE);
            holder.rlrightview.setVisibility(View.GONE);
            holder.txtday.setVisibility(View.GONE);
            if (position == 6)
            {
                holder.txtday.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            holder.rlrightview.setVisibility(View.VISIBLE);
            holder.rllefview.setVisibility(View.GONE);
            holder.txtday.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
