package com.google.android.apps.nexuslauncher.graphics;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.DateFormat;
import android.util.AttributeSet;

import com.android.launcher3.Utilities;
import com.hdeva.launcher.LeanUtils;

public class IcuDateTextView extends DoubleShadowTextView {
    private DateFormat mDateFormat;
    private final BroadcastReceiver mTimeChangeReceiver;
    private boolean mIsVisible = false;

    public IcuDateTextView(final Context context) {
        this(context, null);
    }

    public IcuDateTextView(final Context context, final AttributeSet set) {
        super(context, set, 0);
        mTimeChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                reloadDateFormat();
            }
        };
    }

    @TargetApi(24)
    public void reloadDateFormat() {
        String format = LeanUtils.formatDateTime(getContext(), System.currentTimeMillis());
        setText(format);
        setContentDescription(format);
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        getContext().registerReceiver(mTimeChangeReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        getContext().unregisterReceiver(mTimeChangeReceiver);
    }

    public void onVisibilityAggregated(boolean isVisible) {
        if (Utilities.ATLEAST_NOUGAT) {
            super.onVisibilityAggregated(isVisible);
        }
        if (!mIsVisible && isVisible) {
            mIsVisible = true;
            registerReceiver();
            reloadDateFormat();
        } else if (mIsVisible && !isVisible) {
            unregisterReceiver();
            mIsVisible = false;
        }
    }
}
