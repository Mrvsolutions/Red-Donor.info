package mr.vsolutions.red_donorinfo.model;

public class UploadImageResponse {
    private int success;
    private String message;
    private String pimgUrl;

    public UploadImageResponse(int success, String message,String pimgUrl) {
        this.success = success;
        this.message = message;
        this.pimgUrl = pimgUrl;
    }

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    public String getPimage() {
        return pimgUrl;
    }
}
