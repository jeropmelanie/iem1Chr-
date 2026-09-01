package com.jeropmelanie.iem1chr.icons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class IconPackManager {
    private static final String TAG = "IconPackManager";
    private Context context;
    private Map<String, String> currentPack = new HashMap<>();
    private File packDirectory;
    private String currentPackName = "";

    public IconPackManager(Context context) {
        this.context = context;
        this.packDirectory = new File(context.getFilesDir(), "icon_packs");
        if (!packDirectory.exists()) {
            packDirectory.mkdirs();
        }
    }

    /**
     * Load an icon pack from the directory
     */
    public boolean loadIconPack(String packName) {
        File packPath = new File(packDirectory, packName);
        if (!packPath.exists()) {
            Log.e(TAG, "Pack directory not found: " + packPath.getAbsolutePath());
            return false;
        }

        File manifestFile = new File(packPath, "manifest.json");
        if (!manifestFile.exists()) {
            Log.e(TAG, "manifest.json not found in " + packName);
            return false;
        }

        try {
            String jsonString = readFile(manifestFile);
            JSONObject manifest = new JSONObject(jsonString);
            JSONObject icons = manifest.getJSONObject("icons");

            currentPack.clear();
            for (int i = 0; i < icons.names().length(); i++) {
                String packageName = icons.names().getString(i);
                String iconFile = icons.getString(packageName);
                currentPack.put(packageName, iconFile);
            }

            currentPackName = packName;
            Log.d(TAG, "Loaded icon pack: " + packName + " with " + currentPack.size() + " icons");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error loading icon pack", e);
            return false;
        }
    }

    /**
     * Get icon for a specific app package
     */
    public Drawable getIcon(String packageName) {
        String iconFile = currentPack.get(packageName);
        if (iconFile == null) {
            Log.w(TAG, "Icon not found for: " + packageName);
            return null;
        }

        File iconPath = new File(packDirectory, currentPackName + File.separator + "icons" + File.separator + iconFile);
        if (!iconPath.exists()) {
            Log.w(TAG, "Icon file not found: " + iconPath.getAbsolutePath());
            return null;
        }

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(iconPath.getAbsolutePath());
            if (bitmap != null) {
                return new BitmapDrawable(context.getResources(), bitmap);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading icon bitmap", e);
        }
        return null;
    }

    /**
     * Get all available icon packs
     */
    public String[] getAvailablePacks() {
        File[] packs = packDirectory.listFiles(File::isDirectory);
        if (packs == null || packs.length == 0) {
            Log.w(TAG, "No icon packs found");
            return new String[0];
        }

        String[] names = new String[packs.length];
        for (int i = 0; i < packs.length; i++) {
            names[i] = packs[i].getName();
        }
        return names;
    }

    /**
     * Get currently loaded pack name
     */
    public String getCurrentPackName() {
        return currentPackName;
    }

    /**
     * Helper method to read file as string
     */
    private String readFile(File file) throws Exception {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }
}
