package com.hdeva.launcher;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.apps.nexuslauncher.CustomAppFilter;
import com.google.android.apps.nexuslauncher.smartspace.NewCardInfo;
import com.google.android.apps.nexuslauncher.smartspace.SmartspaceController;
import com.google.android.apps.nexuslauncher.smartspace.nano.SmartspaceProto;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;

public class LeanProxyReceiver extends BroadcastReceiver {

    private static final String AT_A_GLANCE_ACTION = "com.hdeva.launcher.AT_A_GLANCE";
    private static final String HIDE_ACTION = "com.hdeva.launcher.HIDE_AT_A_GLANCE_COMPANION";
    private static final String SETTINGS_ACTION = "com.hdeva.launcher.OPEN_LEAN_SETTINGS";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case AT_A_GLANCE_ACTION:
                    handleSmartspace(context, intent);
                    break;
                case HIDE_ACTION:
                    handleHideAction(context);
                    break;
                case SETTINGS_ACTION:
                    handleSettingsAction(context);
                    break;
            }
        }
    }

    private void handleSmartspace(Context context, Intent intent) {
        byte[] byteArrayExtra = intent.getByteArrayExtra("com.google.android.apps.nexuslauncher.extra.SMARTSPACE_CARD");
        if (byteArrayExtra != null) {
            com.google.android.apps.nexuslauncher.smartspace.nano.SmartspaceProto.a a = new com.google.android.apps.nexuslauncher.smartspace.nano.SmartspaceProto.a();
            try {
                com.google.protobuf.nano.MessageNano.mergeFrom(a, byteArrayExtra);
                SmartspaceProto.b[] cw = a.cw;
                int length = cw.length;
                int i = 0;
                while (i < length) {
                    SmartspaceProto.b b2 = cw[i];
                    boolean b3 = b2.cz == 1;
                    if (b3 || b2.cz == 2) {
                        cg(b2, context, intent, b3);
                    } else {
                        Log.w("SmartspaceReceiver", "unrecognized card priority");
                    }
                    ++i;
                }
            } catch (InvalidProtocolBufferNanoException | PackageManager.NameNotFoundException ex) {
                Log.e("SmartspaceReceiver", "proto", ex);
            }
        } else {
            Log.e("SmartspaceReceiver", "receiving update with no proto: " + intent.getExtras());
        }
    }

    private void cg(SmartspaceProto.b b, Context context, Intent intent, boolean b2) throws PackageManager.NameNotFoundException {
        if (b.cy) {
            SmartspaceController.get(context).cV(null);
            return;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.google.android.googlequicksearchbox", 0);
            SmartspaceController.get(context).cV(new NewCardInfo(b, intent, b2, SystemClock.uptimeMillis(), packageInfo));
        } catch (PackageManager.NameNotFoundException ex) {
        }
    }

    private void handleHideAction(Context context) {
        ComponentName componentName = new ComponentName("com.google.android.apps.nexuslauncher", "com.google.android.apps.nexuslauncher.SmartSpaceCompanionActivity");
        CustomAppFilter.hideComponent(context, componentName, true);
        LeanUtils.reload(context);
    }

    private void handleSettingsAction(Context context) {
        Intent intent = new Intent(Intent.ACTION_APPLICATION_PREFERENCES).setPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
