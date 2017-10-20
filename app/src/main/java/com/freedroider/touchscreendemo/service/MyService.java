package com.freedroider.touchscreendemo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.freedroider.touchscreendemo.constant.Constant;
import com.freedroider.touchscreendemo.looper.UPDLooperThread;
import com.freedroider.touchscreendemo.udp.UDPHelper;
import com.freedroider.touchscreendemo.utils.Logger;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyService extends Service implements UDPHelper.BroadcastListener {
    public static final String INTENT_FILTER_SHAPE = "INTENT_FILTER_SHAPE";
    public static final String INTENT_FILTER_COORDINATES = "INTENT_FILTER_COORDINATES";
    public static final String ACTION_SEND_DATA = "send data";
    private Pinger pinger;
    private final String TAG = getClass().getSimpleName();
    private UDPHelper udp;
    private UPDLooperThread thread;
    private List<String> ips = Collections.synchronizedList(new LinkedList<String>());

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
        pinger = new Pinger();
        pinger.run();
        this.registerReceiver(mScreenStateReceiver, filter);
        Logger.d(TAG, "onCreate");
        udp = new UDPHelper(this);
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        unregisterReceiver(mScreenStateReceiver);
        thread.setLoop(false);
        pinger.end();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onReceive(String msg, String ip) {
        Logger.d(TAG, "onReceive");
        Logger.d(" Pinger receive message " + msg + " from " + ip);
        //// TODO: 20.10.2017 block ip; use only one ip
        if (!ips.contains(ip)) {
            ips.add(ip);
            Logger.d("onReceive ips" + ips.toString());
            sendMessage(msg, INTENT_FILTER_SHAPE);
        }
        sendMessage(msg, INTENT_FILTER_COORDINATES);
    }

    private class Pinger extends Thread {
        private boolean running;

        @Override
        public void run() {
            Logger.d("ReceiveService Pinger run");
            Logger.d("ReceiveService Pinger try");
            udp = new UDPHelper(getApplicationContext());
            udp.start();
            running = true;
        }

        public void end() {
            Logger.d("ReceiveService Pinger end");
            running = false;
            udp.end();
        }
    }

    private void sendMessage(String msg, String filter) {
        Intent intent = new Intent(filter);
        intent.putExtra("message", msg);
        Logger.d("sendMessage");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
