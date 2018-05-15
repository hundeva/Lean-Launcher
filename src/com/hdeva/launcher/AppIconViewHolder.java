package com.hdeva.launcher;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.launcher3.R;
import com.android.launcher3.util.ComponentKey;
import com.google.android.apps.nexuslauncher.CustomIconUtils;

public class AppIconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ViewGroup container;
    ImageView icon;

    AppIconInfo appIconInfo;

    public AppIconViewHolder(View itemView) {
        super(itemView);
        container = itemView.findViewById(R.id.lean_item_app_icon_container);
        icon = itemView.findViewById(R.id.lean_item_app_icon);

        container.setOnClickListener(this);
    }

    public void bind(String packKey, AppIconInfo appIconInfo) {
        this.appIconInfo = appIconInfo;

        Drawable drawable;
        try {
            Resources resources = itemView.getContext().getPackageManager().getResourcesForApplication(packKey);
            drawable = resources.getDrawable(appIconInfo.resourceId);
        } catch (Throwable t) {
            drawable = null;
        }

        if (drawable == null) {
            icon.setImageResource(R.drawable.ic_launcher_home);
        } else {
            icon.setImageDrawable(drawable);
        }
    }

    @Override
    public void onClick(View v) {
        CustomIconUtils.reloadIconByKey(v.getContext(), new ComponentKey(v.getContext(), appIconInfo.componentName.flattenToString()));
    }
}
