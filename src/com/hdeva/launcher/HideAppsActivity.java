package com.hdeva.launcher;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.LauncherActivityInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.launcher3.AppInfo;
import com.android.launcher3.IconCache;
import com.android.launcher3.LauncherAppState;
import com.android.launcher3.R;
import com.android.launcher3.compat.LauncherAppsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HideAppsActivity extends Activity {

    ProgressBar progressBar;
    RecyclerView recyclerView;

    LeanHideAppsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lean_activity_hide_apps);
        bindViews();
        query();
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

    private void bindViews() {
        progressBar = (ProgressBar) findViewById(R.id.activity_hide_apps_progress);
        recyclerView = (RecyclerView) findViewById(R.id.activity_hide_apps_recycler_view);

        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        adapter = new LeanHideAppsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void query() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        final List<AppInfo> apps = new ArrayList<>();
        final List<ComponentName> duplicatePreventionCache = new ArrayList<>();
        final UserHandle user = android.os.Process.myUserHandle();
        final IconCache iconCache = LauncherAppState.getInstance(this).getIconCache();
        for (LauncherActivityInfo info : LauncherAppsCompat.getInstance(this).getActivityList(null, user)) {
            if (!duplicatePreventionCache.contains(info.getComponentName())) {
                duplicatePreventionCache.add(info.getComponentName());
                final AppInfo appInfo = new AppInfo(this, info, user);
                iconCache.getTitleAndIcon(appInfo, false);
                apps.add(appInfo);
            }
        }
        Collections.sort(apps, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo o1, AppInfo o2) {
                String t1 = o1.title == null ? "" : ((String) o1.title).trim().toLowerCase();
                String t2 = o2.title == null ? "" : ((String) o2.title).trim().toLowerCase();
                return t1.compareTo(t2);
            }
        });

        adapter.setApps(apps);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
