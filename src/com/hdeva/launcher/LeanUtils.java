package com.hdeva.launcher;

import android.content.Context;

import com.android.launcher3.LauncherAppState;

public class LeanUtils {

    public static void reload(Context context) {
        LauncherAppState.getInstance(context).getModel().forceReload();
    }

    private LeanUtils() {

    }
}
