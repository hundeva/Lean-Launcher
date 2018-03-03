package com.google.android.apps.nexuslauncher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.TwoStatePreference;
import android.text.TextUtils;
import android.util.Log;

import com.android.launcher3.R;
import com.hdeva.launcher.LeanSettings;
import com.hdeva.launcher.LeanUtils;

public class SettingsActivity extends com.android.launcher3.SettingsActivity implements PreferenceFragment.OnPreferenceStartFragmentCallback {
    public final static String ICON_PACK_PREF = "pref_icon_pack";
    public final static String SHOW_PREDICTIONS_PREF = "pref_show_predictions";
    public final static String ENABLE_MINUS_ONE_PREF = "pref_enable_minus_one";
    public final static String SMARTSPACE_PREF = "pref_smartspace";
    public final static String APP_VERSION_PREF = "about_app_version";
    private final static String GOOGLE_APP = "com.google.android.googlequicksearchbox";

    private static final String SMARTSPACE_COMPANION = "pref_smartspace_companion";
    private static final String SMARTSPACE_PING = "com.hdeva.launcher.AT_A_GLANCE_PING";
    private static final String SMARTSPACE_PING_RESPONSE = "com.hdeva.launcher.AT_A_GLANCE_PING_RESPONSE";

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            getFragmentManager().beginTransaction().replace(android.R.id.content, new MySettingsFragment()).commit();
        }
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment preferenceFragment, Preference preference) {
        Fragment instantiate = Fragment.instantiate(this, preference.getFragment(), preference.getExtras());
        if (instantiate instanceof DialogFragment) {
            ((DialogFragment) instantiate).show(getFragmentManager(), preference.getKey());
        } else {
            getFragmentManager().beginTransaction().replace(android.R.id.content, instantiate).addToBackStack(preference.getKey()).commit();
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (LeanSettings.isLeanSettingsDirty(this) && !isChangingConfigurations()) {
            LeanSettings.clearLeanSettingsDirty(this);
            LeanUtils.restart(this);
        }
    }

    public static class MySettingsFragment extends com.android.launcher3.SettingsActivity.LauncherSettingsFragment
            implements Preference.OnPreferenceChangeListener {
        private CustomIconPreference mIconPackPref;
        private Context mContext;

        BroadcastReceiver smartspaceReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String version = intent.getStringExtra(SMARTSPACE_PING_RESPONSE);
                findPreference(SMARTSPACE_COMPANION).setSummary(context.getString(R.string.companion_app_version_x, version));
            }
        };

        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);

            mContext = getActivity();

            findPreference(SHOW_PREDICTIONS_PREF).setOnPreferenceChangeListener(this);
            findPreference(ENABLE_MINUS_ONE_PREF).setTitle(getDisplayGoogleTitle());

            PackageManager packageManager = mContext.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
                findPreference(APP_VERSION_PREF).setSummary(packageInfo.versionName);
            } catch (PackageManager.NameNotFoundException ex) {
                Log.e("SettingsActivity", "Unable to load my own package info", ex);
            }

            findPreference(LeanSettings.THEME_KEY).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.BOTTOM_SEARCH_BAR_KEY).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.TOP_SEARCH_BAR_KEY).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.PHYSICAL_ANIMATION_KEY).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.TRANSPARENT_NAVIGATION_BAR).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.EXTRA_BOTTOM_PADDING).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.GRID_COLUMNS).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.GRID_ROWS).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.HOTSEAT_ICONS).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.FORCE_COLORED_G_ICON).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.ICON_SIZE).setOnPreferenceChangeListener(this);
            findPreference(LeanSettings.HOTSEAT_BACKGROUND).setOnPreferenceChangeListener(this);

            try {
                ApplicationInfo applicationInfo = mContext.getPackageManager().getApplicationInfo(GOOGLE_APP, 0);
                if (!applicationInfo.enabled) {
                    throw new PackageManager.NameNotFoundException();
                }
            } catch (PackageManager.NameNotFoundException ignored) {
                getPreferenceScreen().removePreference(findPreference(SettingsActivity.ENABLE_MINUS_ONE_PREF));
            }

            mIconPackPref = (CustomIconPreference) findPreference(ICON_PACK_PREF);
            mIconPackPref.setOnPreferenceChangeListener(this);

            findPreference(SHOW_PREDICTIONS_PREF).setOnPreferenceChangeListener(this);
        }

        private String getDisplayGoogleTitle() {
            CharSequence charSequence = null;
            try {
                Resources resourcesForApplication = mContext.getPackageManager().getResourcesForApplication(GOOGLE_APP);
                int identifier = resourcesForApplication.getIdentifier("title_google_home_screen", "string", GOOGLE_APP);
                if (identifier != 0) {
                    charSequence = resourcesForApplication.getString(identifier);
                }
            } catch (PackageManager.NameNotFoundException ex) {
            }
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = mContext.getString(R.string.title_google_app);
            }
            return mContext.getString(R.string.title_show_google_app, charSequence);
        }

        @Override
        public void onResume() {
            super.onResume();
            mIconPackPref.reloadIconPacks();
            findPreference(SMARTSPACE_COMPANION).setSummary(getString(R.string.companion_app_not_installed));
            getActivity().registerReceiver(smartspaceReceiver, new IntentFilter(SMARTSPACE_PING_RESPONSE));
            getActivity().sendBroadcast(new Intent(SMARTSPACE_PING).setPackage("com.google.android.apps.nexuslauncher"));
        }

        @Override
        public void onPause() {
            getActivity().unregisterReceiver(smartspaceReceiver);
            super.onPause();
        }

        @Override
        public boolean onPreferenceChange(Preference preference, final Object newValue) {
            switch (preference.getKey()) {
                case LeanSettings.GRID_COLUMNS:
                case LeanSettings.GRID_ROWS:
                case LeanSettings.HOTSEAT_ICONS:
                case LeanSettings.ICON_SIZE:
                    if (preference instanceof ListPreference) {
                        ((ListPreference) preference).setValue((String) newValue);
                    }
                    LeanSettings.setLeanSettingsDirty(mContext);
                    break;

                case LeanSettings.THEME_KEY:
                case LeanSettings.HOTSEAT_BACKGROUND:
                    if (preference instanceof ListPreference) {
                        ((ListPreference) preference).setValue((String) newValue);
                    }
                    LeanUtils.reloadTheme(mContext);
                    break;

                case LeanSettings.BOTTOM_SEARCH_BAR_KEY:
                case LeanSettings.EXTRA_BOTTOM_PADDING:
                case LeanSettings.TOP_SEARCH_BAR_KEY:
                case LeanSettings.PHYSICAL_ANIMATION_KEY:
                case LeanSettings.TRANSPARENT_NAVIGATION_BAR:
                case LeanSettings.FORCE_COLORED_G_ICON:
                    if (preference instanceof TwoStatePreference) {
                        ((TwoStatePreference) preference).setChecked((boolean) newValue);
                    }
                    LeanUtils.reloadTheme(mContext);
                    break;

                case ICON_PACK_PREF:
                    if (!CustomIconUtils.getCurrentPack(mContext).equals(newValue)) {
                        final ProgressDialog applyingDialog = ProgressDialog.show(mContext,
                                null /* title */,
                                mContext.getString(R.string.state_loading),
                                true /* indeterminate */,
                                false /* cancelable */);

                        CustomIconUtils.setCurrentPack(getActivity(), (String) newValue);
                        CustomIconUtils.applyIconPackAsync(mContext);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                applyingDialog.cancel();
                            }
                        }, 1000);
                    }
                    return true;
                case SHOW_PREDICTIONS_PREF:
                    if ((boolean) newValue) {
                        return true;
                    }
                    SettingsActivity.SuggestionConfirmationFragment confirmationFragment = new SettingsActivity.SuggestionConfirmationFragment();
                    confirmationFragment.setTargetFragment(this, 0);
                    confirmationFragment.show(getFragmentManager(), preference.getKey());
                    break;
            }
            return false;
        }
    }

    public static class SuggestionConfirmationFragment extends DialogFragment implements DialogInterface.OnClickListener {
        public void onClick(final DialogInterface dialogInterface, final int n) {
            if (getTargetFragment() instanceof PreferenceFragment) {
                Preference preference = ((PreferenceFragment) getTargetFragment()).findPreference(SHOW_PREDICTIONS_PREF);
                if (preference instanceof TwoStatePreference) {
                    ((TwoStatePreference) preference).setChecked(false);
                }
            }
        }

        public Dialog onCreateDialog(final Bundle bundle) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.title_disable_suggestions_prompt)
                    .setMessage(R.string.msg_disable_suggestions_prompt)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(R.string.label_turn_off_suggestions, this).create();
        }
    }
}
