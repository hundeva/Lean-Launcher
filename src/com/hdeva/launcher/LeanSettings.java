package com.hdeva.launcher;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.launcher3.Utilities;

public class LeanSettings {

    public static final String QSB_KEY = "pref_enable_at_a_glance";

    private static final boolean QSB_DEFAULT = true;

    public static boolean isQsbEnabled(Context context) {
        return prefs(context).getBoolean(QSB_KEY, QSB_DEFAULT);
    }

    private static SharedPreferences prefs(Context context) {
        return Utilities.getPrefs(context);
    }

    private LeanSettings() {

    }
}
