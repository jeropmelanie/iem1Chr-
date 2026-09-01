# Build & Release Updates

## Step 1: Prepare Release

### Update Version Numbers

Edit `app/build.gradle`:
```gradle
defaultConfig {
    versionCode 2          # Increment this
    versionName "1.1.0"    # Update this
}
```

Edit `releases/manifest.json`:
```json
{
  "version": "1.1.0",
  "versionCode": 2,
  "releaseDate": "2026-09-02",
  "changelog": "Added new gesture actions and improved icon pack loading",
  "downloadUrl": "https://github.com/jeropmelanie/iem1Chr-/releases/download/v1.1.0/iem1chr-v1.1.0.apk"
}
```

## Step 2: Build Release APK

```bash
cd iem1Chr-
./gradlew clean
./gradlew assembleRelease
```

Find APK at:
```
app/build/outputs/apk/release/app-release.apk
```

### Sign APK (for real distribution)

```bash
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
  -keystore my-release-key.jks \
  app/build/outputs/apk/release/app-release.apk \
  my-key-alias
```

## Step 3: Create GitHub Release

### Method 1: Using Git Commands

```bash
# Tag the release
git tag -a v1.1.0 -m "Release version 1.1.0"

# Push tag to GitHub
git push origin v1.1.0
```

### Method 2: Via GitHub Web UI

1. Go to: https://github.com/jeropmelanie/iem1Chr-/releases/new
2. Click "Draft a new release"
3. Fill in:
   - Tag: `v1.1.0`
   - Title: `IEM1Chr v1.1.0`
   - Description: Paste changelog
4. Upload `app-release.apk`
5. Click "Publish release"

## Step 4: Update Manifest

Edit `releases/manifest.json` with release URL:

```json
{
  "version": "1.1.0",
  "versionCode": 2,
  "releaseDate": "2026-09-02",
  "changelog": "Added new gesture actions and improved icon pack loading",
  "downloadUrl": "https://github.com/jeropmelanie/iem1Chr-/releases/download/v1.1.0/app-release.apk",
  "features": {
    "gestures": {
      "version": "1.1",
      "description": "Enhanced gesture detection and customization"
    },
    "icons": {
      "version": "1.1",
      "description": "Improved icon pack loading performance"
    },
    "updates": {
      "version": "1.0",
      "description": "Repository-based auto-updates"
    }
  }
}
```

Push to repo:
```bash
git add releases/manifest.json
git commit -m "Update manifest for v1.1.0"
git push origin main
```

## Step 5: Test Update Check

In your app, call update checker:

```java
UpdateChecker updateChecker = new UpdateChecker(this);
updateChecker.checkForUpdates(new UpdateChecker.UpdateListener() {
    @Override
    public void onUpdateAvailable(String version) {
        Toast.makeText(MainActivity.this, 
            "Update available: " + version, 
            Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpToDate() {
        Toast.makeText(MainActivity.this, 
            "App is up to date", 
            Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(MainActivity.this, 
            "Error: " + e.getMessage(), 
            Toast.LENGTH_SHORT).show();
    }
});
```

## Step 6: Users Update Automatically

Users will see:
```
"Update available: 1.1.0"
↓
[Download] [Later]
```

All user settings persist! ✅

## Troubleshooting

**APK not building?**
```bash
./gradlew clean
./gradlew assembleRelease --info
```

**Update not detected?**
- Check manifest.json is at: `https://raw.githubusercontent.com/jeropmelanie/iem1Chr-/main/releases/manifest.json`
- Verify JSON syntax: use jsonlint.com
- Check app version vs manifest version

**Users can't download APK?**
- Verify GitHub release exists
- Download link must be public
- Check file size (APK should be < 50MB)
