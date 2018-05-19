package com.hdeva.launcher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher3.R;

import java.util.ArrayList;
import java.util.List;

public class AppIconAdapter extends RecyclerView.Adapter<AppIconViewHolder> {

    private final List<AppIconInfo> appIcons = new ArrayList<>();
    private final List<AppIconInfo> filteredIcons = new ArrayList<>();
    private final String appComponentName;
    private final String packKey;
    private String filter;
    private boolean completelyParsed;

    public AppIconAdapter(String appComponentName, String packKey) {
        setHasStableIds(true);
        this.appComponentName = appComponentName;
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
        holder.bind(appComponentName, packKey, getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).iconResourceId;
    }

    @Override
    public int getItemCount() {
        return !TextUtils.isEmpty(filter) || filteredIcons.size() > 0 ? filteredIcons.size() : appIcons.size();
    }

    private AppIconInfo getItem(int position) {
        return !TextUtils.isEmpty(filter) || filteredIcons.size() > 0 ? filteredIcons.get(position) : appIcons.get(position);
    }

    public void setIconList(List<AppIconInfo> appIcons, boolean completelyParsed) {
        this.completelyParsed = completelyParsed;
        this.appIcons.clear();
        this.appIcons.addAll(appIcons);
        doFilter();
    }

    public void filter(String filter) {
        this.filter = filter;
        doFilter();
    }

    private void doFilter() {
        filteredIcons.clear();

        if (!TextUtils.isEmpty(filter)) {
            for (AppIconInfo appIconInfo : appIcons) {
                if (completelyParsed ? appIconInfo.detailedFilter(filter) : appIconInfo.quickFilter(filter)) {
                    filteredIcons.add(appIconInfo);
                }
            }
        }
        notifyDataSetChanged();
    }
}
