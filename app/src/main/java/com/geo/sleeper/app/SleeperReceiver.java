package com.geo.sleeper.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

/**
 * Created by george
 * on 24/01/19.
 */
public class SleeperReceiver extends BroadcastReceiver {

    private boolean screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;

        }
        // Send Current screen ON/OFF value to service
        Intent i = new Intent(context, SleeperService.class);
        i.putExtra(SleeperConstants.SLEEPER, screenOff);
        context.startService(i);
    }
}
