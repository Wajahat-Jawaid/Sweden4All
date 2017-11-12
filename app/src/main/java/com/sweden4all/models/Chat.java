package com.sweden4all.models;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class Chat {

    @SerializedName(Constants.CHAT_ID)
    private String chatId;
    @SerializedName(Constants.USER_ID)
    private String userId;
    private String name;
    private String lastMessage;
    private String timeStamp;
//    private String ;
//    private String ;
//    private String ;


    public String getChatId() {
        return chatId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}