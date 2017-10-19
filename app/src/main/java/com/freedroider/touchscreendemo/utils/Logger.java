package com.freedroider.touchscreendemo.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.freedroider.touchscreendemo.BuildConfig;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class Logger {
    private static final String DEFAULT_TAG = "Default Log =>>";

    public static void d(@NonNull String tag, @NonNull String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void d(@NonNull String message) {
        d(DEFAULT_TAG, message);
    }
}
