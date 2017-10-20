package com.freedroider.touchscreendemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.freedroider.touchscreendemo.R;
import com.freedroider.touchscreendemo.constant.Constant;
import com.freedroider.touchscreendemo.model.ControlAction;
import com.freedroider.touchscreendemo.model.Ratio;
import com.freedroider.touchscreendemo.service.MyService;
import com.freedroider.touchscreendemo.ui.activity.view.RemoteControlView;
import com.freedroider.touchscreendemo.utils.Logger;

import butterknife.BindView;

public class ClientActivity extends BaseActivity implements RemoteControlView.OnRemoteControlListener {
    @BindView(R.id.viewRemoteControl)
    RemoteControlView remoteControlView;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
        intent = new Intent(this, MyService.class);
        startService(intent);
        remoteControlView.setControlListener(this);
    }

    @Override
    protected void onDestroy() {
        Logger.d("onDestroy");
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected int obtainLayoutResId() {
        return R.layout.activity_client;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ClientActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onSizeReady(int width, int height) {
        Logger.d("onSizeReady");
        String json = getApp().getGSON().toJson(new Ratio(width, height));
        Intent broadcast = new Intent(MyService.ACTION_SEND_DATA);
        broadcast.putExtra(Constant.INTENT_KEY_JSON, json);
        sendBroadcast(broadcast);
        Logger.d("onClick: send boradcast. message = " + json);
    }

    @Override
    public void onMove(float angle, double distance) {
        Logger.d("onMove");
        String json = getApp().getGSON().toJson(new ControlAction(angle, distance));
        Intent broadcast = new Intent(MyService.ACTION_SEND_DATA);
        broadcast.putExtra(Constant.INTENT_KEY_JSON, json);
        sendBroadcast(broadcast);
        Logger.d("onClick: send boradcast. message = " + json);
    }
}