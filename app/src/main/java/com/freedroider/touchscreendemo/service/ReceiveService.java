package com.freedroider.touchscreendemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.freedroider.touchscreendemo.utils.Logger;

public class ReceiveService extends Service {


    public ReceiveService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("ReceiveService onCreate");
    }

    @Override
    public void onDestroy() {
        Logger.d("ReceiveService onDestroy");
        super.onDestroy();
    }

}
