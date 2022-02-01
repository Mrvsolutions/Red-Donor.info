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
    @SerializedName("reviewer_id")
    @Expose
    private String reviewerId;
    @SerializedName("reviewer_name")
    @Expose
    private String reviewerNname;
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
    @SerializedName("reviewer_profile_pic")
    @Expose
    private String reviewerProfilePic;

    public Donordatum() {
    }

    public Donordatum(String reviewId, String donorId, String reviewerId , String reviewerNname, String rRating, String reviewTitle, String reviewContent, String reviewDatetime, String reviewStatus, String statusRemark, String reviewerProfilePic) {
        super();
        this.reviewId = reviewId;
        this.donorId = donorId;
        this.reviewerId = reviewerId;
        this.reviewerNname = reviewerNname;
        this.rRating = rRating;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewDatetime = reviewDatetime;
        this.reviewStatus = reviewStatus;
        this.statusRemark = statusRemark;
        this.reviewerProfilePic = reviewerProfilePic;
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

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewerNname() {
        return reviewerNname;
    }

    public void setReviewerNname(String reviewerNname) {
        this.reviewerNname = reviewerNname;
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

    public String getReviewerProfilePic() {
        return reviewerProfilePic;
    }

    public void setReviewerProfilePic(String reviewerProfilePic) {
        this.reviewerProfilePic = reviewerProfilePic;
    }
}