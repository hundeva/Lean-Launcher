package com.hdeva.launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.launcher3.LauncherFiles;
import com.android.launcher3.Utilities;

public class LeanSettings {

    public static final String QSB_KEY = "pref_enable_at_a_glance";
    public static final String LOCK_DESKTOP_KEY = "pref_lock_desktop";
    public static final String THEME_KEY = "pref_theme";
    public static final String BOTTOM_SEARCH_BAR_KEY = "pref_bottom_search_bar";
    public static final String TOP_SEARCH_BAR_KEY = "pref_top_search_bar";
    public static final String PHYSICAL_ANIMATION_KEY = "pref_physical_animation";
    public static final String TRANSPARENT_STATUS_BAR = "pref_transparent_status_bar";
    public static final String EXTRA_BOTTOM_PADDING = "pref_extra_bottom_padding";
    public static final String GRID_COLUMNS = "pref_grid_columns";
    public static final String GRID_ROWS = "pref_grid_rows";
    public static final String HOTSEAT_ICONS = "pref_hotseat_icons";
    public static final String FORCE_COLORED_G_ICON = "pref_colored_g_icon";
    public static final String DOUBLE_TAP_TO_LOCK = "pref_double_tap_to_lock";

    private static final boolean QSB_DEFAULT = true;
    private static final boolean LOCK_DESKTOP_DEFAULT = false;
    private static final String THEME_DEFAULT = "wallpaper";
    private static final boolean BOTTOM_SEARCH_BAR_DEFAULT = true;
    private static final boolean TOP_SEARCH_BAR_DEFAULT = true;
    private static final boolean PHYSICAL_ANIMATION_DEFAULT = true;
    private static final boolean TRANSPARENT_STATUS_BAR_DEFAULT = false;
    private static final boolean EXTRA_BOTTOM_PADDING_DEFAULT = false;
    private static final String GRID_COLUMNS_DEFAULT = "default";
    private static final String GRID_ROWS_DEFAULT = "default";
    private static final String HOTSEAT_ICONS_DEFAULTS = "default";
    private static final boolean FORCE_COLORED_G_ICON_DEFAULT = false;
    private static final boolean DOUBLE_TAP_TO_LOCK_DEFAULT = false;

    private static final String THEME_WALLPAPER = "wallpaper";
    private static final String THEME_LIGHT = "light";
    private static final String THEME_DARK = "dark";

    public static boolean isQsbEnabled(Context context) {
        return prefs(context).getBoolean(QSB_KEY, QSB_DEFAULT);
    }

    public static boolean isDesktopLocked(Context context) {
        return prefs(context).getBoolean(LOCK_DESKTOP_KEY, LOCK_DESKTOP_DEFAULT);
    }

    public static boolean isDark(Context context, boolean originalIsDark) {
        boolean isDark;
        String theme = getTheme(context);
        if (theme.equals(THEME_WALLPAPER)) {
            isDark = originalIsDark;
        } else if (theme.equals(THEME_LIGHT)) {
            isDark = false;
        } else if (theme.equals(THEME_DARK)) {
            isDark = true;
        } else {
            isDark = originalIsDark;
        }
        return isDark;
    }

    private static String getTheme(Context context) {
        return prefs(context).getString(THEME_KEY, THEME_DEFAULT);
    }

    public static boolean isBottomSearchBarVisible(Context context) {
        return prefs(context).getBoolean(BOTTOM_SEARCH_BAR_KEY, BOTTOM_SEARCH_BAR_DEFAULT);
    }

    public static boolean isTopSearchBarVisible(Context context) {
        return prefs(context).getBoolean(TOP_SEARCH_BAR_KEY, TOP_SEARCH_BAR_DEFAULT);
    }

    public static boolean isPhysicalAnimationEnabled(Context context) {
        return prefs(context).getBoolean(PHYSICAL_ANIMATION_KEY, PHYSICAL_ANIMATION_DEFAULT);
    }

    public static boolean isComponentHidden(Context context, ComponentName component) {
        return hiddenAppsPrefs(context).getBoolean(component.getClassName(), false);
    }

    public static void setComponentHidden(Context context, ComponentName component, boolean hidden) {
        hiddenAppsPrefs(context).edit().putBoolean(component.getClassName(), hidden).apply();
    }

    public static boolean isStatusBarTransparent(Context context) {
        return prefs(context).getBoolean(TRANSPARENT_STATUS_BAR, TRANSPARENT_STATUS_BAR_DEFAULT);
    }

    public static boolean shouldExtraBottomPaddingForBottomSearchBar(Context context) {
        return isBottomSearchBarVisible(context) && prefs(context).getBoolean(EXTRA_BOTTOM_PADDING, EXTRA_BOTTOM_PADDING_DEFAULT);
    }

    public static boolean isColoredGIconForced(Context context) {
        return prefs(context).getBoolean(FORCE_COLORED_G_ICON, FORCE_COLORED_G_ICON_DEFAULT);
    }

    public static boolean isDoubleTapToLockEnabled(Context context) {
        return prefs(context).getBoolean(DOUBLE_TAP_TO_LOCK, DOUBLE_TAP_TO_LOCK_DEFAULT);
    }

    public static int getGridColumns(Context context, int fallback) {
        return getIconCount(context, GRID_COLUMNS, GRID_COLUMNS_DEFAULT, fallback);
    }

    public static int getGridRows(Context context, int fallback) {
        return getIconCount(context, GRID_ROWS, GRID_ROWS_DEFAULT, fallback);
    }

    public static int getHotseatIcons(Context context, int fallback) {
        return getIconCount(context, HOTSEAT_ICONS, HOTSEAT_ICONS_DEFAULTS, fallback);
    }

    private static int getIconCount(Context context, String preferenceName, String preferenceFallback, int deviceProfileFallback) {
        String saved = prefs(context).getString(preferenceName, preferenceFallback);
        int num;
        switch (saved) {
            case "default":
                num = deviceProfileFallback;
                break;
            case "three":
                num = 3;
                break;
            case "four":
                num = 4;
                break;
            case "five":
                num = 5;
                break;
            case "six":
                num = 6;
                break;
            case "seven":
                num = 7;
                break;
            default:
                num = deviceProfileFallback;
                break;
        }
        return num;
    }

    private static SharedPreferences prefs(Context context) {
        return Utilities.getPrefs(context);
    }

    private static SharedPreferences hiddenAppsPrefs(Context context) {
        return context.getSharedPreferences(LauncherFiles.HIDDEN_APPS_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private LeanSettings() {

    }
}
