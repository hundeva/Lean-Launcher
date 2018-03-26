package com.hdeva.launcher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher3.R;

import java.util.Map;

public class IconPackListAdapter extends RecyclerView.Adapter<IconPackListViewHolder> {

    private String[] keys;
    private CharSequence[] values;

    private String componentName;
    private String packageName;

    public IconPackListAdapter(String componentName, String packageName) {
        this.componentName = componentName;
        this.packageName = packageName;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public IconPackListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lean_item_icon_pack, viewGroup, false);
        return new IconPackListViewHolder(view, componentName, packageName);
    }

    @Override
    public void onBindViewHolder(@NonNull IconPackListViewHolder iconPackListViewHolder, int i) {
        iconPackListViewHolder.bind(keys[i], values[i]);
    }

    @Override
    public int getItemCount() {
        return keys == null ? 0 : keys.length;
    }

    @Override
    public long getItemId(int position) {
        return keys[position].hashCode();
    }

    public void refresh(Map<String, CharSequence> iconPacks) {
        keys = iconPacks.keySet().toArray(new String[iconPacks.size()]);
        values = iconPacks.values().toArray(new CharSequence[iconPacks.size()]);
        notifyDataSetChanged();
    }
}
