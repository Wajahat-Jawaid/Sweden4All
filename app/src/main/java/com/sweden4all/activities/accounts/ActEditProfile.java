package com.sweden4all.activities.accounts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.constants.Constants;
import com.sweden4all.models.User;
import com.sweden4all.responses.UserResponse;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.accounts.EditProfileView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActEditProfile extends BaseActivity {

    private static final String TAG = "ActSignUp";

    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDataOnView();
    }

    @Override
    public BaseView getView() {
        return new EditProfileView(this);
    }

    private void setDataOnView() {
        User user = new User();
        user.setAboutMe(prefs.getString(Constants.ABOUT_ME));
        user.setEmail(prefs.getString(Constants.EMAIL));
        user.setPhone(prefs.getString(Constants.PHONE));
        user.setCity(prefs.getString(Constants.CITY));
        user.setCountry(prefs.getString(Constants.COUNTRY));
        ((EditProfileView) view).setData(user);
    }

    public void triggerEdit(String query) {
        showLoader();
        Log.i(TAG, "User Id: " + prefs.getString(Constants.USER_ID));
        Log.i(TAG, "query: " + query);
        final Observable<UserResponse> call = apiInterface.editProfile(
                prefs.getString(Constants.USER_ID), query);
        mDisposable = call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseResponse);
    }

    private void parseResponse(UserResponse response) {
        try {
            if (response != null) {
                Log.i(TAG, "Response: " + new Gson().toJson(response));
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStop() {
        if (mDisposable != null && !mDisposable.isDisposed()) mDisposable.dispose();
        ((EditProfileView) view).onStop();
        hideLoader();
        super.onStop();
    }
}