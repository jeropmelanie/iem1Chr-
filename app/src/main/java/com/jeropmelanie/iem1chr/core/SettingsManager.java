package com.jeropmelanie.iem1chr.core;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

public class SettingsManager {
    private static final String PREFS_NAME = "iem1chr_settings";
    private SharedPreferences prefs;
    private Context context;

    public SettingsManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Save a string setting
     */
    public void setSetting(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    /**
     * Get a string setting
     */
    public String getSetting(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    /**
     * Save a boolean setting
     */
    public void setBooleanSetting(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    /**
     * Get a boolean setting
     */
    public boolean getBooleanSetting(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    /**
     * Save an integer setting
     */
    public void setIntSetting(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    /**
     * Get an integer setting
     */
    public int getIntSetting(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    /**
     * Get all settings as a map
     */
    public Map<String, ?> getAllSettings() {
        return prefs.getAll();
    }

    /**
     * Clear all settings (use with caution!)
     */
    public void clearAll() {
        prefs.edit().clear().apply();
    }

    /**
     * Clear specific setting
     */
    public void clearSetting(String key) {
        prefs.edit().remove(key).apply();
    }

    /**
     * Backup settings to prevent loss on update
     */
    public Map<String, ?> backupSettings() {
        return new HashMap<>(getAllSettings());
    }

    /**
     * Restore settings from backup
     */
    public void restoreSettings(Map<String, ?> backup) {
        clearAll();
        SharedPreferences.Editor editor = prefs.edit();
        for (Map.Entry<String, ?> entry : backup.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                editor.putString(entry.getKey(), (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(entry.getKey(), (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(entry.getKey(), (Integer) value);
            } else if (value instanceof Long) {
                editor.putLong(entry.getKey(), (Long) value);
            } else if (value instanceof Float) {
                editor.putFloat(entry.getKey(), (Float) value);
            }
        }
        editor.apply();
    }
}
