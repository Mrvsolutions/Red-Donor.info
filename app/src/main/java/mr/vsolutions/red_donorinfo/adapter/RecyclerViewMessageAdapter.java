package mr.vsolutions.red_donorinfo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

    public void Update (List<Msgdonor> msgDonorList)
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    _msgDonorList = msgDonorList;
                    notifyDataSetChanged();
                }
                catch (Exception ex)
                {
                    Log.e("MessageAdapter",ex.getMessage());
                }
            }
        });
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

        String donorphoto = "";
        if (!msgdonor.getDonorProfilePic().isEmpty())
        {
            donorphoto = msgdonor.getDonorProfilePic();
        }
        Glide.with(activity)
                .load(donorphoto)
                .error(R.drawable.ic_person_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgprofilephoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity.getApplicationContext(), Chat_Screen_Activity.class);
                i.putExtra("RecieverId",msgdonor.getDonorId());
                i.putExtra("RecieverName",msgdonor.getDonorName());
                i.putExtra("RecieverProfilepic",msgdonor.getDonorProfilePic());
                activity.startActivity(i);
            }
        });
        holder.imgmedia.setVisibility(View.GONE);
        holder.txtname.setText(msgdonor.getDonorName());
        holder.txtdesription.setText(msgdonor.getMsg_text());
        holder.txttime.setText(msgdonor.getMsg_time());
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
