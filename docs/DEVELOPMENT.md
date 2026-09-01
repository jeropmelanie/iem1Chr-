# Development Guide

## Quick Build & Run

### Build Debug APK
```bash
./gradlew assembleDebug
```

### Install on Device
```bash
./gradlew installDebug
```

### Run Tests
```bash
./gradlew test
```

### View Logs
```bash
adb logcat | grep "GestureDetector\|IconPackManager\|UpdateChecker"
```

## Adding a New Gesture

1. Add method to `GestureDetectorHandler.GestureListener`:
```java
void onCustomGesture(float x, float y);
```

2. Implement in `MainActivity`:
```java
@Override
public void onCustomGesture(float x, float y) {
    // Your action here
}
```

## Creating an Icon Pack

1. Create folder structure:
```bash
mkdir -p icon_packs/my_pack/icons
```

2. Create `manifest.json`:
```json
{
  "name": "My Pack",
  "version": "1.0",
  "icons": {
    "com.example.app": "app_icon.png"
  }
}
```

3. Add PNG files to `icons/` folder

4. Load with:
```java
IconPackManager manager = new IconPackManager(this);
manager.loadIconPack("my_pack");
```

## Testing Updates

1. Update version in `releases/manifest.json`
2. Push to GitHub
3. Call `UpdateChecker.checkForUpdates()` in your app
4. Settings persist automatically
