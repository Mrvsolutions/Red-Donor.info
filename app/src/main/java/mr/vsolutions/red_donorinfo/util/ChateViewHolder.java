package mr.vsolutions.red_donorinfo.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mr.vsolutions.red_donorinfo.R;


public class ChateViewHolder extends RecyclerView.ViewHolder{

    public ImageView imgreciverprofilephoto;
    public TextView txtday,txtleftmessage,txttimeleft,txtrightmessage,txttimeright;
    public RelativeLayout rllefview,rlrightview;

    public ChateViewHolder(@NonNull View itemView) {
        super(itemView);
        imgreciverprofilephoto = itemView.findViewById(R.id.imgprofilephoto);
        txtday = itemView.findViewById(R.id.txtday);
        rllefview = itemView.findViewById(R.id.rllefview);
        rlrightview = itemView.findViewById(R.id.rlrightview);
        txtleftmessage = itemView.findViewById(R.id.txtleftmessage);
        txttimeleft = itemView.findViewById(R.id.txttimeleft);
        txtrightmessage = itemView.findViewById(R.id.txtrightmessage);
        txttimeright = itemView.findViewById(R.id.txttimeright);
        txtday.setVisibility(View.GONE);
        rlrightview.setVisibility(View.GONE);
        rllefview.setVisibility(View.GONE);
    }
}
