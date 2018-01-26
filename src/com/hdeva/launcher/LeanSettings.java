package com.hdeva.launcher;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.launcher3.Utilities;

public class LeanSettings {

    public static final String QSB_KEY = "pref_enable_at_a_glance";
    public static final String LOCK_DESKTOP_KEY = "pref_lock_desktop";

    private static final boolean QSB_DEFAULT = true;
    private static final boolean LOCK_DESKTOP_DEFAULT = false;

    public static boolean isQsbEnabled(Context context) {
        return prefs(context).getBoolean(QSB_KEY, QSB_DEFAULT);
    }

    public static boolean isDesktopLocked(Context context) {
        return prefs(context).getBoolean(LOCK_DESKTOP_KEY, LOCK_DESKTOP_DEFAULT);
    }

    private static SharedPreferences prefs(Context context) {
        return Utilities.getPrefs(context);
    }

    private LeanSettings() {

    }
}
