package com.sweden4all.responses;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class FetchCatsResponse {

    @SerializedName(Constants.CAT_ID)
    private String catId;
    private String name;

    public String getCatId() {
        return catId;
    }

    public String getName() {
        return name;
    }
}