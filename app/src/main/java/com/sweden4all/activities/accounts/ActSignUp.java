package com.sweden4all.activities.accounts;

import com.sweden4all.R;
import com.sweden4all.activities.ActHome;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.responses.UserResponse;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.accounts.SignUpView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActSignUp extends BaseActivity {

    private static final String TAG = "ActSignUp";

    private Disposable mDisposable;

    @Override
    public BaseView getView() {
        return new SignUpView(this);
    }

    public void triggerSignUp(String name, String email, String phone, String city,
                              String country, String dob, String pwd, String cnfrm) {
        showLoader();
        printLogs(name, email, phone, city, country, dob, pwd, cnfrm);
        // TODO Send FCM token instead of confirm password
        final Observable<UserResponse> call = apiInterface.register(
                name, email, phone, city, country, dob, pwd, cnfrm);
        mDisposable = call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::parseResponse);
    }

    private void parseResponse(UserResponse response) {
        try {
            if (response != null) {
                showSnackBar(R.string.acc_created);
                switchActivity(ActHome.class);
                finish();
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStop() {
        if (mDisposable != null && !mDisposable.isDisposed()) mDisposable.dispose();
        ((SignUpView) view).onStop();
        hideLoader();
        super.onStop();
    }
}