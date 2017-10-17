package com.sweden4all.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sweden4all.R;
import com.sweden4all.adapters.AppointmentsListAdapter;
import com.sweden4all.app.Sweden4AllApp;
import com.sweden4all.constants.Constants;
import com.sweden4all.models.Appointment;
import com.sweden4all.network.ApiInterface;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ZZAppointmentsHistory extends AppCompatActivity {

    private static final String TAG = "FgAppointmentsHistory";

    @Inject
    ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sweden4AllApp.getInstance().getComponent().inject(this);
        setContentView(R.layout.zz_history);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FgAppointmentsHistory()).commit();
//        fetchHistory();
    }

    private void fetchHistory() {
//        Log.i(TAG, "Todays date: " + utils.getTodaysFormattedDate());
//        Log.i(TAG, "User Id: " + prefs.getString(Constants.USER_ID));
        final Observable<List<Appointment>> call = apiInterface.fetchTodayAppointments(
                "97", "10-17-2017");
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseResponse);
    }

    private void parseResponse(List<Appointment> list) {
        Log.i(TAG, "List size: " + list.size());
        if (list.size() > 0) {
            Log.i(TAG, "Initializing recycler");
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            Log.i(TAG, "1");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            Log.i(TAG, "2");
            recyclerView.setAdapter(new AppointmentsListAdapter(list));
            Log.i(TAG, "3");
//            recyclerView.setHasFixedSize(true);
        }
    }
}