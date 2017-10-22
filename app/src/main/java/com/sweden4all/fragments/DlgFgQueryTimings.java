package com.sweden4all.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sweden4all.R;
import com.sweden4all.adapters.FetchTimeSlotsAdapter;
import com.sweden4all.constants.Constants;
import com.sweden4all.events.RecyclerItemClickListener;
import com.sweden4all.responses.FetchTimeSlotsResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DlgFgQueryTimings extends BaseDialogFragment {

    private static final String TAG = "DlgFgQueryTimings";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dlg_fg_query_simple_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(view);
        queryTimings();
    }

    private void setTitle(View view) {
        ((TextView) view.findViewById(R.id.tv_title)).setText(getResString(R.string.select_timings));
    }

    private void queryTimings() {
        final Observable<List<FetchTimeSlotsResponse>> call = apiInterface.queryTimeSlots(
                prefs.getString(Constants.DATE_OF_APP), prefs.getString(Constants.WEEKDAY));
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseResponse);
    }

    private void parseResponse(List<FetchTimeSlotsResponse> response) {
        Log.i(TAG, "response: " + new Gson().toJson(response));
        if (response != null && response.size() > 0) {
            View view = getView();
            if (view == null)
                return;
            final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new FetchTimeSlotsAdapter(response));
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, (view1, position) -> {
                        FetchTimeSlotsResponse resp = response.get(position);
                        Log.i(TAG, "selected time: " + resp.getStartTime() + " - " +
                                resp.getEndTime());
                        prefs.insert(Constants.TIME_ID, resp.getTimeId());
                        prefs.insert(Constants.TIMING, resp.getStartTime() + " - " +
                                resp.getEndTime());
                        hideLoader();
                        dismiss();
                    }));
        }
        hideProgress();
    }

    private void hideProgress() {
        View v = getView();
        if (v == null)
            return;
        v.findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }
}