package com.sweden4all.views.appointments;

import android.content.Context;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sweden4all.R;
import com.sweden4all.activities.appointments.ActAppointmentDetails;
import com.sweden4all.fragments.DlgFgAppointReasonDetails;
import com.sweden4all.utils.RippleView;
import com.sweden4all.views.BaseView;

public class AppointmentDetailsView extends BaseView {

    public AppointmentDetailsView(Context context) {
        super(context);
    }

    @Override
    public int layout() {
        return R.layout.act_appointment_details;
    }

    @Override
    public void onCreate() {
        ((RippleView) findViewFromId(R.id.rv_info)).setOnRippleCompleteListener(v -> {
            DlgFgAppointReasonDetails fg = new DlgFgAppointReasonDetails();
            fg.show(((ActAppointmentDetails) context).getSupportFragmentManager(), "reason");
        });
        ((RippleView) findViewFromId(R.id.rv_del))
                .setOnRippleCompleteListener(v -> showCnfrmDelDlg());
    }

    private void showCnfrmDelDlg() {
        MaterialDialog.SingleButtonCallback posCall = (dialog, which) -> {
            if (hasInternet()) {
                ((ActAppointmentDetails) context).triggerDeleteCall();
            } else showSnackBar(R.string.no_internet);
        };
        showDialogWithDismissCallback(R.string.cnfrm_del_app,
                R.string.u_sure_to_del_app,
                android.R.string.yes,
                posCall);
    }

    public void setData(String status, String date, String time, String cat, String reason) {
        ((TextView) findViewFromId(R.id.tv_status)).setText(status);
        ((TextView) findViewFromId(R.id.tv_date)).setText(date);
        ((TextView) findViewFromId(R.id.tv_timings)).setText(time);
        ((TextView) findViewFromId(R.id.tv_cat)).setText(cat);
        ((TextView) findViewFromId(R.id.tv_reason)).setText(reason);
    }
}