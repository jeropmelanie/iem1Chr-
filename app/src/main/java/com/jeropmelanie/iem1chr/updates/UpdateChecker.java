package com.jeropmelanie.iem1chr.updates;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {
    private static final String TAG = "UpdateChecker";
    private Context context;
    private static final String MANIFEST_URL = 
        "https://raw.githubusercontent.com/jeropmelanie/iem1Chr-/main/releases/manifest.json";
    private static final String PREFS_NAME = "app_state";
    private static final String VERSION_KEY = "app_version";

    public UpdateChecker(Context context) {
        this.context = context;
    }

    /**
     * Check for updates asynchronously
     */
    public void checkForUpdates(UpdateListener listener) {
        new Thread(() -> {
            try {
                String latestVersion = fetchLatestVersion();
                String currentVersion = getCurrentVersion();

                Log.d(TAG, "Current: " + currentVersion + ", Latest: " + latestVersion);

                if (latestVersion != null && isNewerVersion(latestVersion, currentVersion)) {
                    listener.onUpdateAvailable(latestVersion);
                } else {
                    listener.onUpToDate();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error checking updates", e);
                listener.onError(e);
            }
        }).start();
    }

    /**
     * Fetch latest version from GitHub
     */
    private String fetchLatestVersion() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(MANIFEST_URL).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("HTTP " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        connection.disconnect();

        JSONObject manifest = new JSONObject(response.toString());
        return manifest.getString("version");
    }

    /**
     * Get current app version
     */
    private String getCurrentVersion() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(VERSION_KEY, "1.0.0");
    }

    /**
     * Save current version after update
     */
    public void saveVersion(String version) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(VERSION_KEY, version).apply();
        Log.d(TAG, "Saved version: " + version);
    }

    /**
     * Compare semantic versioning
     */
    private boolean isNewerVersion(String latest, String current) {
        try {
            String[] latestParts = latest.split("\\.");
            String[] currentParts = current.split("\\.");

            int maxLength = Math.max(latestParts.length, currentParts.length);
            for (int i = 0; i < maxLength; i++) {
                int l = i < latestParts.length ? Integer.parseInt(latestParts[i]) : 0;
                int c = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
                
                if (l > c) return true;
                if (l < c) return false;
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error comparing versions", e);
            return false;
        }
    }

    public interface UpdateListener {
        void onUpdateAvailable(String version);
        void onUpToDate();
        void onError(Exception e);
    }
}
