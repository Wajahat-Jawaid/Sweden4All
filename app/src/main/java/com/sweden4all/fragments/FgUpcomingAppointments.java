package com.sweden4all.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sweden4all.R;
import com.sweden4all.adapters.AppointmentsListAdapter;
import com.sweden4all.constants.Constants;
import com.sweden4all.models.Appointment;

import java.util.ArrayList;

public class FgUpcomingAppointments extends BaseFragment {

    private static final String TAG = "FgAppointmentsHistory";

    private ArrayList<Appointment> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            list = bundle.getParcelableArrayList(Constants.UPCOMING);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_appointments_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parseResponse();
    }

    private void parseResponse() {
        if (list != null) {
            if (list.size() > 0) {
                View v = getView();
                if (v == null)
                    return;
                final RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new AppointmentsListAdapter(list));
                recyclerView.setHasFixedSize(true);
            }
        }
    }
}