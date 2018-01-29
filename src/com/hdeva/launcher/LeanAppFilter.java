package com.hdeva.launcher;

import android.content.ComponentName;
import android.content.Context;

import com.android.launcher3.AppFilter;

public class LeanAppFilter extends AppFilter {

    private Context context;

    public LeanAppFilter(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldShowApp(ComponentName app) {
        return !LeanSettings.isComponentHidden(context, app);
    }
}
