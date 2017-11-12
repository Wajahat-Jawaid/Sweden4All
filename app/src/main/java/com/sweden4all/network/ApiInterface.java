package com.sweden4all.network;

import com.sweden4all.constants.Constants;
import com.sweden4all.models.Appointment;
import com.sweden4all.models.Chat;
import com.sweden4all.models.Message;
import com.sweden4all.responses.FetchCatsResponse;
import com.sweden4all.responses.FetchTimeSlotsResponse;
import com.sweden4all.responses.ScheduleAppointResponse;
import com.sweden4all.responses.SendMessageResponse;
import com.sweden4all.responses.StartChatResponse;
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

    @FormUrlEncoded
    @POST(POSTFIX + "getUser")
    Observable<UserResponse> getUser(@Field(Constants.USER_ID) String id);

    /* APPOINTMENTS */
    @GET(POSTFIX + "getCats")
    Observable<List<FetchCatsResponse>> queryCategories();

    @FormUrlEncoded
    @POST(POSTFIX + "time")
    Observable<List<FetchTimeSlotsResponse>> queryTimeSlots(@Field(Constants.DATE_OF_APP) String date,
                                                            @Field(Constants.WEEKDAY) String day);

    @FormUrlEncoded
    @POST(POSTFIX + "addApp")
    Observable<ScheduleAppointResponse> scheduleAppoint(@Field(Constants.USER_ID) String uId,
                                                        @Field(Constants.CAT_ID) String catId,
                                                        @Field(Constants.TIME_ID) String timeId,
                                                        @Field(Constants.STATUS) int status,
                                                        @Field(Constants.REASON) String reason,
                                                        @Field(Constants.DATE_OF_APP) String date);

    @FormUrlEncoded
    @POST(POSTFIX + "history")
    Observable<List<Appointment>> queryAppointments(@Field(Constants.USER_ID) String uId);

    @FormUrlEncoded
    @POST(POSTFIX + "getApp")
    Observable<Appointment> queryAppointmentDetails(@Field(Constants.APP_ID) String id);

    @FormUrlEncoded
    @POST(POSTFIX + "deleteApp")
    Observable<Appointment> deleteAppointment(@Field(Constants.APP_ID) String id);

    /* CHATS */
    @FormUrlEncoded
    @POST(POSTFIX + "getChats")
    Observable<List<Chat>> getAllChats(@Field(Constants.USER_ID) String id);

    @FormUrlEncoded
    @POST(POSTFIX + "startChat")
    Observable<StartChatResponse> triggerStartChat(@Field(Constants.USER_ID) String userId,
                                                   @Field(Constants.MESSAGE) String message,
                                                   @Field(Constants.DEVICE_TOKEN) String token,
                                                   @Field(Constants.IS_USER_MSG) String isUserMsg,
                                                   @Field(Constants.IS_PHOTO) int isPhoto,
                                                   @Field(Constants.PHOTO) String photo,
                                                   @Field(Constants.NAME) String name);

    @FormUrlEncoded
    @POST(POSTFIX + "sendMessage")
    Observable<SendMessageResponse> sendMessage(@Field(Constants.CHAT_ID) int chatId,
                                                @Field(Constants.USER_ID) String userId,
                                                @Field(Constants.MESSAGE) String message,
                                                @Field(Constants.DEVICE_TOKEN) String token,
                                                @Field(Constants.IS_USER_MSG) String isUserMsg,
                                                @Field(Constants.IS_PHOTO) int isPhoto,
                                                @Field(Constants.PHOTO) String photo);

    @FormUrlEncoded
    @POST(POSTFIX + "getMessages")
    Observable<List<Message>> getMessages(@Field(Constants.CHAT_ID) int chatId,
                                          @Field(Constants.LAST_MSG_ID) int lastMsgId);

}