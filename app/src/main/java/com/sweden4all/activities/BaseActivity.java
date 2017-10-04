package com.sweden4all.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sweden4all.R;
import com.sweden4all.app.Sweden4AllApp;
import com.sweden4all.database.SharedPrefs;
import com.sweden4all.events.DialogETCallBack;
import com.sweden4all.network.ApiInterface;
import com.sweden4all.utils.Utils;
import com.sweden4all.views.BaseView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import javax.inject.Inject;

import okhttp3.RequestBody;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    public Context context;
    public BaseView view;
    private FragmentManager mFragmentManager;
    private ConnectivityManager mConnectivityManager;
    private Resources mResources;
    private RxPermissions mRxPermissions;
    private NetworkInfo mNetworkInfo;
    private ProgressDialog mProgressDialog;

    @Inject
    public Utils utils;
    @Inject
    public ApiInterface apiInterface;
    @Inject
    public SharedPrefs prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Sweden4AllApp.getInstance().getComponent().inject(this);
        context = this;
        view = getView();
        super.onCreate(savedInstanceState);
        view.initializeView();
        init();
    }

    private void init() {
        if (mConnectivityManager == null)
            mConnectivityManager = (ConnectivityManager) Sweden4AllApp.getInstance()
                    .getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        if (mNetworkInfo == null) mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mResources == null) mResources = getResources();
        if (mRxPermissions == null) mRxPermissions = new RxPermissions(this);
    }

    /**
     * UTILS
     */
    public Resources getMResources() {
        return mResources;
    }

    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    public boolean hasInternet() {
        return mNetworkInfo.isConnectedOrConnecting();
    }

    public FragmentManager getBaseFragmentManager() {
        if (mFragmentManager == null) mFragmentManager = getSupportFragmentManager();
        return mFragmentManager;
    }

    public void finishWithMessage() {
        finishWithMessage(R.string.no_internet);
    }

    public void finishWithMessage(@StringRes int error) {
        showToast(error);
        finish();
    }

    public void finishWithMessage(String error) {
        showToast(error);
        finish();
    }

    public boolean isLocationEnabled() {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(),
                        Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * SHORT MESSAGES
     */

    public void showSnackBar(@StringRes int str) {
        view.showSnackBar(getResString(str), false);
    }

    public void showSnackBar(String str) {
        view.showSnackBar(str, false);
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
        Toast.makeText(this, str, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    protected void switchActivity(Class<? extends BaseActivity> activity, int flags) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(flags);
        startActivity(intent);
    }

    public void switchActivity(Class<? extends BaseActivity> activity, Bundle extras) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(extras);
        startActivity(intent);
    }

    protected void switchActivity(Class<? extends BaseActivity> activity, Bundle extras, int flags) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(flags);
        intent.putExtras(extras);
        startActivity(intent);
    }

    protected void switchActivityForResult(Class<? extends BaseActivity> activity, Bundle extras,
                                           int requestCode) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(extras);
        startActivityForResult(intent, requestCode);
    }

    /**
     * DIALOGS
     */
    public void showLoader() {
        if (mProgressDialog == null)
            initProgressDialog();
        mProgressDialog.show();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Loading...");
    }

    public void hideLoader() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void showDialog(String title, String message) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(message)
                .positiveText(android.R.string.ok)
                .show();
    }

    public void showDialogWithSingleCallback(String title, String message, String positiveText,
                                             MaterialDialog.SingleButtonCallback positiveCall) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(message)
                .positiveText(positiveText)
                .onPositive(positiveCall)
                .show();
    }

    public MaterialDialog.Builder showDialogWithTwoCallbacks(String title, String message,
                                                             String positiveText,
                                                             MaterialDialog.SingleButtonCallback positiveCall,
                                                             String negativeText,
                                                             MaterialDialog.SingleButtonCallback negativeCall) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(title)
                .content(message)
                .positiveText(positiveText)
                .onPositive(positiveCall)
                .negativeText(negativeText)
                .onNegative(negativeCall);
        builder.show();

        return builder;
    }

    public void showDialogWithDismissCallback(@StringRes int title, @StringRes int message,
                                              int positiveText,
                                              MaterialDialog.SingleButtonCallback positiveCall) {
        new MaterialDialog.Builder(this).title(title)
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
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this).title(title)
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

    public void showDialogWithInput(@StringRes int title, @StringRes int msg,
                                    @StringRes int hint, int inputType,
                                    DialogETCallBack inputCall) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        final View view = this.getLayoutInflater().inflate(R.layout.input_dlg_view, null);
//        ((TextView) view.findViewById(R.id.tv_title)).setText(getResString(title));
//        ((TextView) view.findViewById(R.id.tv_message)).setText(getResString(msg));
//        final EditText input = (EditText) view.findViewById(R.id.et_input);
//        input.setInputType(inputType);
//        input.setHint(getResString(hint));
//        builder.setView(view);
//        final AlertDialog dialog = builder.create();
//        ((RippleView) view.findViewById(R.id.rv_cancel)).setOnRippleCompleteListener(v -> {
//            dialog.dismiss();
//        });
//        ((RippleView) view.findViewById(R.id.rv_ok)).setOnRippleCompleteListener(v -> {
//            if (!TextUtils.isEmpty(utils.EToS(input))) {
//                inputCall.onInput(utils.EToS(input));
//                dialog.dismiss();
//            } else showToast(R.string.acc_created_successfully);
//        });
//        dialog.show();
    }

    /**
     * ABSTRACT METHODS
     */
    public abstract BaseView getView();

    /**
     * TEMP
     */
    public void printLogs(Object... str) {
        for (Object aStr : str) {
            if (aStr instanceof String || aStr instanceof Integer || aStr instanceof Double) {
                Log.i(TAG, String.valueOf(aStr));
            }
        }
    }
}