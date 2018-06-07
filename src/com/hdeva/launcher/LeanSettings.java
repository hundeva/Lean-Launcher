package com.hdeva.launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Pair;

import com.android.launcher3.R;
import com.android.launcher3.Utilities;

public class LeanSettings {

    public static final String SETTINGS_DIRTY = "pref_lean_settings_dirty";
    public static final String LOCK_DESKTOP_KEY = "pref_lock_desktop";
    public static final String THEME_KEY = "pref_theme";
    public static final String BOTTOM_SEARCH_BAR_KEY = "pref_bottom_search_bar";
    public static final String TOP_SEARCH_BAR_KEY = "pref_top_search_bar";
    public static final String PHYSICAL_ANIMATION_KEY = "pref_physical_animation";
    public static final String TRANSPARENT_NAVIGATION_BAR = "pref_transparent_navigation_bar";
    public static final String EXTRA_BOTTOM_PADDING = "pref_extra_bottom_padding";
    public static final String GRID_COLUMNS = "pref_grid_columns";
    public static final String GRID_ROWS = "pref_grid_rows";
    public static final String HOTSEAT_ICONS = "pref_hotseat_icons";
    public static final String FORCE_COLORED_G_ICON = "pref_colored_g_icon";
    public static final String DOUBLE_TAP_TO_LOCK = "pref_double_tap_to_lock";
    public static final String ICON_SIZE = "pref_icon_size";
    public static final String ICON_TEXT_SIZE = "pref_icon_text_size";
    public static final String RESET_APP_VISIBILITY_ON_DEFAULT_ICON_PACK = "pref_reset_app_visibility_on_default_icon_pack";
    public static final String SEARCH_PROVIDER = "pref_search_provider";
    public static final String HOTSEAT_BACKGROUND = "pref_hotseat_background";
    public static final String DARK_BOTTOM_SEARCH_BAR = "pref_dark_bottom_search_bar";
    public static final String DARK_TOP_SEARCH_BAR = "pref_dark_top_search_bar";
    public static final String LABEL_HIDDEN_ON_DESKTOP = "pref_label_hidden_on_desktop";
    public static final String LABEL_HIDDEN_ON_ALL_APPS = "pref_label_hidden_on_all_apps";
    public static final String RESET_APP_NAMES = "pref_reset_app_names";
    public static final String RESET_APP_VISIBILITY = "pref_reset_app_visibility";
    public static final String RESET_APP_ICONS = "pref_reset_app_icons";
    public static final String QSB_VOICE_ICON = "pref_qsb_voice_icon";
    public static final String HOME_ACTION = "pref_home_action";
    public static final String DOUBLE_TAP_TO_LOCK_IS_SECURE = "pref_double_tap_to_lock_is_secure";
    public static final String ONE_FINGER_DOWN = "pref_one_finger_down";
    public static final String TWO_FINGER_DOWN = "pref_two_finger_down";
    public static final String BLACK_COLORS = "pref_black_colors";
    public static final String SHOW_CARET = "pref_show_caret";
    public static final String GENERATE_ADAPTIVE_ICONS = "pref_generate_adaptive_icons";
    public static final String GENERATED_ADAPTIVE_BACKGROUND = "pref_generated_adaptive_background";
    public static final String ALLOW_TWO_LINE_LABELS = "pref_allow_to_line_labels";
    public static final String SHORTCUT_UNLOCKED_WIDGETS = "pref_shortcut_unlocked_widgets";
    public static final String SHORTCUT_UNLOCKED_UNINSTALL = "pref_shortcut_unlocked_uninstall";
    public static final String SHORTCUT_UNLOCKED_EDIT = "pref_shortcut_unlocked_edit";
    public static final String SHORTCUT_LOCKED_UNINSTALL = "pref_shortcut_locked_uninstall";
    public static final String SHORTCUT_LOCKED_EDIT = "pref_shortcut_locked_edit";
    public static final String CARET_LONG_PRESS = "pref_caret_long_press";
    public static final String DATE_FORMAT = "pref_date_format";
    public static final String PAGE_INDICATOR = "pref_page_indicator";

    private static final boolean SETTINGS_DIRTY_DEFAULT = false;
    private static final boolean LOCK_DESKTOP_DEFAULT = false;
    private static final String THEME_DEFAULT = "wallpaper";
    private static final boolean BOTTOM_SEARCH_BAR_DEFAULT = true;
    private static final boolean TOP_SEARCH_BAR_DEFAULT = true;
    private static final boolean PHYSICAL_ANIMATION_DEFAULT = true;
    private static final boolean TRANSPARENT_NAVIGATION_BAR_DEFAULT = false;
    private static final boolean EXTRA_BOTTOM_PADDING_DEFAULT = false;
    private static final String GRID_COLUMNS_DEFAULT = "default";
    private static final String GRID_ROWS_DEFAULT = "default";
    private static final String HOTSEAT_ICONS_DEFAULTS = "default";
    private static final boolean FORCE_COLORED_G_ICON_DEFAULT = false;
    private static final boolean DOUBLE_TAP_TO_LOCK_DEFAULT = false;
    private static final String ICON_SIZE_DEFAULT = "average";
    private static final String ICON_TEXT_SIZE_DEFAULT = "average";
    private static final boolean RESET_APP_VISIBILITY_ON_DEFAULT_ICON_PACK_DEFAULT = true;
    private static final String SEARCH_PROVIDER_DEFAULT = "https://www.google.com";
    private static final String HOTSEAT_BACKGROUND_DEFAULT = "100";
    private static final boolean DARK_BOTTOM_SEARCH_BAR_DEFAULT = false;
    private static final boolean DARK_TOP_SEARCH_BAR_DEFAULT = false;
    private static final boolean LABEL_HIDDEN_ON_DESKTOP_DEFAULT = false;
    private static final boolean LABEL_HIDDEN_ON_ALL_APPS_DEFAULT = false;
    private static final boolean QSB_VOICE_ICON_DEFAULT = true;
    private static final boolean DOUBLE_TAP_TO_LOCK_IS_SECURE_DEFAULT = false;
    private static final boolean ONE_FINGER_DOWN_DEFAULT = true;
    private static final boolean TWO_FINGER_DOWN_DEFAULT = true;
    private static final boolean BLACK_COLORS_DEFAULT = false;
    private static final boolean SHOW_CARET_DEFAULT = true;
    private static final boolean GENERATE_ADAPTIVE_ICONS_DEFAULT = false;
    private static final boolean GENERATED_ADAPTIVE_BACKGROUND_DEFAULT = false;
    private static final boolean ALLOW_TWO_LINE_LABELS_DEFAULT = false;
    private static final boolean SHORTCUT_UNLOCKED_WIDGETS_DEFAULT = true;
    private static final boolean SHORTCUT_UNLOCKED_UNINSTALL_DEFAULT = true;
    private static final boolean SHORTCUT_UNLOCKED_EDIT_DEFAULT = true;
    private static final boolean SHORTCUT_LOCKED_UNINSTALL_DEFAULT = false;
    private static final boolean SHORTCUT_LOCKED_EDIT_DEFAULT = false;
    private static final boolean CARET_LONG_PRESS_DEFAULT = true;
    private static final int DATE_FORMAT_DEFAULT = R.string.date_format_normal;
    private static final boolean PAGE_INDICATOR_DEFAULT = true;

    private static final String THEME_WALLPAPER = "wallpaper";
    private static final String THEME_LIGHT = "light";
    private static final String THEME_DARK = "dark";

    public static final String SYSTEM_DEFAULT_ICON_KEY = "system_default_icon_key";
    public static final CharSequence SYSTEM_DEFAULT_ICON_VALUE = "system_default_icon_key";
    public static final String SYSTEM_DEFAULT_ICON_PACK = "system_default_icon_pack";
    public static final int SYSTEM_DEFAULT_ICON_RES_ID = -10;
    private static final String CUSTOM_ICON_PACK_KEY_TEMPLATE = "%s#pack";
    private static final String CUSTOM_ICON_RES_KEY_TEMPLATE = "%s#res";

    public static boolean isLeanSettingsDirty(Context context) {
        return prefs(context).getBoolean(SETTINGS_DIRTY, SETTINGS_DIRTY_DEFAULT);
    }

    public static void setLeanSettingsDirty(Context context) {
        prefs(context).edit().putBoolean(SETTINGS_DIRTY, true).apply();
    }

    public static void clearLeanSettingsDirty(Context context) {
        prefs(context).edit().putBoolean(SETTINGS_DIRTY, false).apply();
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

    public static boolean isNavigationBarTransparent(Context context) {
        return prefs(context).getBoolean(TRANSPARENT_NAVIGATION_BAR, TRANSPARENT_NAVIGATION_BAR_DEFAULT);
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

    public static float getIconSizeModifier(Context context) {
        String modifier = prefs(context).getString(ICON_SIZE, ICON_SIZE_DEFAULT);
        return translateModifier(modifier);
    }

    public static float getIconTextSizeModifier(Context context) {
        String modifier = prefs(context).getString(ICON_TEXT_SIZE, ICON_TEXT_SIZE_DEFAULT);
        return translateModifier(modifier);
    }

    private static float translateModifier(String modifier) {
        float offset;
        switch (modifier) {
            case "extrasmall":
                offset = 0.75F;
                break;
            case "small":
                offset = 0.90F;
                break;
            case "average":
                offset = 1.00F;
                break;
            case "large":
                offset = 1.10F;
                break;
            case "extralarge":
                offset = 1.25F;
                break;
            default:
                offset = 1.00F;
                break;
        }
        return offset;
    }

    public static String getSearchProvider(Context context) {
        return prefs(context).getString(SEARCH_PROVIDER, SEARCH_PROVIDER_DEFAULT);
    }

    public static int getHotseatBackgroundAlpha(Context context) {
        String string = prefs(context).getString(HOTSEAT_BACKGROUND, HOTSEAT_BACKGROUND_DEFAULT);
        try {
            return Integer.parseInt(string);
        } catch (Throwable t) {
            return 100;
        }
    }

    public static boolean isBottomSearchBarDark(Context context) {
        return prefs(context).getBoolean(DARK_BOTTOM_SEARCH_BAR, DARK_BOTTOM_SEARCH_BAR_DEFAULT);
    }

    public static boolean isTopSearchBarDark(Context context) {
        return prefs(context).getBoolean(DARK_TOP_SEARCH_BAR, DARK_TOP_SEARCH_BAR_DEFAULT);
    }

    public static boolean isLabelHiddenOnDesktop(Context context) {
        return prefs(context).getBoolean(LABEL_HIDDEN_ON_DESKTOP, LABEL_HIDDEN_ON_DESKTOP_DEFAULT);
    }

    public static boolean isLabelHiddenOnAllApps(Context context) {
        return prefs(context).getBoolean(LABEL_HIDDEN_ON_ALL_APPS, LABEL_HIDDEN_ON_ALL_APPS_DEFAULT);
    }

    public static String getCustomAppName(Context context, ComponentName componentName) {
        if (componentName == null) {
            return "";
        } else {
            return Utilities.getCustomAppNamePrefs(context).getString(componentName.flattenToString(), "");
        }
    }

    public static void setCustomAppName(Context context, ComponentName componentName, String appName) {
        if (componentName != null) {
            Utilities.getCustomAppNamePrefs(context).edit().putString(componentName.flattenToString(), appName).apply();
        }
    }

    public static boolean isQsbVoiceIconVisible(Context context) {
        return prefs(context).getBoolean(QSB_VOICE_ICON, QSB_VOICE_ICON_DEFAULT);
    }

    public static String getHomeAction(Context context) {
        return prefs(context).getString(HOME_ACTION, "");
    }

    public static boolean isDoubleTapToLockSecure(Context context) {
        return prefs(context).getBoolean(DOUBLE_TAP_TO_LOCK_IS_SECURE, DOUBLE_TAP_TO_LOCK_IS_SECURE_DEFAULT);
    }

    public static boolean isOneFingerDownEnabled(Context context) {
        return prefs(context).getBoolean(ONE_FINGER_DOWN, ONE_FINGER_DOWN_DEFAULT);
    }

    public static boolean isTwoFingerDownEnabled(Context context) {
        return prefs(context).getBoolean(TWO_FINGER_DOWN, TWO_FINGER_DOWN_DEFAULT);
    }

    public static boolean shouldUseBlackColors(Context context) {
        return prefs(context).getBoolean(BLACK_COLORS, BLACK_COLORS_DEFAULT);
    }

    public static boolean shouldShowCaret(Context context) {
        return prefs(context).getBoolean(SHOW_CARET, SHOW_CARET_DEFAULT);
    }

    public static boolean shouldGenerateAdaptiveIcons(Context context) {
        return prefs(context).getBoolean(GENERATE_ADAPTIVE_ICONS, GENERATE_ADAPTIVE_ICONS_DEFAULT);
    }

    public static boolean shouldGenerateAdaptiveBackground(Context context) {
        return prefs(context).getBoolean(GENERATED_ADAPTIVE_BACKGROUND, GENERATED_ADAPTIVE_BACKGROUND_DEFAULT);
    }

    public static boolean shouldAllowTwoLineLabels(Context context) {
        return prefs(context).getBoolean(ALLOW_TWO_LINE_LABELS, ALLOW_TWO_LINE_LABELS_DEFAULT);
    }

    public static boolean isUnlockedWidgetsVisible(Context context) {
        return prefs(context).getBoolean(SHORTCUT_UNLOCKED_WIDGETS, SHORTCUT_UNLOCKED_WIDGETS_DEFAULT);
    }

    public static boolean isUnlockedUninstallVisible(Context context) {
        return prefs(context).getBoolean(SHORTCUT_UNLOCKED_UNINSTALL, SHORTCUT_UNLOCKED_UNINSTALL_DEFAULT);
    }

    public static boolean isUnlockedEditVisible(Context context) {
        return prefs(context).getBoolean(SHORTCUT_UNLOCKED_EDIT, SHORTCUT_UNLOCKED_EDIT_DEFAULT);
    }

    public static boolean isLockedUninstallVisible(Context context) {
        return prefs(context).getBoolean(SHORTCUT_LOCKED_UNINSTALL, SHORTCUT_LOCKED_UNINSTALL_DEFAULT);
    }

    public static boolean isLockedEditVisible(Context context) {
        return prefs(context).getBoolean(SHORTCUT_LOCKED_EDIT, SHORTCUT_LOCKED_EDIT_DEFAULT);
    }

    public static boolean shouldOpenAppSearchOnCaretLongPress(Context context) {
        return prefs(context).getBoolean(CARET_LONG_PRESS, CARET_LONG_PRESS_DEFAULT);
    }

    public static Pair<String, Integer> getCustomIcon(Context context, ComponentName forComponent) {
        String iconPack = Utilities.getCustomIconPrefs(context).getString(String.format(CUSTOM_ICON_PACK_KEY_TEMPLATE, forComponent.flattenToString()), null);
        int resId = Utilities.getCustomIconPrefs(context).getInt(String.format(CUSTOM_ICON_RES_KEY_TEMPLATE, forComponent.flattenToString()), 0);
        return Pair.create(iconPack, resId);
    }

    public static void setCustomIcon(Context context, String forComponent, String iconPack, int iconResId) {
        if (TextUtils.isEmpty(forComponent)) {
            return;
        }

        ComponentName componentName = ComponentName.unflattenFromString(forComponent);

        if (componentName != null) {
            Utilities.getCustomIconPrefs(context)
                    .edit()
                    .putString(String.format(CUSTOM_ICON_PACK_KEY_TEMPLATE, componentName.flattenToString()), iconPack)
                    .putInt(String.format(CUSTOM_ICON_RES_KEY_TEMPLATE, componentName.flattenToString()), iconResId)
                    .apply();
        }
    }

    public static void clearCustomIcons(Context context) {
        Utilities.getCustomIconPrefs(context).edit().clear().apply();
    }

    public static String getDateFormat(Context context) {
        return prefs(context).getString(DATE_FORMAT, context.getString(DATE_FORMAT_DEFAULT));
    }

    public static boolean isPageIndicatorVisible(Context context) {
        return prefs(context).getBoolean(PAGE_INDICATOR, PAGE_INDICATOR_DEFAULT);
    }

    private static SharedPreferences prefs(Context context) {
        return Utilities.getPrefs(context);
    }

    private LeanSettings() {

    }
}
