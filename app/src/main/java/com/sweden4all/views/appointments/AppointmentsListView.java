package com.sweden4all.views.appointments;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.sweden4all.R;
import com.sweden4all.activities.appointments.ActListAppointments;
import com.sweden4all.activities.appointments.ActScheduleAppointment;
import com.sweden4all.adapters.AppointmentsPagerAdapter;
import com.sweden4all.fragments.FgAppointmentsHistory;
import com.sweden4all.utils.RippleView;
import com.sweden4all.views.BaseView;

public class AppointmentsListView extends BaseView {

    private static final String TAG = "AppointmentsListView";

    private static final String TAB_HISTORY = "history";
    private static final String TAB_TODAY = "today";
    private static final String TAB_UPCOMING = "upcoming";

    private ViewPager viewPager;

    public AppointmentsListView(Context context) {
        super(context);
    }

    @Override
    public int layout() {
        return R.layout.act_list_appointments;
    }

    @Override
    public void onCreate() {
        setToolbar(R.string.appointments);

        TabLayout tabLayout = findViewFromId(R.id.tabs_layout);
        viewPager = findViewFromId(R.id.view_pager);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);

        setListeners();
    }

    private void setListeners() {
        ((RippleView) findViewFromId(R.id.rv_make_appoint)).setOnRippleCompleteListener(v -> {
            switchActivity(ActScheduleAppointment.class);
        });
    }

    private void setupViewPager() {
        AppointmentsPagerAdapter adapter = new AppointmentsPagerAdapter
                (((ActListAppointments) context).getSupportFragmentManager());

        adapter.addFragment(new FgAppointmentsHistory(), TAB_HISTORY);
        adapter.addFragment(new FgAppointmentsHistory(), TAB_TODAY);
        adapter.addFragment(new FgAppointmentsHistory(), TAB_UPCOMING);
        viewPager.setAdapter(adapter);
    }
}