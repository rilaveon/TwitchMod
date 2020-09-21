package tv.twitch.android.mod.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RobottyResponse {
    @SerializedName("messages")
    private List<String> messages;
    @SerializedName("error")
    private String error;
    @SerializedName("error_code")
    private String errorCode;
    @Expose
    @SerializedName("status")
    private Integer status;
    @Expose
    @SerializedName("status_message")
    private String statusMessage;


    public List<String> getMessages() {
        return messages;
    }

    public String getError() {
        return error;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
