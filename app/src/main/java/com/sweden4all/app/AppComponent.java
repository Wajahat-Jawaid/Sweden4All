package com.sweden4all.app;

import com.sweden4all.activities.BaseActivity;
import com.sweden4all.database.SharedPrefsModule;
import com.sweden4all.network.ConnectionModule;
import com.sweden4all.network.NetworkModule;
import com.sweden4all.utils.UtilsModule;
import com.sweden4all.views.BaseView;

import dagger.Component;

@AppScope
@Component(modules = {ContextModule.class, NetworkModule.class, SharedPrefsModule.class,
        ConnectionModule.class, UtilsModule.class})
public interface AppComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseView baseView);
}