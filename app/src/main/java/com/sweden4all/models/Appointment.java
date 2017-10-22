package com.sweden4all.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sweden4all.constants.Constants;

public class Appointment implements Parcelable {

    @SerializedName(Constants.APP_ID)
    private String id;
    @SerializedName(Constants.DATE_OF_APP)
    private String date;
    @SerializedName(Constants.CAT_ID)
    private String catId;
    @SerializedName(Constants.TIME_ID)
    private String timeId;
    private String status;
    private String reason;

    /**
     * Non server keys
     */
    private String catStr;
    private String timingsStr;

    protected Appointment(Parcel in) {
        id = in.readString();
        date = in.readString();
        catId = in.readString();
        timeId = in.readString();
        status = in.readString();
        reason = in.readString();
        catStr = in.readString();
        timingsStr = in.readString();
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCatId() {
        return catId;
    }

    public String getTimeId() {
        return timeId;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public String getCatStr() {
        return catStr;
    }

    public String getTimingsStr() {
        return timingsStr;
    }

    public void setCatStr(String catStr) {
        this.catStr = catStr;
    }

    public void setTimingsStr(String timingsStr) {
        this.timingsStr = timingsStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(date);
        parcel.writeString(catId);
        parcel.writeString(timeId);
        parcel.writeString(status);
        parcel.writeString(reason);
        parcel.writeString(catStr);
        parcel.writeString(timingsStr);
    }
}