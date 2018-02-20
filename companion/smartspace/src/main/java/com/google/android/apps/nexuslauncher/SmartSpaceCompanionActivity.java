package com.google.android.apps.nexuslauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.android.apps.nexuslauncher.smartspace.SmartspaceBroadcastReceiver;

public class SmartSpaceCompanionActivity extends Activity {

    private static final String HIDE_ACTION = "com.hdeva.launcher.HIDE_AT_A_GLANCE_COMPANION";
    private static final String SETTINGS_ACTION = "com.hdeva.launcher.OPEN_LEAN_SETTINGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUi();
        sendEnableBroadcast();
    }

    private void setupUi() {
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setActionBar(toolbar);

        Button hideButton = findViewById(R.id.home_hide_companion_app);
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().sendBroadcast(new Intent(HIDE_ACTION));
                finish();
            }
        });

        Button settingsButton = findViewById(R.id.home_open_lean_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().sendBroadcast(new Intent(SETTINGS_ACTION));
                finish();
            }
        });

    }

    private void sendEnableBroadcast() {
        Intent intent = new Intent(SmartspaceBroadcastReceiver.ENABLE_UPDATE_ACTION);
        intent.setPackage(SmartspaceBroadcastReceiver.ENABLE_UPDATE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent);
    }
}
