package com.sweden4all.responses;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class UserResponse {

    @SerializedName(Constants.USER_ID)
    private String id;
    private String name;
    private String email;
    @SerializedName(Constants.PASSWORD)
    private String pwd;
    private String city;
    private String country;
    private String phone;
    @SerializedName(Constants.USER_DOB)
    private String dob;
    @SerializedName(Constants.DEVICE_TOKEN)
    private String token;
}