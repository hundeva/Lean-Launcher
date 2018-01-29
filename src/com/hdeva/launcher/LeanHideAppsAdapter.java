package com.hdeva.launcher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher3.AppInfo;
import com.android.launcher3.R;

import java.util.List;

public class LeanHideAppsAdapter extends RecyclerView.Adapter<HideAppsViewHolder> {

    private List<AppInfo> apps;

    LeanHideAppsAdapter() {
    }

    @Override
    public HideAppsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lean_item_hide_app, viewGroup, false);
        return new HideAppsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HideAppsViewHolder hideAppsViewHolder, int i) {
        hideAppsViewHolder.setAppInfo(apps.get(i));
    }

    @Override
    public int getItemCount() {
        return apps == null ? 0 : apps.size();
    }

    public void setApps(List<AppInfo> apps) {
        this.apps = apps;
        notifyDataSetChanged();
    }
}
