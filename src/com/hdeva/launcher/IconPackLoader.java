package com.hdeva.launcher;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IconPackLoader extends Fragment {

    private static final String PACK_KEY_KEY = "packKey";

    private final List<AppIconInfo> appIcons = new ArrayList<>();

    private AsyncTask<Void, Void, Void> task;
    private IconPackLoaderListener listener;
    private String packKey;

    public static IconPackLoader forPack(String packKey) {
        IconPackLoader fragment = new IconPackLoader();
        Bundle bundle = new Bundle();
        bundle.putString(PACK_KEY_KEY, packKey);
        fragment.setArguments(bundle);
        return fragment;
    }

    public List<AppIconInfo> getAppIcons() {
        return appIcons;
    }

    public void forceStop() {
        if (task != null) {
            task.cancel(true);
        }
    }

    public void setListener(IconPackLoaderListener listener) {
        this.listener = listener;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        packKey = getArguments().getString(PACK_KEY_KEY);
        task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Set<Integer> resIdCache = new HashSet<>();
                    Resources res = getActivity().getPackageManager().getResourcesForApplication(packKey);
                    int resId = res.getIdentifier("appfilter", "xml", packKey);
                    if (resId != 0) {
                        String compStart = "ComponentInfo{";
                        int compStartlength = compStart.length();
                        String compEnd = "}";
                        int compEndLength = compEnd.length();

                        XmlResourceParser parseXml = getActivity().getPackageManager().getXml(packKey, resId, null);
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
                                            if (!isCalendar) {
                                                int drawableId = res.getIdentifier(drawableName, "drawable", packKey);
                                                if (drawableId != 0 && !resIdCache.contains(drawableId)) {
                                                    resIdCache.add(drawableId);
                                                    appIcons.add(new AppIconInfo(parsed, drawableId));
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            int size = appIcons.size();
                            if (size != 0 && (size == 100 || size % 1000 == 0)) {
                                publishProgress();
                            }
                        }
                    }
                } catch (PackageManager.NameNotFoundException | XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (listener != null) {
                    listener.onIconPackLoaded();
                }
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                if (listener != null) {
                    listener.onIconPackLoaded();
                }
            }
        }.execute();
    }
}
