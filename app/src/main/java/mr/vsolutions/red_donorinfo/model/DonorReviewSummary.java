package mr.vsolutions.red_donorinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonorReviewSummary {

    @SerializedName("donordata")
    @Expose
    private List<Donordatum> donordata = null;
    @SerializedName("donorsummary")
    @Expose
    private List<Donorsummary> donorsummary;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public DonorReviewSummary() {
    }

    public DonorReviewSummary(List<Donordatum> donordata, List<Donorsummary> donorsummary, Integer success, String message) {
        super();
        this.donordata = donordata;
        this.donorsummary = donorsummary;
        this.success = success;
        this.message = message;
    }

    public List<Donordatum> getDonordata() {
        return donordata;
    }

    public void setDonordata(List<Donordatum> donordata) {
        this.donordata = donordata;
    }

    public List<Donorsummary> getDonorsummary() {
        return donorsummary;
    }

    public void setDonorsummary(List<Donorsummary> donorsummary) {
        this.donorsummary = donorsummary;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}


