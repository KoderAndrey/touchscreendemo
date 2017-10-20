package com.freedroider.touchscreendemo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.freedroider.touchscreendemo.constant.Constant;
import com.freedroider.touchscreendemo.udp.UDPHelper;
import com.freedroider.touchscreendemo.utils.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyService extends Service implements UDPHelper.BroadcastListener {
    public static final String ACTION_SEND_DATA = "send data";
    private final String TAG = getClass().getSimpleName();
    private UDPHelper udp;
    BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.d(TAG, "onReceive: ");
            final String message = intent.getStringExtra(Constant.INTENT_KEY_JSON);
            Logger.d(TAG, "onReceive: message = " + message);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    udp.send(message);
                }
            });
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SEND_DATA);
        this.registerReceiver(mScreenStateReceiver, filter);
        Logger.d(TAG, "onCreate");
        udp = new UDPHelper(this);
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        unregisterReceiver(mScreenStateReceiver);
        super.onDestroy();
    }

    @Override
    public void onReceive(String msg, String ip) {
        Logger.d(TAG, "onReceive");
        udp.send(msg);
    }

}
