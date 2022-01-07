package com.example.oslobodiseresi;

import android.app.Application;

import com.example.oslobodiseresi.Retrofit.ApiManager;

public class MainApplication extends Application {
    public static ApiManager apiManager;

    @Override
    public void onCreate() {
        super.onCreate();
        apiManager = ApiManager.getInstance();
    }
}
