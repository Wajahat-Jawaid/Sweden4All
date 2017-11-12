package com.sweden4all.activities.settings;

import com.sweden4all.activities.BaseActivity;
import com.sweden4all.views.BaseView;
import com.sweden4all.views.settings.SettingsView;

public class ActSettings extends BaseActivity {

    @Override
    public BaseView getView() {
        return new SettingsView(this);
    }
}