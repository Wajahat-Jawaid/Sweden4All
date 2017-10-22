package com.sweden4all.responses;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class UserResponse {

    @SerializedName(Constants.USER_ID)
    private String id;
    private String name;
    private String aboutMe;
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
    private String isActive;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }

    public String getToken() {
        return token;
    }

    public String isActive() {
        return isActive;
    }
}