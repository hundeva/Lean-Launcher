package com.hdeva.launcher;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.launcher3.R;
import com.android.launcher3.util.ComponentKey;
import com.google.android.apps.nexuslauncher.CustomIconUtils;

public class IconPickerActivity extends Activity implements IconPackLoaderListener, TextWatcher {

    private static final String TAG = "IconPickerActivity";
    private static final String LOADER_TAG = "iconPackLoader";
    private static final String COMPONENT_NAME_KEY = "componentName";
    private static final String PACKAGE_NAME_KEY = "packageName";
    private static final String PACK_KEY_KEY = "packKey";
    private static final String PACK_VALUE_KEY = "packValue";

    private String componentName;
    private String packageName;
    private String packKey;
    private String packValue;

    private IconPackLoader loader;

    private EditText filter;
    private RecyclerView recyclerView;
    private ViewGroup loadingContainer;

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
        bindPack(loader.isCompletelyParsed());
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

    @Override
    public void onIconPackLoaded(boolean completelyParsed) {
        bindPack(completelyParsed);
    }

    private void bindViews() {
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(packValue);

        filter = findViewById(R.id.icon_picker_filter);
        recyclerView = findViewById(R.id.icon_picker_recycler_view);
        loadingContainer = findViewById(R.id.icon_picker_loading_container);

        try {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
            recyclerView.setBackground(wallpaperDrawable);
        } catch (Throwable t) {
            Log.e(TAG, "Error setting wallpaper background on recycler view", t);
        }

        filter.addTextChangedListener(this);

        adapter = new AppIconAdapter(componentName, packKey);
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

    private void bindPack(boolean completelyParsed) {
        loadingContainer.setVisibility(completelyParsed ? View.GONE : View.VISIBLE);
        adapter.setIconList(loader.getAppIcons(), completelyParsed);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        adapter.filter(s.toString());
    }
}
