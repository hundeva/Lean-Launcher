package com.hdeva.launcher;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.launcher3.R;
import com.android.launcher3.util.ComponentKey;
import com.google.android.apps.nexuslauncher.CustomIconUtils;

import java.util.Map;

public class IconPackListActivity extends Activity {

    private static final String COMPONENT_NAME_KEY = "componentName";
    private static final String PACKAGE_NAME_KEY = "packageName";

    private String componentName;
    private String packageName;

    private RecyclerView recyclerView;
    private IconPackListAdapter adapter;

    public static void openForComponent(Context context, ComponentKey key) {
        openForComponent(context, key.componentName.flattenToShortString(), key.componentName.getPackageName());
    }

    public static void openForComponent(Context context, String componentName, String packageName) {
        Intent intent = new Intent(context, IconPackListActivity.class);
        intent.putExtra(COMPONENT_NAME_KEY, componentName);
        intent.putExtra(PACKAGE_NAME_KEY, packageName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lean_activity_icon_pack_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            componentName = extras.getString(COMPONENT_NAME_KEY);
            packageName = extras.getString(PACKAGE_NAME_KEY);
        }
        bindViews();
        setIconPacks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lean_menu_icon_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch (item.getItemId()) {
            case R.id.menu_custom_icon_reset:
                LeanSettings.setCustomIcon(this, componentName, null, 0);
                CustomIconUtils.reloadIconByKey(this, new ComponentKey(this, componentName));
                Toast.makeText(this, R.string.custom_icon_cleared, Toast.LENGTH_SHORT).show();
                finish();
                handled = true;
                break;
            case android.R.id.home:
                handled = true;
                onBackPressed();
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }
        return handled;
    }

    private void bindViews() {
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.icon_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void setIconPacks() {
        Map<String, CharSequence> packs = CustomIconUtils.getPackProviders(this);
        adapter = new IconPackListAdapter(componentName, packageName);
        adapter.refresh(packs);
        recyclerView.setAdapter(adapter);
    }
}
