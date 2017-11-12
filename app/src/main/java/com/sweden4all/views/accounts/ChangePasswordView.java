package com.sweden4all.views.accounts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sweden4all.R;
import com.sweden4all.activities.accounts.ActChangePassword;
import com.sweden4all.constants.Constants;
import com.sweden4all.utils.RippleView;
import com.sweden4all.views.BaseView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ChangePasswordView extends BaseView {

    private static final String TAG = "ChangePasswordView";
    private boolean isInfoValid;

    private final Drawable enabled, disabled;
    private Disposable mDisposable;

    private EditText etOld, etNew, etCnfrm;
    private RippleView rvSubmit;

    public ChangePasswordView(Context context) {
        super(context);
        enabled = ContextCompat.getDrawable(context, Constants.BTN_ENABLED_BG);
        disabled = ContextCompat.getDrawable(context, Constants.BTN_DISABLED_BG);
    }

    @Override
    public int layout() {
        return R.layout.act_change_password;
    }

    @Override
    public void onCreate() {
        etOld = findViewFromId(R.id.et_old);
        etNew = findViewFromId(R.id.et_new);
        etCnfrm = findViewFromId(R.id.et_cnfrm);
        rvSubmit = findViewFromId(R.id.rv_submit);

        setListeners();
        registerObservables();
    }

    private void setListeners() {
        rvSubmit.setOnRippleCompleteListener(v -> triggerSubmit());
    }

    private void registerObservables() {
        final Observable<CharSequence> oldOb = RxTextView.textChanges(etOld);
        final Observable<CharSequence> newOb = RxTextView.textChanges(etNew);
        final Observable<CharSequence> cnfrmOb = RxTextView.textChanges(etCnfrm);

        mDisposable = Observable.combineLatest(
                oldOb, newOb, cnfrmOb,
                (o1, o2, o3) ->
                        !TextUtils.isEmpty(o1) && !TextUtils.isEmpty(o2) &&
                                !TextUtils.isEmpty(o3))
                .subscribe(this::updateSignUpBtn);
    }

    private void updateSignUpBtn(boolean enable) {
        isInfoValid = enable;
        rvSubmit.setEnabled(enable);
        rvSubmit.setBackground(enable ? enabled : disabled);
    }

    private void triggerSubmit() {
        EToS();
        if (validateInfo()) {
            ((ActChangePassword) context).triggerChange(newPwd);
        }
    }

    private String oldPwd = null, newPwd = null, cnfrmPwd = null;

    private void EToS() {
        oldPwd = utils.EToS(etOld);
        newPwd = utils.EToS(etNew);
        cnfrmPwd = utils.EToS(etCnfrm);
    }

    private boolean validateInfo() {
        if (validateOldPwd()) {
            if (validateNewPwdForUnsimilarityWithOldPwd()) {
                return validateNewPwdAndCnfrmPwd();
            }
        }

        return false;
    }

    private boolean validateOldPwd() {
        if (prefs.getString(Constants.PASSWORD).equalsIgnoreCase(oldPwd)) {
            return true;
        } else {
            showSnackBar(R.string.old_pwd_incorrect);
            return false;
        }
    }

    /**
     * At this stage, it's validated that the old password has been input correctly. So
     * we can use either of the etOld or from prefs to get the old password
     */
    private boolean validateNewPwdForUnsimilarityWithOldPwd() {
        if (newPwd.equalsIgnoreCase(oldPwd)) {
            showSnackBar(R.string.new_pwd_must_b_diff_than_old);
            return false;
        } else {
            return true;
        }
    }

    private boolean validateNewPwdAndCnfrmPwd() {
        if (newPwd.equalsIgnoreCase(cnfrmPwd)) {
            return true;
        } else {
            showSnackBar(R.string.new_cnfrm_dont_match);
            return false;
        }
    }

    public void onStop() {
        disposeSubscriber();
    }

    private void disposeSubscriber() {
        if (mDisposable != null && !mDisposable.isDisposed()) mDisposable.dispose();
    }
}