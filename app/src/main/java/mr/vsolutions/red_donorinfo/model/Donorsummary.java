package mr.vsolutions.red_donorinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donorsummary {

    @SerializedName("avg_rating")
    @Expose
    private String avgRating;
    @SerializedName("tot_rating")
    @Expose
    private String totRating;
    @SerializedName("tot_review")
    @Expose
    private String totReview;
    @SerializedName("tot_rating1")
    @Expose
    private String totRating1;
    @SerializedName("tot_rating2")
    @Expose
    private String totRating2;
    @SerializedName("tot_rating3")
    @Expose
    private String totRating3;
    @SerializedName("tot_rating4")
    @Expose
    private String totRating4;
    @SerializedName("tot_rating5")
    @Expose
    private String totRating5;

    public Donorsummary() {
    }

    public Donorsummary(String avgRating, String totRating, String totReview, String totRating1, String totRating2, String totRating3, String totRating4, String totRating5) {
        super();
        this.avgRating = avgRating;
        this.totRating = totRating;
        this.totReview = totReview;
        this.totRating1 = totRating1;
        this.totRating2 = totRating2;
        this.totRating3 = totRating3;
        this.totRating4 = totRating4;
        this.totRating5 = totRating5;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public String getTotRating() {
        return totRating;
    }

    public void setTotRating(String totRating) {
        this.totRating = totRating;
    }

    public String getTotReview() {
        return totReview;
    }

    public void setTotReview(String totReview) {
        this.totReview = totReview;
    }

    public String getTotRating1() {
        return totRating1;
    }

    public void setTotRating1(String totRating1) {
        this.totRating1 = totRating1;
    }

    public String getTotRating2() {
        return totRating2;
    }

    public void setTotRating2(String totRating2) {
        this.totRating2 = totRating2;
    }

    public String getTotRating3() {
        return totRating3;
    }

    public void setTotRating3(String totRating3) {
        this.totRating3 = totRating3;
    }

    public String getTotRating4() {
        return totRating4;
    }

    public void setTotRating4(String totRating4) {
        this.totRating4 = totRating4;
    }

    public String getTotRating5() {
        return totRating5;
    }

    public void setTotRating5(String totRating5) {
        this.totRating5 = totRating5;
    }

}