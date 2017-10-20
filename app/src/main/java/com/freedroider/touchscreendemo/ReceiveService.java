package com.freedroider.touchscreendemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.freedroider.touchscreendemo.utils.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ReceiveService extends Service {

    public static final String INTENT_SHAPE = "INTENT_SHAPE";
    public static final String INTENT_COORDINATES = "INTENT_COORDINATES";
    UDPHelperByMaster udp;
    Pinger pinger;
    List<String> ips = Collections.synchronizedList(new LinkedList<String>());

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
         pinger = new Pinger();
        pinger.run();
    }

    @Override
    public void onDestroy() {
        Logger.d("ReceiveService onDestroy");
        pinger.end();
        super.onDestroy();
    }

    private class Pinger extends Thread {
        private boolean running;

        @Override
        public void run() {
            Logger.d("ReceiveService Pinger run");
            try {
                Logger.d("ReceiveService Pinger try");
                udp = new UDPHelperByMaster(getApplicationContext(), new UDPHelperByMaster.BroadcastListener() {
                    @Override
                    public void onReceive(String msg, String ip) {
                        Logger.d(" Pinger receive message " + msg + " from " + ip);
                        //// TODO: 20.10.2017 block ip; use only one ip
                        if (!ips.contains(ip)) {
                            ips.add(ip);
                            Logger.d("onReceive ips" + ips.toString());
                            sendMessage(msg, INTENT_SHAPE);
                        }
                        sendMessage(msg, INTENT_COORDINATES);

                    }
                });
                udp.start();
            } catch (IOException e) {
                Logger.d("ReceiveService IOException");
                e.printStackTrace();
            }
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
