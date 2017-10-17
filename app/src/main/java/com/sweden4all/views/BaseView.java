package com.sweden4all.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sweden4all.R;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.app.DrawerManager;
import com.sweden4all.app.Sweden4AllApp;
import com.sweden4all.database.SharedPrefs;
import com.sweden4all.events.OnDateSelectedListener;
import com.sweden4all.network.ApiInterface;
import com.sweden4all.utils.RippleView;
import com.sweden4all.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public abstract class BaseView extends View {

    private static final String TAG = "BaseView";

    public View view;
    public Context context;
    private LinearLayoutManager linearLayoutManager;
    private Resources resources;
    private RxPermissions rxPerms;
    private DrawerManager drawerManager;

    @Inject
    public Utils utils;
    @Inject
    public ApiInterface apiInterface;
    @Inject
    public SharedPrefs prefs;

    public BaseView(Context context) {
        super(context);
        this.context = context;
        Sweden4AllApp.getInstance().getComponent().inject(this);
        view = LayoutInflater.from(context).inflate(layout(), null);
        ((AppCompatActivity) context).addContentView(view,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        init();
    }

    private void init() {
        if (linearLayoutManager == null)
            linearLayoutManager = new LinearLayoutManager(context);
        if (resources == null) resources = getResources();
        if (rxPerms == null) rxPerms = new RxPermissions((Activity) context);
    }

    /**
     * UTILS
     */

    public boolean hasInternet() {
        return ((BaseActivity) context).hasInternet();
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }

    public void initializeView() {
        onCreate();
    }

    public Resources getMResources() {
        return resources;
    }

    public RxPermissions getRxPerms() {
        return rxPerms;
    }

    /**
     * VIEWS /
     */
    public void setDrawer() {
        if (view.findViewById(R.id.drawer_layout) != null)
            if (drawerManager == null)
                drawerManager = new DrawerManager(context, view, prefs);
            else drawerManager.setHeader(prefs);
    }

    public void setToolbar(@StringRes int text, @DrawableRes int icon) {
        TextView title = findViewFromId(R.id.tv_title);
        title.setText(getResString(text));
        if (icon != -1) {
//            ImageButton image = findViewFromId(R.id.ib_icon);
//            image.setImageResource(icon);
        }
        if (findViewFromId(R.id.rv_back) != null)
            ((RippleView) findViewFromId(R.id.rv_back))
                    .setOnRippleCompleteListener(v -> ((Activity) context).finish());
    }

    public void setToolbar(@StringRes int text) {
        TextView title = findViewFromId(R.id.tv_title);
        title.setText(getResString(text));
        if (findViewFromId(R.id.rv_back) != null)
            ((RippleView) findViewFromId(R.id.rv_back))
                    .setOnRippleCompleteListener(v -> ((Activity) context).finish());
    }

    protected <T extends View> T findViewFromId(int id) {
        return (T) view.findViewById(id);
    }

    /**
     * SHORT MESSAGES
     */
    public void showSnackBar(@StringRes int str) {
        showSnackBar(getResString(str), false);
    }

    public void showSnackBar(String str) {
        showSnackBar(str, false);
    }

    public void showSnackBar(String str, Boolean isShort) {
        Snackbar.make(view, str, isShort ? Snackbar.LENGTH_SHORT : Snackbar.LENGTH_LONG).show();
    }

    public void showToast(@StringRes int str) {
        showToast(getResString(str), true);
    }

    public void showToast(String str) {
        showToast(str, true);
    }

    public void showToast(String str, boolean isShort) {
        Toast.makeText(context, str, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }

    public String getResString(@StringRes int id) {
        return getMResources().getString(id);
    }

    /**
     * DIALOGS
     */
    public void showDialogWithSingleCallback(String title, String message, int positiveText,
                                             MaterialDialog.SingleButtonCallback positiveCall) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(positiveText)
                .onPositive(positiveCall)
                .show();
    }

    public void showDialogForDismissCallback(String title, String message) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(android.R.string.ok)
                .onPositive((dialog1, which) -> dialog1.dismiss())
                .show();
    }

    public void showDialog(String title, String message) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(android.R.string.ok)
                .show();
    }

    public void showDialogWithSingleCallback(String title, String message, String positiveText,
                                             MaterialDialog.SingleButtonCallback positiveCall) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(positiveText)
                .onPositive(positiveCall)
                .show();
    }

    public MaterialDialog.Builder showDialogWithTwoCallbacks(String title, String message,
                                                             @StringRes int positiveText,
                                                             MaterialDialog.SingleButtonCallback positiveCall,
                                                             @StringRes int negativeText,
                                                             MaterialDialog.SingleButtonCallback negativeCall) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(positiveText)
                .onPositive(positiveCall)
                .negativeText(negativeText)
                .onNegative(negativeCall);
        builder.show();

        return builder;
    }

    public void showDialogWithDismissCallback(String title, String message,
                                              int positiveText,
                                              MaterialDialog.SingleButtonCallback positiveCall) {
        new MaterialDialog.Builder(context).title(title)
                .content(message)
                .positiveText(positiveText)
                .onPositive(positiveCall)
                .negativeText(android.R.string.cancel)
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    public MaterialDialog.Builder showDialogWithInput(@StringRes int title, @StringRes int message,
                                                      @StringRes int positiveText,
                                                      MaterialDialog.SingleButtonCallback positiveCall,
                                                      @StringRes int hint, @StringRes int preFilled,
                                                      @StringRes int inputType, boolean canBeEmpty,
                                                      MaterialDialog.InputCallback inputCallback,
                                                      DialogInterface.OnDismissListener dismissListener) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context).title(title)
                .content(message)
                .input(hint, preFilled, canBeEmpty, inputCallback)
                .inputType(inputType)
                .positiveText(positiveText)
                .onPositive(positiveCall)
                .negativeText(android.R.string.cancel)
                .onNegative((dialog, which) -> dialog.dismiss())
                .dismissListener(dismissListener);
        builder.show();

        return builder;
    }

    /**
     * ABSTRACT METHODS
     */
    public abstract int layout();

    public abstract void onCreate();

    /**
     * ACTIVITY LAUNCHERS
     */
    public void switchActivity(Class<? extends BaseActivity> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    protected void switchActivity(Class<? extends BaseActivity> activity, int flags) {
        Intent intent = new Intent(context, activity);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    public void switchActivity(Class<? extends BaseActivity> activity, Bundle extras) {
        Intent intent = new Intent(context, activity);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    protected void switchActivity(Class<? extends BaseActivity> activity, Bundle extras, int flags) {
        Intent intent = new Intent(context, activity);
        intent.setFlags(flags);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public static Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }

    private OnDateSetListener dateSetListener;

    public void showDatePicker(OnDateSelectedListener listener) {
        if (dateSetListener == null)
            dateSetListener = new OnDateSetListener(listener);
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(
                        context,
                        dateSetListener,
                        getCurrentCalendar().get(Calendar.YEAR),
                        getCurrentCalendar().get(Calendar.MONTH),
                        getCurrentCalendar().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private final class OnDateSetListener implements DatePickerDialog.OnDateSetListener {

        private OnDateSelectedListener listener;

        OnDateSetListener(OnDateSelectedListener listener) {
            this.listener = listener;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE", Locale.getDefault());
            Date date = new Date(year, month, day - 1);
            String dayOfWeek = simpledateformat.format(date);
            listener.onDateSet(utils.toYMDFormat(year, month, day), dayOfWeek);
        }
    }
}