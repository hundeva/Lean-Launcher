package com.hdeva.launcher;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class LeanDoubleTapToLockRegistry {

    private static final long TIMEOUT_THRESHOLD = 350L;
    private List<TouchRecord> history = new ArrayList<>();

    private static final int REGISTRY_SIZE = 4;

    public void add(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            history.add(0, new TouchRecord(action));
            while (history.size() > REGISTRY_SIZE) {
                history.remove(history.size() - 1);
            }
        }
    }

    public boolean shouldLock() {
        return history.size() == REGISTRY_SIZE
                && history.get(0).action == MotionEvent.ACTION_UP
                && history.get(1).action == MotionEvent.ACTION_DOWN
                && history.get(2).action == MotionEvent.ACTION_UP
                && history.get(3).action == MotionEvent.ACTION_DOWN
                && history.get(0).timestamp - history.get(3).timestamp < TIMEOUT_THRESHOLD;
    }

    static class TouchRecord {
        int action;
        long timestamp;

        TouchRecord(int action) {
            this.action = action;
            this.timestamp = System.currentTimeMillis();
        }
    }
}
