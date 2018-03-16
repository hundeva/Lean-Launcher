package com.hdeva.launcher;

import android.content.Context;
import android.content.Intent;

public class LeanGlanceProxySender {

    private static final String AT_A_GLANCE_WEATHER_ACTION = "com.hdeva.launcher.AT_A_GLANCE_WEATHER_ACTION";
    private static final String AT_A_GLANCE_PREFERENCE_ACTION = "com.hdeva.launcher.AT_A_GLANCE_PREFERENCE_ACTION";

    private static final String INTENT_EXTRA = "com.hdeva.launcher.INTENT_EXTRA";
    private static final String TARGET_PACKAGE = "com.google.android.apps.nexuslauncher";

    public static void sendWeatherAction(Context context, Intent weatherIntent) {
        forward(context, AT_A_GLANCE_WEATHER_ACTION, weatherIntent);
    }

    public static void sendPreferenceAction(Context context, Intent preferenceIntent) {
        forward(context, AT_A_GLANCE_PREFERENCE_ACTION, preferenceIntent);
    }

    private static void forward(Context context, String action, Intent intentExtra) {
        Intent intent = new Intent(action);
        intent.setPackage(TARGET_PACKAGE);
        intent.putExtra(INTENT_EXTRA, intentExtra);
        context.sendBroadcast(intent);
    }

    private LeanGlanceProxySender() {

    }
}
