package mr.vsolutions.red_donorinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginUser {
    @SerializedName("userdata")
    @Expose
    private List<UserDetail> userdata = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public LoginUser(List<UserDetail> userdata, Integer success, String message) {
        super();
        this.userdata = userdata;
        this.success = success;
        this.message = message;
    }

    public List<UserDetail> getUserdata() {
        return userdata;
    }

    public void setUserdata(List<UserDetail> userdata) {
        this.userdata = userdata;
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