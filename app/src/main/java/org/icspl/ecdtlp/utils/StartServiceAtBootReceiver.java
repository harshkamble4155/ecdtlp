package org.icspl.ecdtlp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.icspl.ecdtlp.activity.TestingActivity;

public class StartServiceAtBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, TestingActivity.class);
            context.startService(serviceIntent);
        }
    }
}
