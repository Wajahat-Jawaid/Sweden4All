package com.sweden4all.activities;

import com.sweden4all.views.ApplicationStatusView;
import com.sweden4all.views.BaseView;

public class ActApplicationStatus extends BaseActivity {

    @Override
    public BaseView getView() {
        return new ApplicationStatusView(this);
    }
}