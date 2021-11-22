package mr.vsolutions.red_donorinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donordatum {

    @SerializedName("review_id")
    @Expose
    private String reviewId;
    @SerializedName("donor_id")
    @Expose
    private String donorId;
    @SerializedName("r_name")
    @Expose
    private String rName;
    @SerializedName("r_rating")
    @Expose
    private String rRating;
    @SerializedName("review_title")
    @Expose
    private String reviewTitle;
    @SerializedName("review_content")
    @Expose
    private String reviewContent;
    @SerializedName("review_datetime")
    @Expose
    private String reviewDatetime;
    @SerializedName("review_status")
    @Expose
    private String reviewStatus;
    @SerializedName("status_remark")
    @Expose
    private String statusRemark;

    public Donordatum() {
    }

    public Donordatum(String reviewId, String donorId, String rName, String rRating, String reviewTitle, String reviewContent, String reviewDatetime, String reviewStatus, String statusRemark) {
        super();
        this.reviewId = reviewId;
        this.donorId = donorId;
        this.rName = rName;
        this.rRating = rRating;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewDatetime = reviewDatetime;
        this.reviewStatus = reviewStatus;
        this.statusRemark = statusRemark;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrRating() {
        return rRating;
    }

    public void setrRating(String rRating) {
        this.rRating = rRating;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewDatetime() {
        return reviewDatetime;
    }

    public void setReviewDatetime(String reviewDatetime) {
        this.reviewDatetime = reviewDatetime;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getStatusRemark() {
        return statusRemark;
    }

    public void setStatusRemark(String statusRemark) {
        this.statusRemark = statusRemark;
    }

}