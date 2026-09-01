# Create Custom Icon Packs

## Step 1: Create Pack Directory

```bash
mkdir -p /sdcard/iem1chr/icon_packs/my_dark_pack/icons
```

Or on your computer:
```bash
mkdir -p assets/icon_packs/my_dark_pack/icons
```

## Step 2: Create manifest.json

Create `icon_packs/my_dark_pack/manifest.json`:

```json
{
  "name": "My Dark Pack",
  "version": "1.0.0",
  "author": "jeropmelanie",
  "description": "Custom dark minimalist icon pack",
  "icons": {
    "com.google.android.apps.maps": "maps.png",
    "com.google.android.apps.messaging": "messaging.png",
    "com.android.gallery3d": "gallery.png",
    "com.android.settings": "settings.png",
    "com.google.android.gms": "gms.png",
    "com.android.vending": "playstore.png",
    "com.spotify.music": "spotify.png",
    "com.whatsapp": "whatsapp.png"
  }
}
```

## Step 3: Add Icon Images

### Using GIMP (Free)

1. Create 192x192px PNG images
2. Use dark colors or flat design
3. Save to `my_dark_pack/icons/` folder

Example file names:
- `maps.png`
- `messaging.png`
- `gallery.png`
- `settings.png`

### Using Online Tools

- **Pixlr** (pixlr.com) - Web-based editor
- **PikasoArt** (pikasoart.com) - Easy icon editor
- **Inkscape** (inkscape.org) - Vector graphics

### Free Icon Resources

- **Flaticon** (flaticon.com) - Download PNG icons
- **IconMonstr** (iconmonstr.com) - Minimalist icons
- **Material Design Icons** (fonts.google.com/icons) - Official Google icons

## Step 4: Push to Device

```bash
adb push assets/icon_packs/my_dark_pack /data/local/tmp/icon_packs/my_dark_pack
```

Or via adb shell:
```bash
adb shell mkdir -p /data/local/tmp/icon_packs/my_dark_pack/icons
adb push icon_packs/my_dark_pack/manifest.json /data/local/tmp/icon_packs/my_dark_pack/
adb push icon_packs/my_dark_pack/icons/* /data/local/tmp/icon_packs/my_dark_pack/icons/
```

## Step 5: Load Icon Pack in App

Add to `MainActivity.java`:

```java
private IconPackManager iconManager;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Load icon pack
    iconManager = new IconPackManager(this);
    boolean loaded = iconManager.loadIconPack("my_dark_pack");
    
    if (loaded) {
        Toast.makeText(this, "Icon pack loaded!", Toast.LENGTH_SHORT).show();
        Log.d("IconPack", "Currently using: " + iconManager.getCurrentPackName());
    }

    gestureHandler = new GestureDetectorHandler(this);
    gestureDetector = new GestureDetector(this, gestureHandler);
}
```

## Step 6: Use Icons in App

```java
// Get icon for an app
Drawable icon = iconManager.getIcon("com.google.android.apps.maps");

// Display in ImageView
ImageView appIcon = findViewById(R.id.app_icon);
appIcon.setImageDrawable(icon);
```

## Advanced: Multiple Icon Packs

### Switch Between Packs

```java
String[] availablePacks = iconManager.getAvailablePacks();
for (String pack : availablePacks) {
    Log.d("Packs", pack);
}

// Load different pack
iconManager.loadIconPack("another_pack_name");
```

### Save Active Pack

```java
SettingsManager settings = new SettingsManager(this);
settings.setSetting("active_icon_pack", "my_dark_pack");
```

### Load on App Startup

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SettingsManager settings = new SettingsManager(this);
    String activePack = settings.getSetting("active_icon_pack", "default");
    
    iconManager = new IconPackManager(this);
    iconManager.loadIconPack(activePack);
}
```

## Troubleshooting

**Icon pack not loading?**
- Check manifest.json syntax (use JSON validator)
- Verify file paths in manifest match actual filenames
- Check logcat: `adb logcat | grep IconPackManager`

**Icons not showing?**
- Ensure PNG files are in correct folder
- Verify package names in manifest are correct
- Check file permissions: `adb shell ls -l /data/local/tmp/icon_packs/`

**Wrong pack loaded?**
- Clear app data: `adb shell pm clear com.jeropmelanie.iem1chr`
- Reinstall app: `./gradlew installDebug`
