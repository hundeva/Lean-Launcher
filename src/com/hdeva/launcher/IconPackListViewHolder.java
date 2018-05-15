package com.hdeva.launcher;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.launcher3.R;

public class IconPackListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private String componentName;
    private String packageName;

    private String key;
    private CharSequence value;

    private ViewGroup container;
    private TextView name;

    public IconPackListViewHolder(View itemView, String componentName, String packageName) {
        super(itemView);
        this.componentName = componentName;
        this.packageName = packageName;

        container = itemView.findViewById(R.id.item_icon_pack_container);
        name = itemView.findViewById(R.id.item_icon_pack_name);

        container.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        IconPickerActivity.fromPackForApp(v.getContext(), componentName, packageName, key, value);

        if (v.getContext() instanceof Activity) {
            ((Activity) v.getContext()).finish();
        }
    }

    public void bind(String key, CharSequence value) {
        this.key = key;
        this.value = value;
        name.setText(value);
    }
}
