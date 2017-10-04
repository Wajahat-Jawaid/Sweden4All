package com.sweden4all.activities;

import com.sweden4all.views.BaseView;
import com.sweden4all.views.HomeView;

public class ActHome extends BaseActivity {

    @Override
    public BaseView getView() {
        return new HomeView(this);
    }
}