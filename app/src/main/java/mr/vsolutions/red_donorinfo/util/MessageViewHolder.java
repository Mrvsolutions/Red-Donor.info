package mr.vsolutions.red_donorinfo.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mr.vsolutions.red_donorinfo.R;


public class MessageViewHolder extends RecyclerView.ViewHolder{

    public ImageView imgprofilephoto,imgmedia;
    public TextView txtdesription,txtname,txttime;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        imgprofilephoto = itemView.findViewById(R.id.imgprofilephoto);
        imgmedia= itemView.findViewById(R.id.imgmedia);
        txtdesription = itemView.findViewById(R.id.txtdesription);
        txtname = itemView.findViewById(R.id.txtname);
        txttime = itemView.findViewById(R.id.txttime);

    }
}
