package mr.vsolutions.red_donorinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MsgdataMain {
    @SerializedName("msgdata")
    @Expose
    private List<Msgdata> msgdata = null;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public MsgdataMain() {
    }

    /**
     *
     * @param msgdata
     * @param success
     * @param message
     */
    public MsgdataMain(List<Msgdata> msgdata, Integer success, String message) {
        super();
        this.msgdata = msgdata;
        this.success = success;
        this.message = message;
    }

    public List<Msgdata> getMsgdata() {
        return msgdata;
    }

    public void setMsgdata(List<Msgdata> msgdata) {
        this.msgdata = msgdata;
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
