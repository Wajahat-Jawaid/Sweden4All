package com.sweden4all.views.appointments;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.sweden4all.R;
import com.sweden4all.activities.appointments.ActScheduleAppointment;
import com.sweden4all.constants.Constants;
import com.sweden4all.events.OnDateSelectedListener;
import com.sweden4all.fragments.DlgFgQueryCategories;
import com.sweden4all.fragments.DlgFgQueryTimings;
import com.sweden4all.utils.RippleView;
import com.sweden4all.views.BaseView;

public class ScheduleAppointmentView extends BaseView implements
        RippleView.OnRippleCompleteListener, OnDateSelectedListener {

    private static final String TAG = "ScheduleAppointmentView";

    private TextView tvCat, tvDate, tvHour;
    private EditText etReason;

    public ScheduleAppointmentView(Context context) {
        super(context);
    }

    @Override
    public int layout() {
        return R.layout.act_schedule_appoint;
    }

    @Override
    public void onCreate() {
        etReason = findViewFromId(R.id.et_reason);
        tvCat = findViewFromId(R.id.tv_cat_val);
        tvDate = findViewFromId(R.id.tv_date);
        tvHour = findViewFromId(R.id.tv_hour);
        setListeners();
    }

    private void setListeners() {
        ((RippleView) findViewFromId(R.id.rv_select_cat)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_select_date)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_select_hour)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_send)).setOnRippleCompleteListener(this);
//        RxView.clicks(findViewFromId(R.id.tv_why_val)).subscribe(__ -> {
//        });
    }

    @Override
    public void onComplete(RippleView v) {
        switch (v.getId()) {
            case R.id.rv_select_cat:
                DlgFgQueryCategories dlg = new DlgFgQueryCategories();
                dlg.show(((ActScheduleAppointment) context).getSupportFragmentManager(), "cat");
                break;
            case R.id.rv_select_date:
                showDatePicker(this);
                break;
            case R.id.rv_select_hour:
                showTimeSlotDlgFg();
                break;
            case R.id.rv_send:
                triggerSend();
                break;
        }
    }

    private void showTimeSlotDlgFg() {
        if (!TextUtils.isEmpty(mDate)) {
            DlgFgQueryTimings dlgTime = new DlgFgQueryTimings();
            dlgTime.show(((ActScheduleAppointment) context).getSupportFragmentManager(), "time");
        } else showSnackBar(R.string.pick_date);
    }

    private void triggerSend() {
        if (validateInfo()) {
            Log.i(TAG, "mDate : " + mDate);
            ((ActScheduleAppointment) context).triggerSend(utils.EToS(etReason), mDate);
        }
    }

    private boolean validateInfo() {
        // 5 mandatory params
        // 1 - userID (at this place user ID must be exist so no validation)
        // 2 - catID (done)
        // 3 - timeID (done)
        // 4 - status (hard coded default 0)
        // 5 - dateOfApp (not required as its already been validated at the timeID)
        if (prefs.getString(Constants.CAT_ID).equals(Constants.INVALID_INT)) {
            showSnackBar(R.string.no_cat_selected);
            return false;
        }
        if (TextUtils.isEmpty(prefs.getString(Constants.TIMING))) {
            showSnackBar(R.string.no_time_selected);
            return false;
        }

        return true;
    }

    private String mDate;

    @Override
    public void onDateSet(String date, String day) {
        mDate = date;
        tvDate.setText(date);
        prefs.insert(Constants.DATE_OF_APP, date);
        prefs.insert(Constants.WEEKDAY, day);
    }

    public void setCategoryView() {
        if (!TextUtils.isEmpty(prefs.getString(Constants.CAT_NAME)))
            tvCat.setText(prefs.getString(Constants.CAT_NAME));
    }

    public void setTimingsView() {
        if (!TextUtils.isEmpty(prefs.getString(Constants.TIMING)))
            tvHour.setText(prefs.getString(Constants.TIMING));
    }
}