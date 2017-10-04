package com.sweden4all.utils;

import com.sweden4all.app.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @AppScope
    public Utils getUtils() {
        return new Utils();
    }
}