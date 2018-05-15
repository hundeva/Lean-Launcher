package com.hdeva.launcher;

import android.content.ComponentName;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppIconAdapter extends RecyclerView.Adapter<AppIconViewHolder> {

    private List<AppIconInfo> appIcons;
    private String packKey;

    public AppIconAdapter(String packKey) {
        setHasStableIds(true);
        this.packKey = packKey;
    }

    @NonNull
    @Override
    public AppIconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lean_item_app_icon, parent, false);
        return new AppIconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppIconViewHolder holder, int position) {
        holder.bind(packKey, appIcons.get(position));
    }

    @Override
    public long getItemId(int position) {
        return appIcons.get(position).componentName.hashCode();
    }

    @Override
    public int getItemCount() {
        return appIcons == null ? 0 : appIcons.size();
    }

    public void setIconList(List<AppIconInfo> appIcons) {
        this.appIcons = appIcons;
        notifyDataSetChanged();
    }
}
