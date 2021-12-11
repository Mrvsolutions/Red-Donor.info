package mr.vsolutions.red_donorinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonorDataMain {
    @SerializedName("donordata")
    @Expose
    private List<Donordata> donordata = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public DonorDataMain(List<Donordata> donordata, Integer success, String message) {
        this.donordata = donordata;
        this.success = success;
        this.message = message;
    }

    public List<Donordata> getDonordata() {
        return donordata;
    }

    public void setDonordata(List<Donordata> donordata) {
        this.donordata = donordata;
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

    public class Donordata {
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
        @SerializedName("donor_latitude")
        @Expose
        private String donorLatitude;
        @SerializedName("donor_longitude")
        @Expose
        private String donorLongitude;
        @SerializedName("donor_address")
        @Expose
        private String donorAddress;
        @SerializedName("donor_email")
        @Expose
        private String donorEmail;
        @SerializedName("donor_pswd")
        @Expose
        private String donorPswd;
        @SerializedName("signup_datetime")
        @Expose
        private String signupDatetime;
        @SerializedName("signup_verificationcode")
        @Expose
        private String signupVerificationcode;
        @SerializedName("frgt_pswd_rqst")
        @Expose
        private String frgtPswdRqst;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("avg_rating")
        @Expose
        private String avgRating;

        public Donordata(String donorId, String donorName, String donorCity, String donorMobileno, String donorProfilePic, String donorAge, String donorBloodGroup, String donorLatitude, String donorLongitude, String donorAddress, String donorEmail, String donorPswd, String signupDatetime, String signupVerificationcode, String frgtPswdRqst,  String distance, String avgRating) {
            super();
            this.donorId = donorId;
            this.donorName = donorName;
            this.donorCity = donorCity;
            this.donorMobileno = donorMobileno;
            this.donorProfilePic = donorProfilePic;
            this.donorAge = donorAge;
            this.donorBloodGroup = donorBloodGroup;
            this.donorLatitude = donorLatitude;
            this.donorLongitude = donorLongitude;
            this.donorAddress = donorAddress;
            this.donorEmail = donorEmail;
            this.donorPswd = donorPswd;
            this.signupDatetime = signupDatetime;
            this.signupVerificationcode = signupVerificationcode;
            this.frgtPswdRqst = frgtPswdRqst;
            this.distance = distance;
            this.avgRating = avgRating;
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

        public String getDonorLatitude() {
            return donorLatitude;
        }

        public void setDonorLatitude(String donorLatitude) {
            this.donorLatitude = donorLatitude;
        }

        public String getDonorLongitude() {
            return donorLongitude;
        }

        public void setDonorLongitude(String donorLongitude) {
            this.donorLongitude = donorLongitude;
        }

        public String getDonorAddress() {
            return donorAddress;
        }

        public void setDonorAddress(String donorAddress) {
            this.donorAddress = donorAddress;
        }

        public String getDonorEmail() {
            return donorEmail;
        }

        public void setDonorEmail(String donorEmail) {
            this.donorEmail = donorEmail;
        }

        public String getDonorPswd() {
            return donorPswd;
        }

        public void setDonorPswd(String donorPswd) {
            this.donorPswd = donorPswd;
        }

        public String getSignupDatetime() {
            return signupDatetime;
        }

        public void setSignupDatetime(String signupDatetime) {
            this.signupDatetime = signupDatetime;
        }

        public String getSignupVerificationcode() {
            return signupVerificationcode;
        }

        public void setSignupVerificationcode(String signupVerificationcode) {
            this.signupVerificationcode = signupVerificationcode;
        }

        public String getFrgtPswdRqst() {
            return frgtPswdRqst;
        }

        public void setFrgtPswdRqst(String frgtPswdRqst) {
            this.frgtPswdRqst = frgtPswdRqst;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getAvgRating() {
            return avgRating;
        }

        public void setAvgRating(String avgRating) {
            this.avgRating = avgRating;
        }

    }
}
