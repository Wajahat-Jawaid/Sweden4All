package com.sweden4all.models;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class Appointment {

    @SerializedName(Constants.APP_ID)
    private String id;
    @SerializedName(Constants.DATE_OF_APP)
    private String date;
    @SerializedName(Constants.CAT_ID)
    private String catId;
    @SerializedName(Constants.TIME_ID)
    private String timeId;
    private String status;
    private String reason;

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCatId() {
        return catId;
    }

    public String getTimeId() {
        return timeId;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}