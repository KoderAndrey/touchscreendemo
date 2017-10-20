package com.freedroider.touchscreendemo.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.freedroider.touchscreendemo.R;
import com.freedroider.touchscreendemo.service.MyService;
import com.freedroider.touchscreendemo.ui.activity.view.TVCursorView;
import com.freedroider.touchscreendemo.utils.Logger;

import butterknife.BindView;

public class HostActivity extends BaseActivity {

    @BindView(R.id.viewTvCursor)
    TVCursorView tvCursorView;

    @Override
    protected int obtainLayoutResId() {
        return R.layout.activity_host;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, HostActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("HostActivity onCreate");
        startService(new Intent(this, MyService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        IntentFilter filterShape = new IntentFilter(MyService.INTENT_FILTER_SHAPE);
        IntentFilter filterCoordinates = new IntentFilter(MyService.INTENT_FILTER_COORDINATES);
        manager.registerReceiver(mMessageReceiver, filterShape);
        manager.registerReceiver(mMessageReceiver, filterCoordinates);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mMessageReceiver);
        super.onPause();

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            Logger.d("BroadcastReceiver onReceive " + message);

            // TODO: 20.10.17 set cursor
            if (intent.getAction().equals(MyService.INTENT_FILTER_SHAPE)) {
//                Ratio ratio =  ParserUtils.sActionSize(message);
//                Logger.d("BroadcastReceiver onReceive Ratio" + "\n" + ratio.getWidth()
//                        + "\n" + ratio.getHeight());
            } else if (intent.getAction().equals(MyService.INTENT_FILTER_COORDINATES)) {
//                ControlAction controlAction = ParserUtils.sActionCoordinates(message);
//                Logger.d("BroadcastReceiver onReceive ControlAction" + "\n" + controlAction.getAngle()
//                        + "\n" + controlAction.getDistance());
            }
        }
    };
}
