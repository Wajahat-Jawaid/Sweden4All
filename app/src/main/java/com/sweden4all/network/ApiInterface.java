package com.sweden4all.network;

import com.sweden4all.constants.Constants;
import com.sweden4all.models.Appointment;
import com.sweden4all.responses.FetchCatsResponse;
import com.sweden4all.responses.FetchTimeSlotsResponse;
import com.sweden4all.responses.ScheduleAppointResponse;
import com.sweden4all.responses.UserResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @FormUrlEncoded
    @POST(POSTFIX + "editInfo")
    Observable<UserResponse> editProfile(@Field(Constants.USER_ID) String id,
                                         @Field(Constants.QUERY) String query);

    /* APPOINTMENTS */
    @GET(POSTFIX + "getCats")
    Observable<List<FetchCatsResponse>> queryCategories();

    @FormUrlEncoded
    @POST(POSTFIX + "time")
    Observable<List<FetchTimeSlotsResponse>> queryTimeSlots(@Field(Constants.DATE_OF_APP) String date,
                                                            @Field(Constants.WEEKDAY) String day);

    @FormUrlEncoded
    @POST(POSTFIX + "addApp")
    Observable<ScheduleAppointResponse> scheduleAppoint(@Field(Constants.USER_ID) int uId,
                                                        @Field(Constants.CAT_ID) int catId,
                                                        @Field(Constants.TIME_ID) int timeId,
                                                        @Field(Constants.STATUS) int status,
                                                        @Field(Constants.REASON) String reason,
                                                        @Field(Constants.DATE_OF_APP) String date);

    @FormUrlEncoded
    @POST(POSTFIX + "history")
    Observable<List<Appointment>> fetchTodayAppointments(@Field(Constants.USER_ID) String uId,
                                                         @Field(Constants.SAME_DATE) String sameDate);
}