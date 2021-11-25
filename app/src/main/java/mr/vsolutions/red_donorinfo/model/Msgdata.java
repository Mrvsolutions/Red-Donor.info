package mr.vsolutions.red_donorinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Msgdata {
    @SerializedName("msg_id")
    @Expose
    private String msgId;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("msg_text")
    @Expose
    private String msgText;
    @SerializedName("msg_date")
    @Expose
    private String msgDate;
    @SerializedName("msg_time")
    @Expose
    private String msgTime;
    @SerializedName("msg_isread")
    @Expose
    private String msgIsread;

    public Msgdata() {
    }

    public Msgdata(String msgId, String senderId, String receiverId, String msgText, String msgDate, String msgTime, String msgIsread) {
        super();
        this.msgId = msgId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.msgText = msgText;
        this.msgDate = msgDate;
        this.msgTime = msgTime;
        this.msgIsread = msgIsread;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgIsread() {
        return msgIsread;
    }

    public void setMsgIsread(String msgIsread) {
        this.msgIsread = msgIsread;
    }
}
