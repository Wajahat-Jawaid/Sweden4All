package com.sweden4all.responses;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class FetchTimeSlotsResponse {

    @SerializedName(Constants.TIME_ID)
    private String timeId;
    private String startTime;
    private String endTime;

    public String getTimeId() {
        return timeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}