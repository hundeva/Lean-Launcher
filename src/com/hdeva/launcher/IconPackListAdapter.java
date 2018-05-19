package com.hdeva.launcher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcher3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IconPackListAdapter extends RecyclerView.Adapter<IconPackListViewHolder> {

    private final List<String> keys = new ArrayList<>();
    private final List<CharSequence> values = new ArrayList<>();

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
        iconPackListViewHolder.bind(keys.get(i), values.get(i));
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    @Override
    public long getItemId(int position) {
        return keys.get(position).hashCode();
    }

    public void refresh(Map<String, CharSequence> iconPacks) {
        keys.clear();
        values.clear();

        keys.add(LeanSettings.SYSTEM_DEFAULT_ICON_KEY);
        values.add(LeanSettings.SYSTEM_DEFAULT_ICON_VALUE);
        for (Map.Entry<String, CharSequence> entry : iconPacks.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }
        notifyDataSetChanged();
    }
}
