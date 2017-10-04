package com.sweden4all.app;

import android.app.Application;

public class Sweden4AllApp extends Application {

    private static Sweden4AllApp mInstance;
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static Sweden4AllApp getInstance() {
        return mInstance;
    }

    public AppComponent getComponent() {
        return component;
    }
}