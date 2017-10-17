package com.sweden4all.views.accounts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sweden4all.R;
import com.sweden4all.activities.accounts.ActLogIn;
import com.sweden4all.activities.accounts.ActSignUp;
import com.sweden4all.constants.Constants;
import com.sweden4all.utils.RippleView;
import com.sweden4all.views.BaseView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LogInView extends BaseView implements RippleView.OnRippleCompleteListener {

    private static final String TAG = "SignInView";
    private boolean isInfoValid;

    private final Drawable enabled, disabled;
    private Disposable mDisposable;

    private EditText etEmail, etPwd;
    private RippleView rvLogin;

    public LogInView(Context context) {
        super(context);
        enabled = ContextCompat.getDrawable(context, Constants.BTN_ENABLED_BG);
        disabled = ContextCompat.getDrawable(context, Constants.BTN_DISABLED_BG);
    }

    @Override
    public int layout() {
        return R.layout.act_login;
    }

    @Override
    public void onCreate() {
        etEmail = findViewFromId(R.id.et_email);
        etPwd = findViewFromId(R.id.et_pwd);
        rvLogin = findViewFromId(R.id.rv_login);

        setToolbar(R.string.login);
        setListeners();
        registerObservables();
    }

    private void setListeners() {
        ((RippleView) findViewFromId(R.id.rv_login)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_sign_up)).setOnRippleCompleteListener(this);
        etPwd.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                if (isInfoValid)
                    triggerSignIn();
                return true;
            }
            return false;
        });
    }

    private void registerObservables() {
        Observable<CharSequence> emailOb = RxTextView.textChanges(etEmail);
        Observable<CharSequence> pwdOb = RxTextView.textChanges(etPwd);

        mDisposable = Observable.combineLatest(
                emailOb, pwdOb,
                (o1, o2) ->
                        !TextUtils.isEmpty(o1) && !TextUtils.isEmpty(o2))
                .subscribe(this::updateSignInBtn);
    }

    private void updateSignInBtn(boolean enable) {
        Log.i(TAG, "enable: " + enable);
        isInfoValid = enable;
        rvLogin.setEnabled(enable);
        rvLogin.setBackground(enable ? enabled : disabled);
    }

    private void triggerSignIn() {
        // Trigger now
        ((ActLogIn) context).triggerSignIn(utils.EToS(etEmail), utils.EToS(etPwd));
    }

    @Override
    public void onComplete(RippleView v) {
        switch (v.getId()) {
            case R.id.rv_login:
                triggerSignIn();
                break;
            case R.id.rv_sign_up:
                ((ActLogIn) context).switchActivity(ActSignUp.class);
                break;
        }
    }

    public void onStop() {
        disposeSubscriber();
        nullifyAssets();
    }

    private void disposeSubscriber() {
        if (mDisposable != null && !mDisposable.isDisposed()) mDisposable.dispose();
    }

    private void nullifyAssets() {
    }
}