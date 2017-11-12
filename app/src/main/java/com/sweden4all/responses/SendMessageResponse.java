package com.sweden4all.responses;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class SendMessageResponse {

    private String response;
    @SerializedName(Constants.MSG_ID)
    private String msdId;
    private String timeStamp;

    public String getResponse() {
        return response;
    }

    public String getMsdId() {
        return msdId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}