package mr.vsolutions.red_donorinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Msgdonor {
    @SerializedName("donor_id")
    @Expose
    private String donorId;
    @SerializedName("donor_name")
    @Expose
    private String donorName;
    @SerializedName("donor_city")
    @Expose
    private String donorCity;
    @SerializedName("donor_mobileno")
    @Expose
    private String donorMobileno;
    @SerializedName("donor_profile_pic")
    @Expose
    private String donorProfilePic;
    @SerializedName("donor_age")
    @Expose
    private String donorAge;
    @SerializedName("donor_blood_group")
    @Expose
    private String donorBloodGroup;

    public Msgdonor() {
    }

    public Msgdonor(String donorId, String donorName, String donorCity, String donorMobileno, String donorProfilePic, String donorAge, String donorBloodGroup) {
        super();
        this.donorId = donorId;
        this.donorName = donorName;
        this.donorCity = donorCity;
        this.donorMobileno = donorMobileno;
        this.donorProfilePic = donorProfilePic;
        this.donorAge = donorAge;
        this.donorBloodGroup = donorBloodGroup;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorCity() {
        return donorCity;
    }

    public void setDonorCity(String donorCity) {
        this.donorCity = donorCity;
    }

    public String getDonorMobileno() {
        return donorMobileno;
    }

    public void setDonorMobileno(String donorMobileno) {
        this.donorMobileno = donorMobileno;
    }

    public String getDonorProfilePic() {
        return donorProfilePic;
    }

    public void setDonorProfilePic(String donorProfilePic) {
        this.donorProfilePic = donorProfilePic;
    }

    public String getDonorAge() {
        return donorAge;
    }

    public void setDonorAge(String donorAge) {
        this.donorAge = donorAge;
    }

    public String getDonorBloodGroup() {
        return donorBloodGroup;
    }

    public void setDonorBloodGroup(String donorBloodGroup) {
        this.donorBloodGroup = donorBloodGroup;
    }
}
