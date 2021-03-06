package mr.vsolutions.red_donorinfo.util;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mr.vsolutions.red_donorinfo.R;


public class MarkerViewHolder extends RecyclerView.ViewHolder{

    public ImageView imgprofilephoto;
    public Button btnchat,btnRate,btnReport;
    public TextView txtname,txtage, txtbloodgroup,txtdetail,txtavgrating,txtCity,txtratingdetail;
//    private CardView card;
//    View positionController;

    public MarkerViewHolder(@NonNull View itemView) {
        super(itemView);
        imgprofilephoto = itemView.findViewById(R.id.imgprofilephoto);
        btnchat = itemView.findViewById(R.id.btnchat);
        btnRate = itemView.findViewById(R.id.btnRate);
        btnReport = itemView.findViewById(R.id.btnReport);
        txtname= itemView.findViewById(R.id.txtname);
        txtage= itemView.findViewById(R.id.txtage);
        txtbloodgroup = itemView.findViewById(R.id.txtaddress);
        txtCity= itemView.findViewById(R.id.txtCity);
        txtdetail= itemView.findViewById(R.id.txtdetail);
        txtavgrating = itemView.findViewById(R.id.txtavgrating);
        txtratingdetail = itemView.findViewById(R.id.txtratingdetail);
    }
}
