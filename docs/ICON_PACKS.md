# Icon Pack System

## Overview
Load and manage multiple icon packs dynamically without app restart.

## Icon Pack Structure
Icon packs are stored as:
```
/data/local/tmp/icon_packs/
├── pack_name/
│   ├── manifest.json
│   ├── icons/
│   │   ├── app_name_1.png
│   │   ├── app_name_2.png
│   │   └── ...
│   └── preview.png
```

### manifest.json Example
```json
{
  "name": "Dark Minimal",
  "version": "1.0",
  "author": "jeropmelanie",
  "description": "Minimalist dark icon pack",
  "icons": {
    "com.example.app1": "app_name_1.png",
    "com.example.app2": "app_name_2.png"
  }
}
```

## IconPackManager.java
```java
package com.jeropmelanie.iem1chr.icons;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class IconPackManager {
    private Context context;
    private Map<String, String> currentPack = new HashMap<>();
    private File packDirectory;

    public IconPackManager(Context context) {
        this.context = context;
        this.packDirectory = new File("/data/local/tmp/icon_packs/");
    }

    public void loadIconPack(String packName) {
        File packPath = new File(packDirectory, packName);
        if (!packPath.exists()) return;

        // Parse manifest.json and load icon mappings
        File manifest = new File(packPath, "manifest.json");
        // Parse JSON and populate currentPack
    }

    public Drawable getIcon(String packageName) {
        String iconFile = currentPack.get(packageName);
        if (iconFile != null) {
            return Drawable.createFromPath(iconFile);
        }
        return null; // Fallback to system icon
    }

    public String[] getAvailablePacks() {
        File[] packs = packDirectory.listFiles();
        if (packs == null) return new String[0];
        
        String[] names = new String[packs.length];
        for (int i = 0; i < packs.length; i++) {
            names[i] = packs[i].getName();
        }
        return names;
    }
}
```

## Usage
```java
IconPackManager iconManager = new IconPackManager(this);
iconManager.loadIconPack("dark_minimal");
Drawable icon = iconManager.getIcon("com.example.app");
```