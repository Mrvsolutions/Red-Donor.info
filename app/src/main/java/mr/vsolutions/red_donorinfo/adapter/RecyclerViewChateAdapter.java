package mr.vsolutions.red_donorinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.model.DonorDataMain;
import mr.vsolutions.red_donorinfo.model.Msgdata;
import mr.vsolutions.red_donorinfo.util.ChateViewHolder;
import mr.vsolutions.red_donorinfo.util.Comman;

public class RecyclerViewChateAdapter extends RecyclerView.Adapter<ChateViewHolder> {

    List<Msgdata> msgdataList;
    Context context;
    String _Recieved_ID;
    public RecyclerViewChateAdapter(List<Msgdata> msgdataList, String Recieved_ID,Context context) {
        this.msgdataList = msgdataList;
        this.context = context;
        this._Recieved_ID = Recieved_ID;
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

        Msgdata msgdata = msgdataList.get(position);
        Glide.with(context)
                .load("https://i.picsum.photos/id/866/200/300.jpg?hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI")
                .apply(new RequestOptions().centerCrop())
                .into(holder.imgprofilephoto);
        if (Comman.CommanUserDetail.getDonorId().equals(msgdata.getSenderId()))
        {
            holder.rlrightview.setVisibility(View.VISIBLE);
            holder.rllefview.setVisibility(View.GONE);
            holder.txtday.setVisibility(View.GONE);
            holder.txtrightmessage.setText(msgdata.getMsgText());
            holder.txttimeright.setText(msgdata.getMsgTime());
        }
        else
        {
            holder.rllefview.setVisibility(View.VISIBLE);
            holder.rlrightview.setVisibility(View.GONE);
            holder.txtday.setVisibility(View.GONE);
            holder.txtleftmessage.setText(msgdata.getMsgText());
            holder.txttimeleft.setText(msgdata.getMsgTime());
//            if (position == 6)
//            {
//                holder.txtday.setVisibility(View.VISIBLE);
//            }
        }
    }

    @Override
    public int getItemCount() {
        return msgdataList.size();
    }
}
