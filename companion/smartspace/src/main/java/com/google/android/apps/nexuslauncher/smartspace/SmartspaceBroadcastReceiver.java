package com.google.android.apps.nexuslauncher.smartspace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class SmartspaceBroadcastReceiver extends BroadcastReceiver {

    public static final String AT_A_GLANCE_SOURCE = "com.google.android.apps.nexuslauncher.UPDATE_SMARTSPACE";
    public static final String AT_A_GLANCE_PROXY_ACTION = "com.hdeva.launcher.AT_A_GLANCE";
    public static final String AT_A_GLANCE_FORCE_ENABLE = "com.hdeva.launcher.AT_A_GLANCE_FORCE_ENABLE";
    public static final String ENABLE_UPDATE_ACTION = "com.google.android.apps.gsa.smartspace.ENABLE_UPDATE";
    public static final String ENABLE_UPDATE_PACKAGE = "com.google.android.googlequicksearchbox";
    public static final String ACTION_PING = "com.hdeva.launcher.AT_A_GLANCE_PING";
    public static final String ACTION_PING_RESPONSE = "com.hdeva.launcher.AT_A_GLANCE_PING_RESPONSE";
    public static final String AT_A_GLANCE_TARGET_PACKAGE = "com.hdeva.launcher";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case AT_A_GLANCE_SOURCE:
                    Intent proxy = new Intent(AT_A_GLANCE_PROXY_ACTION);
                    proxy.setPackage(AT_A_GLANCE_TARGET_PACKAGE);
                    proxy.putExtras(intent.getExtras() == null ? Bundle.EMPTY : intent.getExtras());
                    context.sendBroadcast(proxy);
                    break;
                case AT_A_GLANCE_FORCE_ENABLE:
                    Intent enable = new Intent(ENABLE_UPDATE_ACTION);
                    enable.setPackage(ENABLE_UPDATE_PACKAGE);
                    enable.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.sendBroadcast(enable);
                    break;
                case ACTION_PING:
                    String version;
                    try {
                        PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                        version = pInfo.versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        version = "NaN";
                    }

                    Intent ping = new Intent(ACTION_PING_RESPONSE);
                    ping.setPackage(AT_A_GLANCE_TARGET_PACKAGE);
                    ping.putExtra(ACTION_PING_RESPONSE, version);
                    context.sendBroadcast(ping);
                    break;
            }
        }
    }
}
