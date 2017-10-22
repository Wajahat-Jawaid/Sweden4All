package com.sweden4all.views.appointments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.sweden4all.R;
import com.sweden4all.activities.appointments.ActListAppointments;
import com.sweden4all.activities.appointments.ActScheduleAppointment;
import com.sweden4all.adapters.AppointmentsPagerAdapter;
import com.sweden4all.constants.Constants;
import com.sweden4all.fragments.BaseFragment;
import com.sweden4all.fragments.FgHistoryAppointments;
import com.sweden4all.fragments.FgTodayAppointments;
import com.sweden4all.fragments.FgUpcomingAppointments;
import com.sweden4all.models.Appointment;
import com.sweden4all.utils.RippleView;
import com.sweden4all.views.BaseView;

import java.util.ArrayList;

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

        viewPager = findViewFromId(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);

        setListeners();
    }

    private void setListeners() {
        ((RippleView) findViewFromId(R.id.rv_make_appoint)).setOnRippleCompleteListener(v -> {
            switchActivity(ActScheduleAppointment.class);
        });
    }

    public void setupViewPager(ArrayList<Appointment> listHistory,
                               ArrayList<Appointment> listTodays,
                               ArrayList<Appointment> listUpcoming) {
        AppointmentsPagerAdapter adapter = new AppointmentsPagerAdapter
                (((ActListAppointments) context).getSupportFragmentManager());
        final FgHistoryAppointments fgHistory = new FgHistoryAppointments();
        addFragmentToAdapter(adapter, listHistory, Constants.HISTORY, TAB_HISTORY, fgHistory);
        final FgTodayAppointments fgToday = new FgTodayAppointments();
        addFragmentToAdapter(adapter, listTodays, Constants.TODAYS, TAB_TODAY, fgToday);
        final FgUpcomingAppointments fgUpcoming = new FgUpcomingAppointments();
        addFragmentToAdapter(adapter, listUpcoming, Constants.UPCOMING, TAB_UPCOMING, fgUpcoming);

        viewPager.setAdapter(adapter);

        setTabLayout();
    }

    private void addFragmentToAdapter(AppointmentsPagerAdapter adapter, ArrayList<Appointment> list,
                                      String bundleTag, String name,
                                      BaseFragment fragment) {
        if (list != null && list.size() > 0) {
            Bundle bundleHistory = new Bundle();
            bundleHistory.putParcelableArrayList(bundleTag, list);
            fragment.setArguments(bundleHistory);
        }
        adapter.addFragment(fragment, name);
    }

    private void setTabLayout() {
        final TabLayout tabLayout = findViewFromId(R.id.tabs_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}