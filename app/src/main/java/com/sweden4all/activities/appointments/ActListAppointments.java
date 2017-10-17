package com.sweden4all.activities.appointments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sweden4all.activities.BaseActivity;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.appointments.AppointmentsListView;

public class ActListAppointments extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public BaseView getView() {
        return new AppointmentsListView(this);
    }
}