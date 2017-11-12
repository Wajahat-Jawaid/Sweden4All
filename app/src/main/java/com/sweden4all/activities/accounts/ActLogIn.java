package com.sweden4all.activities.accounts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.sweden4all.R;
import com.sweden4all.activities.ActHome;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.activities.appointments.ActListAppointments;
import com.sweden4all.constants.Constants;
import com.sweden4all.responses.UserResponse;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.accounts.LogInView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActLogin extends BaseActivity {

    private static final String TAG = "ActLogin";

    private Disposable mDisposable;

    @Override
    public BaseView getView() {
        return new LogInView(this);
    }

    public void triggerSignIn(final @NonNull String email, final @NonNull String pwd) {
        showLoader();
        final Observable<UserResponse> call = apiInterface.login(email, pwd);
        mDisposable = call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseResponse);
    }

    private void parseResponse(UserResponse response) {
        try {
            if (response != null) {
                Log.i(TAG, "Response: " + new Gson().toJson(response));
                showSnackBar(R.string.logged_in);
                prefs.insert(Constants.USER_ID, response.getId());
                prefs.insert(Constants.NAME, response.getName());
                prefs.insert(Constants.EMAIL, response.getEmail());
                prefs.insert(Constants.PASSWORD, response.getPwd());
                prefs.insert(Constants.PHONE, response.getPhone());
                prefs.insert(Constants.CITY, response.getCity());
                prefs.insert(Constants.COUNTRY, response.getCountry());
                prefs.insert(Constants.USER_DOB, response.getDob());
                prefs.insert(Constants.ABOUT_ME, response.getAboutMe());
                switchActivity(ActHome.class);
//                switchActivity(ActListAppointments.class);
//                switchActivity(ActEditProfile.class);
                finish();
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStop() {
        if (mDisposable != null && !mDisposable.isDisposed()) mDisposable.dispose();
        ((LogInView) view).onStop();
        hideLoader();
        super.onStop();
    }
}