package com.sweden4all.activities.appointments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sweden4all.activities.BaseActivity;
import com.sweden4all.constants.Constants;
import com.sweden4all.models.Appointment;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.appointments.AppointmentDetailsView;

public class ActAppointmentDetails extends BaseActivity {

    private Appointment mAppointment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
        setDetailsView();
    }

    private void getExtras() {
        if (getIntent().getExtras() != null)
            mAppointment = getIntent().getExtras().getParcelable(Constants.APPOINTMENT);
        else finishWithMessage();
    }

    private void setDetailsView() {
        ((AppointmentDetailsView) view).setData(
                mAppointment.getStatus(),
                mAppointment.getDate(),
                mAppointment.getTimingsStr(),
                mAppointment.getCatStr(),
                mAppointment.getReason()
        );
    }

    public void triggerDeleteCall() {

    }

    private void parseDeleteResponse(final Appointment appointment) {

    }

    @Override
    public BaseView getView() {
        return new AppointmentDetailsView(this);
    }
}