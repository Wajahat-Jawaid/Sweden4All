package com.sweden4all.activities.appointments;

import com.sweden4all.activities.BaseActivity;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.appointments.AppointmentsListView;

public class ActListAppointments extends BaseActivity {

    @Override
    public BaseView getView() {
        return new AppointmentsListView(this);
    }
}