package com.hdeva.launcher;

import android.content.ComponentName;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class AppIconInfo {
    private final List<ComponentName> componentNames = new ArrayList<>();
    public final int resourceId;

    public AppIconInfo(int resourceId) {
        this.resourceId = resourceId;
    }

    public void addComponent(ComponentName componentName) {
        if (!componentNames.contains(componentName)) {
            componentNames.add(componentName);
        }
    }

    public boolean containsSimilarComponent(String filter) {
        if (TextUtils.isEmpty(filter)) {
            return false;
        }

        for (ComponentName componentName : componentNames) {
            if (componentName.getPackageName().contains(filter.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
