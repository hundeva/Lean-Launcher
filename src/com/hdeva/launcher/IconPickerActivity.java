package com.hdeva.launcher;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.launcher3.R;

import java.util.ArrayList;
import java.util.List;

public class IconPickerActivity extends Activity implements IconPackLoaderListener {

    private static final String LOADER_TAG = "iconPackLoader";
    private static final String COMPONENT_NAME_KEY = "componentName";
    private static final String PACKAGE_NAME_KEY = "packageName";
    private static final String PACK_KEY_KEY = "packKey";
    private static final String PACK_VALUE_KEY = "packValue";

    private final List<ComponentName> filteredComponents = new ArrayList<>();
    private final List<Integer> filteredResourceIds = new ArrayList<>();

    private String componentName;
    private String packageName;
    private String packKey;
    private String packValue;

    private IconPackLoader loader;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private AppIconAdapter adapter;

    public static void fromPackForApp(Context context, String componentName, String packageName, String packKey, CharSequence packValue) {
        Intent intent = new Intent(context, IconPickerActivity.class);
        intent.putExtra(COMPONENT_NAME_KEY, componentName);
        intent.putExtra(PACKAGE_NAME_KEY, packageName);
        intent.putExtra(PACK_KEY_KEY, packKey);
        intent.putExtra(PACK_VALUE_KEY, packValue);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lean_activity_icon_picker);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            componentName = extras.getString(COMPONENT_NAME_KEY);
            packageName = extras.getString(PACKAGE_NAME_KEY);
            packKey = extras.getString(PACK_KEY_KEY);
            packValue = extras.getString(PACK_VALUE_KEY);
        }
        bindViews();
        setupLoader();
        //filterPack();
        bindPack();
    }

    @Override
    protected void onDestroy() {
        if (loader != null) {
            loader.setListener(null);

            if (!isChangingConfigurations()) {
                loader.forceStop();
            }
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch (item.getItemId()) {
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

    @Override
    public void onIconPackLoaded() {
        bindPack();
    }

    private void bindViews() {
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(packValue);

        progressBar = findViewById(R.id.icon_picker_progress);
        recyclerView = findViewById(R.id.icon_picker_recycler_view);

        adapter = new AppIconAdapter(packKey);
        recyclerView.setLayoutManager(new GridLayoutManager(this, calculateGridCount()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private int calculateGridCount() {
        float iconMargin = getResources().getDimensionPixelSize(R.dimen.custom_app_icon_margin);
        float iconSize = getResources().getDimensionPixelSize(R.dimen.custom_app_icon_size);
        float sumSize = 2 * iconMargin + iconSize;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        int grid = (int) (width / sumSize);
        return grid > 0 ? grid : 3;
    }

    private void setupLoader() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(LOADER_TAG);
        if (fragment == null) {
            loader = IconPackLoader.forPack(packKey);
            loader.setListener(this);
            fragmentManager.beginTransaction()
                    .add(loader, LOADER_TAG)
                    .commit();
        } else {
            loader = (IconPackLoader) fragment;
            loader.setListener(this);
        }
    }

    private void filterPack() {
        filteredComponents.clear();
        filteredResourceIds.clear();

        for (AppIconInfo appIconInfo : loader.getAppIcons()) {
            if (TextUtils.equals(appIconInfo.componentName.getPackageName(), packageName)) {
                Log.i(":::", "filter match");
                Log.i(":::", appIconInfo.toString());
                Log.i(":::", componentName);
                filteredComponents.add(appIconInfo.componentName);
                filteredResourceIds.add(appIconInfo.resourceId);
            }
        }

        Log.i(":::", "filter: " + filteredComponents.size());
        for (int i = 0; i < filteredComponents.size(); i++) {
            Log.i(":::", "filter: " + filteredComponents.get(i) + " -> " + filteredResourceIds.get(i));
        }
    }

    private void bindPack() {
        if (loader.getAppIcons().isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter.setIconList(loader.getAppIcons());
    }
}
