package com.sweden4all.network;

import com.sweden4all.constants.Constants;
import com.sweden4all.responses.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    String POSTFIX = "android.php?q=";

    @FormUrlEncoded
    @POST(POSTFIX + "login")
    Observable<UserResponse> login(@Field(Constants.EMAIL) String email,
                                   @Field(Constants.PASSWORD) String pwd);

    @FormUrlEncoded
    @POST(POSTFIX + "reg")
    Observable<UserResponse> register(@Field(Constants.NAME) String name,
                                      @Field(Constants.EMAIL) String email,
                                      @Field(Constants.PHONE) String phone,
                                      @Field(Constants.CITY) String city,
                                      @Field(Constants.COUNTRY) String country,
                                      @Field(Constants.USER_DOB) String dob,
                                      @Field(Constants.PASSWORD) String pwd,
                                      @Field(Constants.DEVICE_TOKEN) String token);
}