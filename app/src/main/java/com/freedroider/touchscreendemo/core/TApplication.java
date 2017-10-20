package com.freedroider.touchscreendemo.core;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.freedroider.touchscreendemo.core.api.App;
import com.google.gson.Gson;

public class TApplication extends Application implements App {
    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        gson = new Gson();
    }

    public static App getApp(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public Gson getGSON() {
        return gson;
    }
}
