package com.freedroider.touchscreendemo.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class SystemUtils {

    public static float convertDpToPixel(@NonNull Context context, float dp) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(@NonNull Context context, float px) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}