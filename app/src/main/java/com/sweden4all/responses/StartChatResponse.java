package com.sweden4all.responses;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class StartChatResponse {

    private String response;
    @SerializedName(Constants.CHAT_ID)
    private String userId;
    @SerializedName(Constants.USER_ID)
    private String chatId;
    private String photo;
    @SerializedName(Constants.MSG_ID)
    private String msdId;
    private String timeStamp;

    public String getResponse() {
        return response;
    }

    public String getUserId() {
        return userId;
    }

    public String getChatId() {
        return chatId;
    }

    public String getPhoto() {
        return photo;
    }

    public String getMsdId() {
        return msdId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}