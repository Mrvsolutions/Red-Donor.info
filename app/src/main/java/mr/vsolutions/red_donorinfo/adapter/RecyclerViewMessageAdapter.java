package mr.vsolutions.red_donorinfo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mr.vsolutions.red_donorinfo.Chat_Screen_Activity;
import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.model.Msgdonor;
import mr.vsolutions.red_donorinfo.util.MessageViewHolder;

public class RecyclerViewMessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    List<Msgdonor> _msgDonorList;
    Activity activity;

    public RecyclerViewMessageAdapter(List<Msgdonor> msgDonorList, Activity activity) {
        this._msgDonorList = msgDonorList;
        this.activity = activity;
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
        final Msgdonor msgdonor = _msgDonorList.get(position);

        String donorphoto = "https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI";
        if (!msgdonor.getDonorProfilePic().isEmpty())
        {
            donorphoto = msgdonor.getDonorProfilePic();
        }
        Glide.with(activity)
                .load(donorphoto)
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgprofilephoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity.getApplicationContext(), Chat_Screen_Activity.class);
                i.putExtra("RecieverId",msgdonor.getDonorId());
                i.putExtra("RecieverName",msgdonor.getDonorName());
                activity.startActivity(i);
            }
        });
        holder.imgmedia.setVisibility(View.GONE);
        holder.txtname.setText(msgdonor.getDonorName());
//        if (position == 1 || position == 4 || position == 8)
//        {
//            holder.imgmedia.setVisibility(View.VISIBLE);
//            holder.txtdesription.setText("Photo");
//        }
//        else
//        {
//            holder.imgmedia.setVisibility(View.GONE);
//            holder.txtdesription.setText("On October 21, 2020, Mr. mondell,of Wyoming ,the men");
//        }


    }

    @Override
    public int getItemCount() {
        return _msgDonorList.size();
    }
}
