package com.sweden4all.activities.accounts;

import android.support.annotation.NonNull;

import com.sweden4all.R;
import com.sweden4all.activities.ActHome;
import com.sweden4all.activities.BaseActivity;
import com.sweden4all.responses.UserResponse;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.accounts.LogInView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActLogIn extends BaseActivity {

    private static final String TAG = "ActLogIn";

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
                showSnackBar(R.string.logged_in);
                switchActivity(ActHome.class);
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