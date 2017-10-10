package com.sweden4all.models;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class User {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}