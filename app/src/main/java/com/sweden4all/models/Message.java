package com.sweden4all.models;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class Message {

    @SerializedName(Constants.MSG_ID)
    private String msgId;
    private String message;
    private String timeStamp;
    private String isUserMsg;

    public String getMsgId() {
        return msgId;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String isUserMsg() {
        return isUserMsg;
    }

    public void setUserMsg(String userMsg) {
        isUserMsg = userMsg;
    }
}