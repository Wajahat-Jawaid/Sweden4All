package com.sweden4all.activities.appointments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sweden4all.activities.BaseActivity;
import com.sweden4all.constants.Constants;
import com.sweden4all.models.Appointment;
import com.sweden4all.responses.FetchCatsResponse;
import com.sweden4all.responses.FetchTimeSlotsResponse;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.appointments.AppointmentsListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActListAppointments extends BaseActivity {

    private static final String TAG = "ActListAppointments";
    private static int catsSize, timingsSize;

    private List<FetchTimeSlotsResponse> timingsList;
    private List<FetchCatsResponse> catsList;
    private static Date todaysDate;
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            todaysDate = SIMPLE_DATE_FORMAT.parse(utils.getTodaysYMDDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        queryCategories();
    }

    @Override
    public BaseView getView() {
        return new AppointmentsListView(this);
    }

    private void queryAppointments() {
        final Observable<List<Appointment>> call =
                apiInterface.queryAppointments(prefs.getString(Constants.USER_ID));
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseAppointmentsResponse);
    }

    private void parseAppointmentsResponse(List<Appointment> list) {
        if (list != null && list.size() > 0) {
            ArrayList<Appointment> listHistory = new ArrayList<>();
            ArrayList<Appointment> listTodays = new ArrayList<>();
            ArrayList<Appointment> listUpcoming = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Appointment app = list.get(i);
                try {
                    app.setCatStr(returnCategoryStr(app.getCatId()));
                    app.setTimingsStr(returnTimingStr(app.getTimeId()));
                    Date d = SIMPLE_DATE_FORMAT.parse(app.getDate());
                    if (todaysDate.compareTo(d) > 0)
                        listHistory.add(app);
                    else if (todaysDate.compareTo(d) == 0)
                        listTodays.add(app);
                    else
                        listUpcoming.add(app);
                } catch (Exception e) {
                }
            }

            ((AppointmentsListView) view).setupViewPager(listHistory, listTodays, listUpcoming);
            hideLoader();
        }
    }

    private String returnCategoryStr(String catId) {
        for (int i = 0; i < catsSize; i++) {
            if (catId.equals(catsList.get(i).getCatId()))
                return catsList.get(i).getName();
        }
        return "NO CATEGORY";
    }

    private String returnTimingStr(String timeId) {
        for (int i = 0; i < timingsSize; i++) {
            if (timeId.equals(timingsList.get(i).getTimeId()))
                return timingsList.get(i).getStartTime() + " - " + timingsList.get(i).getEndTime();
        }
        return "OFF - OFF";
    }

    private void queryCategories() {
        showNotCancellableLoader();
        final Observable<List<FetchCatsResponse>> call = apiInterface.queryCategories();
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseCatsResponse);
    }

    private void parseCatsResponse(List<FetchCatsResponse> response) {
        if (response != null && response.size() > 0) {
            catsList = response;
            catsSize = response.size();
            queryTimings();
        }
    }

    private void queryTimings() {
        Log.i(TAG, "Date: " + utils.getTodaysMDYDate());
        Log.i(TAG, "Weekday: " + utils.getWeekDay());
        final Observable<List<FetchTimeSlotsResponse>> call = apiInterface.queryTimeSlots(
                utils.getTodaysMDYDate(), utils.getWeekDay());
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseTimingsResponse);
    }

    private void parseTimingsResponse(List<FetchTimeSlotsResponse> response) {
        if (response != null && response.size() > 0) {
            timingsList = response;
            timingsSize = response.size();
            queryAppointments();
        }
    }

    @Override
    protected void onStop() {
        hideLoader();
        super.onStop();
    }
}