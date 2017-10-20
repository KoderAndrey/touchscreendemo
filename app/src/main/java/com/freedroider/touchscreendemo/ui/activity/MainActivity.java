package com.freedroider.touchscreendemo.ui.activity;

import android.view.View;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.freedroider.touchscreendemo.R;
import com.freedroider.touchscreendemo.ui.activity.view.RemoteControlView;
import com.freedroider.touchscreendemo.ui.activity.view.TVCursorView;

import butterknife.OnClick;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.viewTvCursor)
    TVCursorView viewTvCursor;
    @BindView(R.id.viewRemoteControl)
    RemoteControlView viewRemoteControl;


    @OnClick({R.id.client_button, R.id.host_button})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.client_button:
                ClientActivity.start(this);
                break;
            case R.id.host_button:
                HostActivity.start(this);
                break;
        }
    }

    @Override
    protected int obtainLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewRemoteControl.setControlListener(new RemoteControlView.OnRemoteControlListener() {
            @Override
            public void onSizeReady(int width, int height) {
                viewTvCursor.calculateRatio(width, height);
            }

            @Override
            public void onMove(float angle, double distance) {
                viewTvCursor.move(angle, distance);
            }
        });

    }
}
