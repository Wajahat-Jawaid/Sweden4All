package com.sweden4all.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sweden4all.R;
import com.sweden4all.constants.Constants;
import com.sweden4all.responses.UserResponse;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.HomeView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ActHome extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIfUserIsActive();
    }

    private void checkIfUserIsActive() {
        showLoader();
        final Observable<UserResponse> call =
                apiInterface.getUser(prefs.getString(Constants.USER_ID));
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseUserResponse);
    }

    private void parseUserResponse(UserResponse response) {
        if (response != null) {
            String isActive = response.isActive();
            if (isActive.equals("1")) {
                // User is active
                hideLoader();
            } else {
                hideLoader();
                finishWithMessage(R.string.acc_inactive_contact);
            }
        } else hideLoader();
    }

    @Override
    public BaseView getView() {
        return new HomeView(this);
    }
}