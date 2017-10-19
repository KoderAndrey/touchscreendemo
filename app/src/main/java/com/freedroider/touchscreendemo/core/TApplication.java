package com.freedroider.touchscreendemo.core;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.freedroider.touchscreendemo.core.api.App;

public class TApplication extends Application implements App {
    public static App getApp(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }
}
