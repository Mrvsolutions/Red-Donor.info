package mr.vsolutions.red_donorinfo.util;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import mr.vsolutions.red_donorinfo.R;


public class MarkerViewHolder extends RecyclerView.ViewHolder{

    public ImageView imgprofilephoto;
    public Button btnchat,btnRate,btnReport;
//    private CardView card;
//    View positionController;

    public MarkerViewHolder(@NonNull View itemView) {
        super(itemView);
        imgprofilephoto = itemView.findViewById(R.id.imgprofilephoto);
        btnchat = itemView.findViewById(R.id.btnchat);
        btnRate = itemView.findViewById(R.id.btnRate);
        btnReport = itemView.findViewById(R.id.btnReport);
//        card = itemView.findViewById(R.id.indicatorCard);
//        positionController = itemView.findViewById(R.id.activeImage);
    }
}
