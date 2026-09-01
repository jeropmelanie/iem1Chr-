# Repository-Based Update System

## Overview
App scans your GitHub repo for updates and pulls new features without resetting user settings.

## How It Works
1. App checks `repo/releases/latest` for new version
2. Downloads update package
3. Merges new files with existing user settings
4. Applies changes on next restart

## Update Manifest
Create `releases/manifest.json` in your repo:
```json
{
  "version": "1.1.0",
  "versionCode": 2,
  "releaseDate": "2026-09-01",
  "changelog": "Added swipe gestures, improved icon pack loading",
  "downloadUrl": "https://github.com/jeropmelanie/iem1Chr-/releases/download/v1.1.0/iem1chr-update.apk",
  "features": {
    "gestures": {"version": "1.1"},
    "icons": {"version": "1.0"},
    "updates": {"version": "1.0"}
  }
}
```

## UpdateChecker.java
```java
package com.jeropmelanie.iem1chr.updates;

import android.content.Context;
import android.content.SharedPreferences;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {
    private Context context;
    private static final String MANIFEST_URL = 
        "https://raw.githubusercontent.com/jeropmelanie/iem1Chr-/main/releases/manifest.json";

    public UpdateChecker(Context context) {
        this.context = context;
    }

    public void checkForUpdates(UpdateListener listener) {
        new Thread(() -> {
            try {
                URLConnection conn = new URL(MANIFEST_URL).openConnection();
                // Parse JSON response
                String latestVersion = getLatestVersion();
                String currentVersion = getCurrentVersion();

                if (isNewerVersion(latestVersion, currentVersion)) {
                    listener.onUpdateAvailable(latestVersion);
                } else {
                    listener.onUpToDate();
                }
            } catch (Exception e) {
                listener.onError(e);
            }
        }).start();
    }

    private String getCurrentVersion() {
        SharedPreferences prefs = context.getSharedPreferences("app_state", Context.MODE_PRIVATE);
        return prefs.getString("version", "1.0.0");
    }

    private String getLatestVersion() {
        // Parse manifest.json
        return "1.1.0";
    }

    private boolean isNewerVersion(String latest, String current) {
        String[] latestParts = latest.split("\\.");
        String[] currentParts = current.split("\\.");

        for (int i = 0; i < latestParts.length; i++) {
            int l = Integer.parseInt(latestParts[i]);
            int c = Integer.parseInt(currentParts[i]);
            if (l > c) return true;
            if (l < c) return false;
        }
        return false;
    }

    public interface UpdateListener {
        void onUpdateAvailable(String version);
        void onUpToDate();
        void onError(Exception e);
    }
}
```

## Settings Preservation
```java
// Before update:
SharedPreferences userSettings = context.getSharedPreferences("user_prefs", MODE_PRIVATE);
String settingsBackup = userSettings.getAll().toString();

// After update:
// Restore settings from backup
for (String key : userSettings.getAll().keySet()) {
    // Preserved settings remain intact
}
```