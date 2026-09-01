# Architecture Guide

## Project Structure

```
app/
├── src/main/java/com/jeropmelanie/iem1chr/
│   ├── core/
│   │   └── SettingsManager.java          # Persistent settings & backups
│   ├── gestures/
│   │   └── GestureDetector.java          # Touch event handling
│   ├── icons/
│   │   └── IconPackManager.java          # Load & manage icon packs
│   ├── updates/
│   │   └── UpdateChecker.java            # Check GitHub for updates
│   └── ui/
│       ├── MainActivity.java              # Home screen
│       ├── AppDrawerActivity.java         # App launcher grid
│       └── SettingsActivity.java          # User preferences
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── activity_app_drawer.xml
│   │   └── activity_settings.xml
│   └── values/
│       ├── strings.xml
│       └── styles.xml
└── AndroidManifest.xml
```

## How Updates Work Without Losing Settings

### Before Update:
1. `SettingsManager.backupSettings()` saves all user preferences to a HashMap
2. Saved to a backup file or temporary storage

### Update Process:
3. New app files are downloaded
4. App is reinstalled
5. Old settings are preserved in SharedPreferences

### After Update:
6. App loads using the same SharedPreferences database
7. User settings remain intact

## Gesture System

- **GestureDetector** extends `SimpleOnGestureListener`
- Detects: swipes (4 directions), double-tap, long-press, single-tap
- Each gesture triggers a callback in `MainActivity`
- Configurable thresholds for sensitivity

## Icon Packs

- Icon packs are JSON + PNG folders
- `IconPackManager` loads manifests and maps apps to icons
- Multiple packs supported, only one active at a time
- Easy to add new packs without app update

## Auto-Updates

- `UpdateChecker` polls GitHub for `releases/manifest.json`
- Compares semantic versioning (1.0.0 format)
- Downloads APK from GitHub releases
- Preserves all user settings via SharedPreferences
