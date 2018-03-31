package com.hdeva.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.android.launcher3.R;

public class LeanTimeoutActivity extends Activity {

    private static final String ORIGINAL_TIMEOUT_KEY = "originalTimeoutKey";
    private static final String ORIGINAL_STAY_ON_WHILE_PLUGGED_IN_KEY = "originalStayOnWhilePluggedInKey";

    private int originalTimeout;
    private int originalStayOnWhilePluggedIn;

    public static void startTimeout(Context context) {
        context.startActivity(new Intent(context, LeanTimeoutActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        disableTouches();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lean_activity_timeout);
        if (savedInstanceState == null) {
            originalTimeout = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 60000);
            originalStayOnWhilePluggedIn = Settings.System.getInt(getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN, 0);
        } else {
            originalTimeout = savedInstanceState.getInt(ORIGINAL_TIMEOUT_KEY);
            originalStayOnWhilePluggedIn = savedInstanceState.getInt(ORIGINAL_STAY_ON_WHILE_PLUGGED_IN_KEY);
        }

        try {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 0);
        } catch (Throwable t) {
            LeanUtils.reportNonFatal(new Exception("Error writing Settings.System.SCREEN_OFF_TIMEOUT", t));
        }
        try {
            Settings.System.putInt(getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN, 0);
        } catch (Throwable t) {
            LeanUtils.reportNonFatal(new Exception("Error writing Settings.Global.STAY_ON_WHILE_PLUGGED_IN", t));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ORIGINAL_TIMEOUT_KEY, originalTimeout);
        outState.putInt(ORIGINAL_STAY_ON_WHILE_PLUGGED_IN_KEY, originalStayOnWhilePluggedIn);
    }

    @Override
    public void onBackPressed() {
        // ignore
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isFinishing()) {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        try {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, originalTimeout);
        } catch (Throwable t) {
            LeanUtils.reportNonFatal(new Exception("Error writing Settings.System.SCREEN_OFF_TIMEOUT", t));
        }
        try {
            Settings.System.putInt(getContentResolver(), Settings.Global.STAY_ON_WHILE_PLUGGED_IN, originalStayOnWhilePluggedIn);
        } catch (Throwable t) {
            LeanUtils.reportNonFatal(new Exception("Error writing Settings.Global.STAY_ON_WHILE_PLUGGED_IN", t));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setImmersiveFullScreen();
    }

    private void disableTouches() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void setImmersiveFullScreen() {
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        getWindow().getDecorView().setSystemUiVisibility(flags);
    }
}
