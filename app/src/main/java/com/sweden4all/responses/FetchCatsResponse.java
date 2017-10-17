package com.sweden4all.responses;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class FetchCatsResponse {

    @SerializedName(Constants.CAT_ID)
    private int catId;
    private String name;

    public int getCatId() {
        return catId;
    }

    public String getName() {
        return name;
    }
}