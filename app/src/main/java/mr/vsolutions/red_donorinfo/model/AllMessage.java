package mr.vsolutions.red_donorinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllMessage {
    @SerializedName("msgdonorlist")
    @Expose
    private List<Msgdonor> msgdonorlist = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public AllMessage() {
    }

    public AllMessage(List<Msgdonor> msgdonorlist, Integer success, String message) {
        super();
        this.msgdonorlist = msgdonorlist;
        this.success = success;
        this.message = message;
    }
    public List<Msgdonor> getMsgdonorlist() {
        return msgdonorlist;
    }

    public void setMsgdonorlist(List<Msgdonor> msgdonorlist) {
        this.msgdonorlist = msgdonorlist;
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
