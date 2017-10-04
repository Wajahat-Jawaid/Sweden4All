package com.sweden4all.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sweden4all.app.AppScope;
import com.sweden4all.app.ContextModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class ConnectionModule {

    @AppScope
    @Provides
    public NetworkInfo networkInfo(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}