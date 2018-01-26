package com.hdeva.launcher;

import android.content.ComponentName;

import com.android.launcher3.AppFilter;

public class LeanAppFilter extends AppFilter {

    @Override
    public boolean shouldShowApp(ComponentName app) {
        // TODO create logic to hide apps from drawer (AllAppsList.java)
        return true;
    }
}
