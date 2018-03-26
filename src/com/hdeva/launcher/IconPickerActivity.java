package com.hdeva.launcher;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.android.launcher3.R;
import com.google.android.apps.nexuslauncher.clock.CustomClock;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IconPickerActivity extends Activity {

    private static final String COMPONENT_NAME_KEY = "componentName";
    private static final String PACKAGE_NAME_KEY = "packageName";
    private static final String PACK_KEY_KEY = "packKey";
    private static final String PACK_VALUE_KEY = "packValue";

    private final Map<ComponentName, Integer> packComponents = new HashMap<>();
    private final Map<ComponentName, String> packCalendars = new HashMap<>();
    private final Map<Integer, CustomClock.Metadata> packClocks = new HashMap<>();

    private String componentName;
    private String packageName;
    private String packKey;
    private String packValue;

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
        parsePack();
        bindPack();
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
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void parsePack() {
        // should be async
        try {
            Resources res = getPackageManager().getResourcesForApplication(packKey);
            int resId = res.getIdentifier("appfilter", "xml", packKey);
            if (resId != 0) {
                String compStart = "ComponentInfo{";
                int compStartlength = compStart.length();
                String compEnd = "}";
                int compEndLength = compEnd.length();

                XmlResourceParser parseXml = getPackageManager().getXml(packKey, resId, null);
                while (parseXml.next() != XmlPullParser.END_DOCUMENT) {
                    if (parseXml.getEventType() == XmlPullParser.START_TAG) {
                        String name = parseXml.getName();
                        boolean isCalendar = name.equals("calendar");
                        if (isCalendar || name.equals("item")) {
                            String componentName = parseXml.getAttributeValue(null, "component");
                            String drawableName = parseXml.getAttributeValue(null, isCalendar ? "prefix" : "drawable");
                            if (componentName != null && drawableName != null && componentName.startsWith(compStart) && componentName.endsWith(compEnd)) {
                                componentName = componentName.substring(compStartlength, componentName.length() - compEndLength);
                                ComponentName parsed = ComponentName.unflattenFromString(componentName);
                                if (parsed != null) {
                                    if (isCalendar) {
                                        packCalendars.put(parsed, drawableName);
                                    } else {
                                        int drawableId = res.getIdentifier(drawableName, "drawable", packKey);
                                        if (drawableId != 0) {
                                            packComponents.put(parsed, drawableId);
                                        }
                                    }
                                }
                            }
                        } else if (name.equals("dynamic-clock")) {
                            String drawableName = parseXml.getAttributeValue(null, "drawable");
                            if (drawableName != null) {
                                int drawableId = res.getIdentifier(drawableName, "drawable", packKey);
                                if (drawableId != 0) {
                                    packClocks.put(drawableId, new CustomClock.Metadata(
                                            parseXml.getAttributeIntValue(null, "hourLayerIndex", -1),
                                            parseXml.getAttributeIntValue(null, "minuteLayerIndex", -1),
                                            parseXml.getAttributeIntValue(null, "secondLayerIndex", -1),
                                            parseXml.getAttributeIntValue(null, "defaultHour", 0),
                                            parseXml.getAttributeIntValue(null, "defaultMinute", 0),
                                            parseXml.getAttributeIntValue(null, "defaultSecond", 0)));
                                }
                            }
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException | XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void bindPack() {

    }
}
