package mr.vsolutions.red_donorinfo.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mr.vsolutions.red_donorinfo.R;


public class ReportViewHolder extends RecyclerView.ViewHolder{

    public ImageView imgprofilephoto;
    public TextView txtname, txtage,txtaddress, txtdetail,txtratecount;

    public ReportViewHolder(@NonNull View itemView) {
        super(itemView);
        imgprofilephoto = itemView.findViewById(R.id.imgprofilephoto);
        txtname = itemView.findViewById(R.id.txtname);
        txtage = itemView.findViewById(R.id.txtage);
        txtaddress = itemView.findViewById(R.id.txtaddress);
        txtdetail = itemView.findViewById(R.id.txtdetail);
        txtratecount = itemView.findViewById(R.id.txtratecount);
    }
}
