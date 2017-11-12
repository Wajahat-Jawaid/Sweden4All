package com.sweden4all.views.settings;

import android.content.Context;

import com.sweden4all.R;
import com.sweden4all.activities.accounts.ActChangePassword;
import com.sweden4all.activities.accounts.ActEditProfile;
import com.sweden4all.activities.accounts.ActLogin;
import com.sweden4all.utils.RippleView;
import com.sweden4all.views.BaseView;

import static com.sweden4all.constants.Constants.FLAG_ACTIVITY_LAUNCH_BY_FLUSHING_STACK;

public class SettingsView extends BaseView implements RippleView.OnRippleCompleteListener {

    public SettingsView(Context context) {
        super(context);
    }

    @Override
    public int layout() {
        return R.layout.act_settings;
    }

    @Override
    public void onCreate() {
        setToolbar(R.string.settings);
        setListeners();
    }

    private void setListeners() {
        ((RippleView) findViewFromId(R.id.rv_profile_settings)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_chng_pwd)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_logout)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_ask_ques)).setOnRippleCompleteListener(this);
    }

    private void handleLogout() {
        switchActivity(ActLogin.class, FLAG_ACTIVITY_LAUNCH_BY_FLUSHING_STACK);
    }

    @Override
    public void onComplete(RippleView v) {
        switch (v.getId()) {
            case R.id.rv_profile_settings:
                switchActivity(ActEditProfile.class);
                break;
            case R.id.rv_chng_pwd:
                switchActivity(ActChangePassword.class);
                break;
            case R.id.rv_logout:
                handleLogout();
                break;
            case R.id.rv_ask_ques:
                break;
        }
    }
}