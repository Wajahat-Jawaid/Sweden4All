package com.sweden4all.responses;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class ScheduleAppointResponse {

    @SerializedName(Constants.APP_ID)
    private String appointId;

    public String getAppointId() {
        return appointId;
    }
}