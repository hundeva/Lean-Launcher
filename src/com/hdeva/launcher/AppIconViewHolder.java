package com.hdeva.launcher;

import android.content.ComponentName;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.launcher3.R;

import java.util.Map;

public class AppIconViewHolder extends RecyclerView.ViewHolder {

    ImageView icon;

    public AppIconViewHolder(View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.lean_item_app_icon);
    }

    public void bind(String packKey, AppIconInfo appIconInfo) {
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
}
