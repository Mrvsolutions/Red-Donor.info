package mr.vsolutions.red_donorinfo.util;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mr.vsolutions.red_donorinfo.R;


public class NotificationViewHolder extends RecyclerView.ViewHolder{

    public ImageView imgprofilephoto;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        imgprofilephoto = itemView.findViewById(R.id.imgprofilephoto);

    }
}
