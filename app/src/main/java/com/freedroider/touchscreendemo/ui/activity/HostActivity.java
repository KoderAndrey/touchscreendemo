package com.freedroider.touchscreendemo.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.freedroider.touchscreendemo.R;

public class HostActivity extends BaseActivity {

    @Override
    protected int obtainLayoutResId() {
        return R.layout.activity_host;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, HostActivity.class);
        context.startActivity(starter);
    }
}
