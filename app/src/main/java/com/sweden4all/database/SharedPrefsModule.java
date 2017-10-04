package com.sweden4all.database;

import android.content.Context;

import com.sweden4all.app.AppScope;
import com.sweden4all.app.ContextModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class SharedPrefsModule {

    @AppScope
    @Provides
    public SharedPrefs sharedPrefs(Context context) {
        return new SharedPrefs(context);
    }
}