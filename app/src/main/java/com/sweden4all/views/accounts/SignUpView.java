package com.sweden4all.views.accounts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sweden4all.R;
import com.sweden4all.activities.accounts.ActLogin;
import com.sweden4all.activities.accounts.ActSignUp;
import com.sweden4all.constants.Constants;
import com.sweden4all.events.OnDateSelectedListener;
import com.sweden4all.utils.RippleView;
import com.sweden4all.views.BaseView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class SignUpView extends BaseView implements RippleView.OnRippleCompleteListener,
        OnDateSelectedListener {

    private static final String TAG = "SignUpView";
    private boolean isInfoValid;
    private String formattedDate = null;

    private final Drawable enabled, disabled;
    private Disposable mDisposable;

    private EditText etName, etEmail, etPhone, etCity, etCountry, etDob, etPwd, etCnfrmPwd;
    private RippleView rvSignUp;

    public SignUpView(Context context) {
        super(context);
        enabled = ContextCompat.getDrawable(context, Constants.BTN_ENABLED_BG);
        disabled = ContextCompat.getDrawable(context, Constants.BTN_DISABLED_BG);
    }

    @Override
    public int layout() {
        return R.layout.act_sign_up;
    }

    @Override
    public void onCreate() {
        etName = findViewFromId(R.id.et_name);
        etEmail = findViewFromId(R.id.et_email);
        etPhone = findViewFromId(R.id.et_phone);
        etCity = findViewFromId(R.id.et_city);
        etCountry = findViewFromId(R.id.et_country);
        etDob = findViewFromId(R.id.et_dob);
        etPwd = findViewFromId(R.id.et_pwd);
        etCnfrmPwd = findViewFromId(R.id.et_cnfrm_pwd);
        rvSignUp = findViewFromId(R.id.rv_sign_up);

        setToolbar(R.string.sign_up);
        setListeners();
        registerObservables();
    }

    private void setListeners() {
        RxView.clicks(etDob).subscribe(__ -> showDatePicker(this));
        ((RippleView) findViewFromId(R.id.rv_sign_in)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_sign_up)).setOnRippleCompleteListener(this);
        etCnfrmPwd.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                if (isInfoValid)
                    triggerSignUp();
                return true;
            }
            return false;
        });
    }

    private void registerObservables() {
        final Observable<CharSequence> nameOb = RxTextView.textChanges(etName);
        final Observable<CharSequence> emailOb = RxTextView.textChanges(etEmail);
        final Observable<CharSequence> phoneOb = RxTextView.textChanges(etPhone);
        final Observable<CharSequence> cityOb = RxTextView.textChanges(etCity);
        final Observable<CharSequence> countryOb = RxTextView.textChanges(etCountry);
        final Observable<CharSequence> dobOb = RxTextView.textChanges(etDob);
        final Observable<CharSequence> pwdOb = RxTextView.textChanges(etPwd);
        final Observable<CharSequence> cnfrmPwdPOb = RxTextView.textChanges(etCnfrmPwd);

        mDisposable = Observable.combineLatest(
                nameOb, emailOb, phoneOb, cityOb, countryOb, dobOb, pwdOb, cnfrmPwdPOb,
                (o1, o2, o3, o4, o5, o6, o7, o8) ->
                        !TextUtils.isEmpty(o1) && !TextUtils.isEmpty(o2) &&
                                !TextUtils.isEmpty(o3) && !TextUtils.isEmpty(o4) &&
                                !TextUtils.isEmpty(o5) && !TextUtils.isEmpty(o6) &&
                                !TextUtils.isEmpty(o7) && !TextUtils.isEmpty(o8))
                .subscribe(this::updateSignUpBtn);
    }

    private void updateSignUpBtn(boolean enable) {
        isInfoValid = enable;
        rvSignUp.setEnabled(enable);
        rvSignUp.setBackground(enable ? enabled : disabled);
    }

    private void triggerSignUp() {
        // Trigger now
        ((ActSignUp) context).triggerSignUp(utils.EToS(etName), utils.EToS(etEmail),
                utils.EToS(etPhone), utils.EToS(etCity), utils.EToS(etCountry),
                formattedDate, utils.EToS(etPwd), utils.EToS(etCnfrmPwd));
    }

    @Override
    public void onComplete(RippleView v) {
        switch (v.getId()) {
            case R.id.rv_sign_up:
                triggerSignUp();
                break;
            case R.id.rv_sign_in:
                ((ActSignUp) context).switchActivity(ActLogin.class);
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

    @Override
    public void onDateSet(String date, String day) {
        formattedDate = date;
        etDob.setText(formattedDate);
    }
}