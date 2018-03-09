package com.hdeva.launcher;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherAppState;
import com.android.launcher3.LauncherModel;
import com.android.launcher3.R;
import com.android.launcher3.compat.LauncherAppsCompat;
import com.android.launcher3.dynamicui.WallpaperColorInfo;
import com.android.launcher3.util.LooperExecutor;

public class LeanUtils {

    private static final long WAIT_BEFORE_RESTART = 250;
    private static final LeanDoubleTapToLockRegistry REGISTRY = new LeanDoubleTapToLockRegistry();
    private static final String GOOGLE_QSB = "com.google.android.googlequicksearchbox";

    public static void reload(Context context) {
        LauncherAppState.getInstance(context).getModel().forceReload();
    }

    public static void reloadTheme(Context context) {
        WallpaperColorInfo.getInstance(context).notifyChange(true);
    }

    public static void restart(final Context context) {
        ProgressDialog.show(context, null, context.getString(R.string.state_loading), true, false);
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
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
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

    public static void handleWorkspaceTouchEvent(Context context, MotionEvent ev) {
        REGISTRY.add(ev);
        if (LeanSettings.isDoubleTapToLockEnabled(context) && REGISTRY.shouldLock()) {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            if (devicePolicyManager != null) {
                if (devicePolicyManager.isAdminActive(adminComponent(context))) {
                    devicePolicyManager.lockNow();
                } else {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent(context));
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, context.getString(R.string.double_tap_to_lock_hint));
                    context.startActivity(intent);
                }
            }
        }
    }

    public static void startQuickSearch(final Launcher launcher) {
        final String provider = LeanSettings.getSearchProvider(launcher);
        if (provider.contains("google")) {
            Point point = new Point(0, 0);
            Intent intent = new Intent("com.google.nexuslauncher.FAST_TEXT_SEARCH")
                    .setPackage("com.google.android.googlequicksearchbox")
                    .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("source_round_left", true)
                    .putExtra("source_round_right", true)
                    .putExtra("source_logo_offset", point)
                    .putExtra("source_mic_offset", point)
                    .putExtra("use_fade_animation", true);
            intent.setSourceBounds(new Rect());
            launcher.sendOrderedBroadcast(intent, null,
                    new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            Log.e("HotseatQsbSearch", getResultCode() + " " + getResultData());
                            if (getResultCode() == 0) {
                                try {
                                    launcher.startActivity(new Intent("com.google.android.googlequicksearchbox.TEXT_ASSIST")
                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                            .setPackage(GOOGLE_QSB));
                                } catch (ActivityNotFoundException e) {
                                    try {
                                        launcher.getPackageManager().getPackageInfo(GOOGLE_QSB, 0);
                                        LauncherAppsCompat.getInstance(launcher)
                                                .showAppDetailsForProfile(new ComponentName(GOOGLE_QSB, ".SearchActivity"), Process.myUserHandle());
                                    } catch (PackageManager.NameNotFoundException ignored) {
                                        launcher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(provider)));
                                    }
                                }
                            }
                        }
                    }, null, 0, null, null);
        } else {
            launcher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(provider)));
        }
    }

    public static void startVoiceSearch(Launcher launcher) {
        try {
            launcher.startActivity(new Intent("android.intent.action.VOICE_ASSIST")
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .setPackage(GOOGLE_QSB));
        } catch (ActivityNotFoundException e) {
            try {
                launcher.getPackageManager().getPackageInfo(GOOGLE_QSB, 0);
                LauncherAppsCompat.getInstance(launcher).showAppDetailsForProfile(new ComponentName(GOOGLE_QSB, ".SearchActivity"), Process.myUserHandle());
            } catch (PackageManager.NameNotFoundException ignored) {
                launcher.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com")));
            }
        }
    }

    public static void openAppDrawer(Launcher launcher) {
        launcher.showAppsView(true, false);
    }

    public static void openAppSearch(Launcher launcher) {
        launcher.showAppsViewWithSearch(true, false);
    }

    public static void openOverview(Launcher launcher) {
        launcher.showOverviewMode(true);
    }

    private static ComponentName adminComponent(Context context) {
        return new ComponentName(context, LeanDeviceAdmin.class);
    }

    private LeanUtils() {

    }
}
