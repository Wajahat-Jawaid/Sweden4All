package com.sweden4all.activities.appointments;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sweden4all.R;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.constants.Constants;
import com.sweden4all.responses.ScheduleAppointResponse;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.appointments.ScheduleAppointmentView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActScheduleAppointment extends BaseActivity
        implements DialogInterface.OnDismissListener {

    private static final String TAG = "ActScheduleAppointment";

    @Override
    public BaseView getView() {
        return new ScheduleAppointmentView(this);
    }

    public void triggerSend(String reason, String date) {
        final Observable<ScheduleAppointResponse> call = apiInterface.scheduleAppoint(
                prefs.getInt(Constants.USER_ID),
                prefs.getInt(Constants.CAT_ID),
                prefs.getInt(Constants.TIME_ID),
                0,
                reason,
                date
        );
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseResponse);
    }

    private void parseResponse(ScheduleAppointResponse response) {
        Log.i(TAG, "response: " + new Gson().toJson(response));
        if (response != null) {
            if (!TextUtils.isEmpty(response.getAppointId())) {
                showToast(R.string.appoint_done_msg);
                finish();
            }
        }
        showToast(R.string.no_time_selected);
        finish();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        ((ScheduleAppointmentView) view).setCategoryView();
        ((ScheduleAppointmentView) view).setTimingsView();
    }
}