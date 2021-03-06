package mr.vsolutions.red_donorinfo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.taufiqrahman.reviewratings.BarLabels;

import java.util.List;

import mr.vsolutions.red_donorinfo.R;
import mr.vsolutions.red_donorinfo.model.Donordatum;
import mr.vsolutions.red_donorinfo.model.Donorsummary;
import mr.vsolutions.red_donorinfo.util.ReportHeaderViewHolder;
import mr.vsolutions.red_donorinfo.util.ReportViewHolder;

public class RecyclerViewReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Donordatum> _donorreviewdata;
    Donorsummary _donorsummary;
    Context pictureContx;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public RecyclerViewReportAdapter(List<Donordatum> donordataList, Donorsummary _donorsummary, Context pictureContx) {
        this._donorreviewdata = donordataList;
        this.pictureContx = pictureContx;
        this._donorsummary = _donorsummary;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View cell = inflater.inflate(R.layout.report_row_layout, parent, false);
            return new ReportViewHolder(cell);

        } else if (viewType == TYPE_HEADER) {
            // Here Inflating your header view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View cell = inflater.inflate(R.layout.horizontal_chartbar, parent, false);
            return new ReportHeaderViewHolder(cell);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReportViewHolder)
        {
            Donordatum item =  _donorreviewdata.get(position-1);
            ReportViewHolder reportViewHolder = (ReportViewHolder) holder;
            reportViewHolder.txtname.setText(item.getReviewerNname());
            reportViewHolder.txtreviewtitle.setText(item.getReviewTitle());
            reportViewHolder.txtreviewdetail.setText(item.getReviewContent());
            reportViewHolder.txtratecount.setText(item.getrRating());
            reportViewHolder.txtdetail.setVisibility(View.GONE);
            reportViewHolder.txtratecount.setText(item.getrRating());
            Glide.with(pictureContx)
                    .load(item.getReviewerProfilePic())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .apply(new RequestOptions().centerCrop())
                    .error(R.drawable.ic_person_placeholder)
                    .into(reportViewHolder.imgprofilephoto);
        }
        else if (holder instanceof ReportHeaderViewHolder)
        {

            ReportHeaderViewHolder headerViewHolder = (ReportHeaderViewHolder) holder;
            headerViewHolder.txttotalrating.setText(_donorsummary.getAvgRating());

            String strRating = " Rating", strReview = " Review";
            if (!_donorsummary.getTotRating().equals("0") && !_donorsummary.getTotRating().equals("1"))
            {
                strRating = " Ratings";
            }
            if (!_donorsummary.getTotReview().equals("0") && !_donorsummary.getTotReview().equals("1"))
            {
                strReview = " Reviews";
            }
            headerViewHolder.txtratingdetail.setText((_donorsummary.getTotRating() + strRating+" & "+_donorsummary.getTotReview() +strReview));
            int colors[] = new int[]{
                    Color.parseColor("#0e9d58"),
                    Color.parseColor("#bfd047"),
                    Color.parseColor("#ffc105"),
                    Color.parseColor("#ef7e14"),
                    Color.parseColor("#d36259")};

            int raters[] = new int[]{
                    Integer.parseInt(_donorsummary.getTotRating5()),
                    Integer.parseInt(_donorsummary.getTotRating4()),
                    Integer.parseInt(_donorsummary.getTotRating3()),
                    Integer.parseInt(_donorsummary.getTotRating2()),
                    Integer.parseInt(_donorsummary.getTotRating1()),
            };
            headerViewHolder.rating_reviews.createRatingBars(100, BarLabels.STYPE1, colors, raters);
        }
    }

    @Override
    public int getItemCount() {
        return _donorreviewdata.size()+1;
    }
}
