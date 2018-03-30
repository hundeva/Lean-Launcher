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

    private int originalTimeout;

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
        } else {
            originalTimeout = savedInstanceState.getInt(ORIGINAL_TIMEOUT_KEY);
        }

        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ORIGINAL_TIMEOUT_KEY, originalTimeout);
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
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, originalTimeout);
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
