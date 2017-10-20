package com.freedroider.touchscreendemo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.IntDef;

import com.freedroider.touchscreendemo.constant.Constant;
import com.freedroider.touchscreendemo.looper.UPDLooperThread;
import com.freedroider.touchscreendemo.udp.UDPHelper;
import com.freedroider.touchscreendemo.utils.Logger;

public class MyService extends Service implements UDPHelper.BroadcastListener {
    public static final String ACTION_SEND_DATA = "send data";
    private final String TAG = getClass().getSimpleName();
    private UDPHelper udp;
    private UPDLooperThread thread;
    BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.d(TAG, "onReceive: ");
            final String message = intent.getStringExtra(Constant.INTENT_KEY_JSON);
            Logger.d(TAG, "onReceive: message = " + message);

            thread.send(new Runnable() {
                @Override
                public void run() {
                    Logger.d(Thread.currentThread().getName());
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
        thread = new UPDLooperThread();
        thread.start();

        this.registerReceiver(mScreenStateReceiver, filter);
        Logger.d(TAG, "onCreate");
        udp = new UDPHelper(this);
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        unregisterReceiver(mScreenStateReceiver);
        thread.setLoop(false);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onReceive(String msg, String ip) {
        Logger.d(TAG, "onReceive");
        udp.send(msg);
    }
}
