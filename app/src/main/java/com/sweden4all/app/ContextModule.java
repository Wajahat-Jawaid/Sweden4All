package com.sweden4all.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    public Context context() {
        return context;
    }
}