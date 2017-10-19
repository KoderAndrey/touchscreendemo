package com.freedroider.touchscreendemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.freedroider.touchscreendemo.core.TApplication;
import com.freedroider.touchscreendemo.core.api.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;
    private App app;

    @LayoutRes
    protected abstract int obtainLayoutResId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(obtainLayoutResId());
        unbinder = ButterKnife.bind(this);
        app = TApplication.getApp(this);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        throw new UnsupportedOperationException("Use obtainLayoutResId() method.");
    }

    @NonNull
    protected App getApp() {
        return app;
    }
}
