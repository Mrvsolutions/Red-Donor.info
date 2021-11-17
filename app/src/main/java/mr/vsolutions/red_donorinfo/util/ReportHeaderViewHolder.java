package mr.vsolutions.red_donorinfo.util;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taufiqrahman.reviewratings.RatingReviews;

import mr.vsolutions.red_donorinfo.R;


public class ReportHeaderViewHolder extends RecyclerView.ViewHolder{

    public RatingReviews rating_reviews;
    TextView txttotalrating,txtratingdetail;

    public ReportHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        rating_reviews = itemView.findViewById(R.id.rating_reviews);
        txttotalrating = itemView.findViewById(R.id.txttotalrating);
        txtratingdetail = itemView.findViewById(R.id.txtratingdetail);
    }
}
