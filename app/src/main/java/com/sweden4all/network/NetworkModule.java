package com.sweden4all.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sweden4all.app.AppScope;
import com.sweden4all.app.ContextModule;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ContextModule.class)
public class NetworkModule {

    @AppScope
    @Provides
    public ApiInterface apiInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    // TODO provide base URL
    @AppScope
    @Provides
    public Retrofit retrofit(OkHttpClient httpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .baseUrl("http://sweden4all.org/deviphoneapp/")
                .build();
    }

    @AppScope
    @Provides
    public Gson gson() {
        return new GsonBuilder().setLenient().create();
    }

    @AppScope
    @Provides
    public OkHttpClient okHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    @AppScope
    @Provides
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 2 * 1024 * 1000); // 2 MB cache
    }

    @AppScope
    @Provides
    public File cacheFile(Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }
}