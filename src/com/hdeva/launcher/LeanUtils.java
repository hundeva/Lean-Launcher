package com.hdeva.launcher;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.util.Log;

import com.android.launcher3.LauncherAppState;
import com.android.launcher3.LauncherModel;
import com.android.launcher3.R;
import com.android.launcher3.dynamicui.WallpaperColorInfo;
import com.android.launcher3.util.LooperExecutor;

public class LeanUtils {

    private static final long WAIT_BEFORE_RESTART = 250;

    public static void reload(Context context) {
        LauncherAppState.getInstance(context).getModel().forceReload();
    }

    public static void reloadTheme(Context context) {
        WallpaperColorInfo.getInstance(context).notifyChange(true);
    }

    public static void restart(final Context context) {
        new LooperExecutor(LauncherModel.getWorkerLooper()).execute(new Runnable() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void run() {
                try {
                    Thread.sleep(WAIT_BEFORE_RESTART);
                } catch (Exception e) {
                    Log.e("SettingsActivity", "Error waiting", e);
                }

                Intent intent = new Intent(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_HOME)
                        .setPackage(context.getPackageName())
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_ONE_SHOT);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 50, pendingIntent);

                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    public static int getAllAppsQsbTopOffset(Context context) {
        int statusBar = getStatusBarHeight(context);
        int topTranslationY = context.getResources().getDimensionPixelSize(R.dimen.all_apps_qsb_top_translation_y);
        int topOffset = context.getResources().getDimensionPixelOffset(R.dimen.all_apps_qsb_top_offset);
        int height;
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            height = statusBar - topTranslationY + topOffset;
        } else {
            height = topOffset;
        }
        return height;
    }

    public static int getStatusBarHeight(Context context) {
        int height;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        } else {
            height = context.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        }
        return height;
    }

    private LeanUtils() {

    }
}
