package com.freedroider.touchscreendemo.looper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Student Vsevolod on 20.10.17.
 * usevalad.uladzimiravich@gmail.com
 */

public class UPDLooperThread extends Thread {
    private Handler handler;

    @Override
    public void run() {
        Looper.prepare();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // process incoming messages here
            }
        };
        Looper.loop();
    }

    public void send(Runnable runnable) {
        handler.post(runnable);
    }

}
