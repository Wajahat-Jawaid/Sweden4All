package com.sweden4all.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sweden4all.R;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.app.Sweden4AllApp;
import com.sweden4all.database.SharedPrefs;
import com.sweden4all.network.ApiInterface;
import com.sweden4all.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import javax.inject.Inject;

import okhttp3.RequestBody;

public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    public Context context;
    public Activity activity;
    private RxPermissions rxPerms;
    private ConnectivityManager mConnectivityManager;
    private Resources mResources;
    private NetworkInfo mNetworkInfo;
    private ProgressDialog mProgressDialog;

    @Inject
    public Utils utils;
    @Inject
    public ApiInterface apiInterface;
    @Inject
    public SharedPrefs prefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sweden4AllApp.getInstance().getComponent().inject(this);
        init();
    }

    private void init() {
        context = getContext();
        activity = getActivity();
        if (mConnectivityManager == null)
            mConnectivityManager = (ConnectivityManager) Sweden4AllApp.getInstance()
                    .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mNetworkInfo == null) mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (rxPerms == null) rxPerms = new RxPermissions(getActivity());
        if (mResources == null) mResources = getResources();
    }

    /**
     * UTILS
     */
    public Resources getMResources() {
        return mResources;
    }

    public RxPermissions getRxPerms() {
        return rxPerms;
    }

    public boolean hasInternet() {
        return mNetworkInfo.isConnectedOrConnecting();
    }

    public void finishActivityByShowingError(Activity activity, @StringRes int error) {
        showToast(error);
        activity.finish();
    }

    public void finishActivityByShowingError(Activity activity, String error) {
        showToast(error);
        activity.finish();
    }

    /**
     * SHORT MESSAGES
     */

//    public void showSnackBar(@StringRes int str) {
//        view.showSnackBar(getResString(str), false);
//    }
//
//    public void showSnackBar(String str) {
//        view.showSnackBar(str, false);
//    }
//
//    public void showSnackBar(String str, Boolean isShort) {
//        Snackbar.make(view, str, isShort ? Snackbar.LENGTH_SHORT : Snackbar.LENGTH_LONG).show();
//    }
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
     * Create the raw json RequestBody
     */
    public RequestBody getPayloadRequestBody(@NonNull final JSONObject raw) {
        final String finalJSON = "{\"data\":" + raw.toString() + "}";
        Log.i(TAG, "finalJSON: " + finalJSON);

        return RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"),
                finalJSON);
    }

    /**
     * ACTIVITY LAUNCHERS
     */
    public void switchActivity(Class<? extends BaseActivity> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }

    protected void switchActivity(Class<? extends BaseActivity> activity, int flags) {
        Intent intent = new Intent(context, activity);
        intent.setFlags(flags);
        startActivity(intent);
    }

    public void switchActivity(Class<? extends BaseActivity> activity, Bundle extras) {
        Intent intent = new Intent(context, activity);
        intent.putExtras(extras);
        startActivity(intent);
    }

    protected void switchActivity(Class<? extends BaseActivity> activity, Bundle extras, int flags) {
        Intent intent = new Intent(context, activity);
        intent.setFlags(flags);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * DIALOGS
     */
    public void showProgressDialog() {
        if (mProgressDialog == null)
            initProgressDialog();
        mProgressDialog.show();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Loading...");
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void showDialog(String title, String message) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(android.R.string.ok)
                .show();
    }

    public void showDialogWithSingleCallback(String title, String message, String positiveText,
                                             MaterialDialog.SingleButtonCallback positiveCall,
                                             boolean cancelable) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .cancelable(cancelable)
                .positiveText(positiveText)
                .onPositive(positiveCall)
                .show();
    }

    public MaterialDialog.Builder showDialogWithTwoCallbacks(String title, String message,
                                                             String positiveText,
                                                             MaterialDialog.SingleButtonCallback positiveCall,
                                                             String negativeText,
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

    public void showDialogWithInput(String title, String message,
                                    int positiveText,
                                    MaterialDialog.SingleButtonCallback positiveCall,
                                    String hint, String preFilled, boolean canBeEmpty,
                                    MaterialDialog.InputCallback inputCallback) {
        new MaterialDialog.Builder(context).title(title)
                .content(message)
                .input(hint, preFilled, canBeEmpty, inputCallback)
                .positiveText(positiveText)
                .onPositive(positiveCall)
                .negativeText(android.R.string.cancel)
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }
}