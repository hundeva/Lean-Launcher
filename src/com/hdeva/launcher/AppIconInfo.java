package com.hdeva.launcher;

import android.content.ComponentName;

public class AppIconInfo {
    public final ComponentName componentName;
    public final int resourceId;

    public AppIconInfo(ComponentName componentName, int resourceId) {
        this.componentName = componentName;
        this.resourceId = resourceId;
    }
}
