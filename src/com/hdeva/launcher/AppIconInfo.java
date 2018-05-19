package com.hdeva.launcher;

import android.content.ComponentName;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class AppIconInfo {
    private final List<ComponentName> componentNames = new ArrayList<>();
    public final String iconName;
    public final int iconResourceId;

    public AppIconInfo(String iconName, int iconResourceId) {
        this.iconName = iconName;
        this.iconResourceId = iconResourceId;
    }

    public void addComponent(ComponentName componentName) {
        if (!componentNames.contains(componentName)) {
            componentNames.add(componentName);
        }
    }

    public boolean quickFilter(String filter) {
        if (TextUtils.isEmpty(filter)) {
            return false;
        }

        if (iconName.contains(filter)) {
            return true;
        }

        return false;
    }

    public boolean detailedFilter(String filter) {
        if (TextUtils.isEmpty(filter)) {
            return false;
        }

        if (iconName.contains(filter)) {
            return true;
        }

        for (ComponentName componentName : componentNames) {
            if (componentName.getPackageName().contains(filter.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
