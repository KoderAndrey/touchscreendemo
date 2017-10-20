package com.freedroider.touchscreendemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.freedroider.touchscreendemo.R;
import com.freedroider.touchscreendemo.constant.Constant;
import com.freedroider.touchscreendemo.service.MyService;
import com.freedroider.touchscreendemo.udp.UDPHelper;
import com.freedroider.touchscreendemo.ui.activity.view.TVCursorView;
import com.freedroider.touchscreendemo.utils.Logger;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

public class ClientActivity extends BaseActivity implements UDPHelper.BroadcastListener {
    @BindView(R.id.viewRemoteControl)
    TVCursorView tvCursorView;
    Intent intent;

//    @OnClick(R.id.send_button)
//    void onClick() {
//        Logger.d(TAG, "onClick: ");
//        String message = editText.getText().toString();
//        String json = getApp().getGSON().toJson(message);
//        Intent broadcast = new Intent(MyService.ACTION_SEND_DATA);
//        broadcast.putExtra(Constant.INTENT_KEY_JSON, json);
//        sendBroadcast(broadcast);
//        Logger.d(TAG, "onClick: send boradcast. message = " + message);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, MyService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
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
    public void onReceive(String msg, String ip) {
        Logger.d(msg);
    }
}
