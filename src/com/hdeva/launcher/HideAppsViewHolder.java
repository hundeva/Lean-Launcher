package com.hdeva.launcher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.launcher3.AppInfo;
import com.android.launcher3.LauncherAppState;
import com.android.launcher3.R;
import com.google.android.apps.nexuslauncher.CustomAppFilter;

public class HideAppsViewHolder extends RecyclerView.ViewHolder {

    ViewGroup container;
    ImageView launcherIcon;
    TextView appName;
    TextView className;
    CheckBox isHidden;

    AppInfo appInfo;

    public HideAppsViewHolder(View itemView) {
        super(itemView);
        container = (ViewGroup) itemView.findViewById(R.id.hide_app_container);
        launcherIcon = (ImageView) itemView.findViewById(R.id.hide_app_icon);
        appName = (TextView) itemView.findViewById(R.id.hide_app_name);
        className = (TextView) itemView.findViewById(R.id.hide_app_class_name);
        isHidden = (CheckBox) itemView.findViewById(R.id.hide_app_checkbox);

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAppVisibility();
            }
        });
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;

        if (appInfo.usingLowResIcon) {
            LauncherAppState.getInstance(itemView.getContext()).getIconCache().getTitleAndIcon(appInfo, false);
        }
        launcherIcon.setImageBitmap(appInfo.iconBitmap);
        appName.setText(appInfo.title);
        className.setText(appInfo.componentName.getClassName());
        isHidden.setChecked(CustomAppFilter.isHidden(itemView.getContext(), appInfo.componentName));
    }

    private void toggleAppVisibility() {
        boolean currentState = isHidden.isChecked();
        CustomAppFilter.hideComponent(itemView.getContext(), appInfo.componentName, !currentState);
        isHidden.setChecked(!currentState);
    }
}
