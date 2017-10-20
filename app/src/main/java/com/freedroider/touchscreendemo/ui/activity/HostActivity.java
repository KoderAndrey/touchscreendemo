package com.freedroider.touchscreendemo.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

import com.freedroider.touchscreendemo.R;
import com.freedroider.touchscreendemo.ReceiveService;
import com.freedroider.touchscreendemo.model.ControlAction;
import com.freedroider.touchscreendemo.model.Ratio;
import com.freedroider.touchscreendemo.utils.Logger;
import com.freedroider.touchscreendemo.utils.ParserUtils;

import butterknife.BindView;

public class HostActivity extends BaseActivity {

    @BindView(R.id.info)
    TextView info;

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
        startService(new Intent(this, ReceiveService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        IntentFilter filterShape = new IntentFilter(ReceiveService.INTENT_SHAPE);
        IntentFilter filterCoordinates = new IntentFilter(ReceiveService.INTENT_COORDINATES);
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

            if (intent.getAction().equals(ReceiveService.INTENT_SHAPE)) {
                Ratio ratio =  ParserUtils.sActionSize(message);
                Logger.d("BroadcastReceiver onReceive Ratio" + "\n" + ratio.getWidth()
                        + "\n" + ratio.getHeight());
            } else if (intent.getAction().equals(ReceiveService.INTENT_COORDINATES)) {
                ControlAction controlAction = ParserUtils.sActionCoordinates(message);
                Logger.d("BroadcastReceiver onReceive ControlAction" + "\n" + controlAction.getAngle()
                        + "\n" + controlAction.getDistance());
            }

            info.setText(message);
        }
    };
}
