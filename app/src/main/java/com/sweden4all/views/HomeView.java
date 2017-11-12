package com.sweden4all.views;

import android.content.Context;

import com.sweden4all.R;
import com.sweden4all.activities.ActMessenger;
import com.sweden4all.activities.appointments.ActListAppointments;
import com.sweden4all.activities.appointments.ActScheduleAppointment;
import com.sweden4all.utils.RippleView;

public class HomeView extends BaseView implements RippleView.OnRippleCompleteListener {

    public HomeView(Context context) {
        super(context);
    }

    @Override
    public int layout() {
        return R.layout.act_home;
    }

    @Override
    public void onCreate() {
        setListeners();
    }

    private void setListeners() {
        ((RippleView) findViewFromId(R.id.rv_messenger)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_app_status)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_appointments)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_faq)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_make_appoint)).setOnRippleCompleteListener(this);
    }

    @Override
    public void onComplete(RippleView v) {
        switch (v.getId()) {
            case R.id.rv_messenger:
                switchActivity(ActMessenger.class);
                break;
            case R.id.rv_appointments:
                switchActivity(ActListAppointments.class);
                break;
            case R.id.rv_make_appoint:
                switchActivity(ActScheduleAppointment.class);
                break;
        }
    }
}