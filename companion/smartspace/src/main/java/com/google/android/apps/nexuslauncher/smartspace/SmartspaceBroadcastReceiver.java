package com.google.android.apps.nexuslauncher.smartspace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SmartspaceBroadcastReceiver extends BroadcastReceiver {

    public static final String AT_A_GLANCE_SOURCE = "com.google.android.apps.nexuslauncher.UPDATE_SMARTSPACE";
    public static final String AT_A_GLANCE_PROXY_ACTION = "com.hdeva.launcher.AT_A_GLANCE";
    public static final String AT_A_GLANCE_FORCE_ENABLE = "com.hdeva.launcher.AT_A_GLANCE_FORCE_ENABLE";
    public static final String ENABLE_UPDATE_ACTION = "com.google.android.apps.gsa.smartspace.ENABLE_UPDATE";
    public static final String ENABLE_UPDATE_PACKAGE = "com.google.android.googlequicksearchbox";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case AT_A_GLANCE_SOURCE:
                    Intent proxy = new Intent(AT_A_GLANCE_PROXY_ACTION);
                    proxy.putExtras(intent.getExtras() == null ? Bundle.EMPTY : intent.getExtras());
                    context.sendBroadcast(proxy);
                    break;
                case AT_A_GLANCE_FORCE_ENABLE:
                    Intent enable = new Intent(ENABLE_UPDATE_ACTION);
                    enable.setPackage(ENABLE_UPDATE_PACKAGE);
                    enable.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.sendBroadcast(enable);
                    break;
            }
        }
    }
}
