package com.freedroider.touchscreendemo.ui.activity;

import android.view.View;

import com.freedroider.touchscreendemo.R;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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
}
