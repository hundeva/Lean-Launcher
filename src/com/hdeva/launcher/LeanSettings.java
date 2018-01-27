package com.hdeva.launcher;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.launcher3.Utilities;

public class LeanSettings {

    public static final String QSB_KEY = "pref_enable_at_a_glance";
    public static final String LOCK_DESKTOP_KEY = "pref_lock_desktop";
    public static final String THEME_KEY = "pref_theme";
    public static final String BOTTOM_SEARCH_BAR_KEY = "pref_bottom_search_bar";
    public static final String TOP_SEARCH_BAR_KEY = "pref_top_search_bar";

    private static final boolean QSB_DEFAULT = true;
    private static final boolean LOCK_DESKTOP_DEFAULT = false;
    private static final String THEME_DEFAULT = "wallpaper";
    private static final boolean BOTTOM_SEARCH_BAR_DEFAULT = true;
    private static final boolean TOP_SEARCH_BAR_DEFAULT = true;

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

    private static SharedPreferences prefs(Context context) {
        return Utilities.getPrefs(context);
    }

    private LeanSettings() {

    }
}
