package com.sweden4all.views;

import android.content.Context;

import com.sweden4all.R;
import com.sweden4all.activities.accounts.ActLogIn;
import com.sweden4all.activities.accounts.ActSignUp;
import com.sweden4all.utils.RippleView;

public class HomeView extends BaseView implements RippleView.OnRippleCompleteListener {

    public HomeView(Context context) {
        super(context);
    }

    @Override
    public int layout() {
        return R.layout.act_home;
    }

    @Override
    public void onCreate() {
        setListeners();
    }

    private void setListeners() {
        ((RippleView) findViewFromId(R.id.rv_login)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_sign_up)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_services)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_contact)).setOnRippleCompleteListener(this);
        ((RippleView) findViewFromId(R.id.rv_status)).setOnRippleCompleteListener(this);
    }

    @Override
    public void onComplete(RippleView v) {
        switch (v.getId()) {
            case R.id.rv_login:
                switchActivity(ActLogIn.class);
                break;
            case R.id.rv_sign_up:
                switchActivity(ActSignUp.class);
                break;
        }
    }
}