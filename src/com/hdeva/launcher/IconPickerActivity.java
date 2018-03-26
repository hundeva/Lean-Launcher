package com.hdeva.launcher;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.android.launcher3.R;

public class IconPickerActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lean_activity_icon_picker);
        bindViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch (item.getItemId()) {
            case android.R.id.home:
                handled = true;
                onBackPressed();
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }
        return handled;
    }

    private void bindViews() {
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
