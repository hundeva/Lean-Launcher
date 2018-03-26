package com.hdeva.launcher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.launcher3.R;

public class IconPackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private String componentName;
    private String packageName;

    private ViewGroup container;
    private TextView name;

    public IconPackViewHolder(View itemView, String componentName, String packageName) {
        super(itemView);
        this.componentName = componentName;
        this.packageName = packageName;

        container = itemView.findViewById(R.id.item_icon_pack_container);
        name = itemView.findViewById(R.id.item_icon_pack_name);

        container.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void bind(String key, CharSequence value) {
        name.setText(value);
    }
}
