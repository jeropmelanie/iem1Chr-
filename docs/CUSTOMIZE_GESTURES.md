# Customize Gesture Actions

## Understanding Gesture Flow

When a gesture is detected:
1. `GestureDetectorHandler` detects the touch event
2. Calls the appropriate method in `MainActivity`
3. Your code in `MainActivity` performs the action

## Map Gestures to Actions

### Swipe Left → Open App Drawer

Edit `MainActivity.java`:

```java
@Override
public void onSwipeLeft() {
    Toast.makeText(this, "Opening App Drawer", Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(this, AppDrawerActivity.class);
    startActivity(intent);
}
```

### Swipe Right → Back to Home

```java
@Override
public void onSwipeRight() {
    Toast.makeText(this, "Home Screen", Toast.LENGTH_SHORT).show();
    // Already on home, optionally close any overlays
}
```

### Swipe Up → Open Notifications

```java
@Override
public void onSwipeUp() {
    Toast.makeText(this, "Opening Notifications", Toast.LENGTH_SHORT).show();
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
}
```

### Swipe Down → Settings

```java
@Override
public void onSwipeDown() {
    Toast.makeText(this, "Opening Settings", Toast.LENGTH_SHORT).show();
    Intent intent = new Intent(this, SettingsActivity.class);
    startActivity(intent);
}
```

### Double Tap → Lock Screen

```java
@Override
public void onDoubleTap(float x, float y) {
    Toast.makeText(this, "Locking Screen", Toast.LENGTH_SHORT).show();
    
    // Requires LOCK_DEVICE permission
    try {
        Runtime.getRuntime().exec("input keyevent " + KeyEvent.KEYCODE_POWER);
    } catch (Exception e) {
        Log.e("MainActivity", "Error locking screen", e);
    }
}
```

### Long Press → Widget Menu

```java
@Override
public void onLongPress(float x, float y) {
    Toast.makeText(this, "Widget Menu at " + x + ", " + y, Toast.LENGTH_SHORT).show();
    // Show widget selection dialog
    showWidgetMenu(x, y);
}

private void showWidgetMenu(float x, float y) {
    // Create and show widget selection menu
    Toast.makeText(this, "Widget menu would appear here", Toast.LENGTH_SHORT).show();
}
```

### Single Tap → Launch Tapped App

```java
@Override
public void onSingleTap(float x, float y) {
    Toast.makeText(this, "Tapped: " + x + ", " + y, Toast.LENGTH_SHORT).show();
    // Detect which app icon was tapped and launch it
}
```

## Launch Specific Apps

### Generic App Launcher

```java
private void launchApp(String packageName) {
    try {
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "App not installed", Toast.LENGTH_SHORT).show();
        }
    } catch (Exception e) {
        Log.e("MainActivity", "Error launching app", e);
    }
}
```

### Example Gesture → Launch Maps

```java
@Override
public void onDoubleTap(float x, float y) {
    launchApp("com.google.android.apps.maps");
}
```

## Configurable Gesture Actions

Add to `SettingsManager.java` for user customization:

```java
// Save gesture mappings
SettingsManager settings = new SettingsManager(this);
settings.setSetting("swipe_left_action", "app_drawer");
settings.setSetting("swipe_right_action", "home");
settings.setSetting("swipe_up_action", "notifications");
settings.setSetting("swipe_down_action", "settings");
```

Then in `MainActivity`:

```java
private void executeGestureAction(String actionName) {
    switch (actionName) {
        case "app_drawer":
            startActivity(new Intent(this, AppDrawerActivity.class));
            break;
        case "home":
            // Already home
            break;
        case "notifications":
            // Open notifications
            break;
        case "settings":
            startActivity(new Intent(this, SettingsActivity.class));
            break;
        default:
            Toast.makeText(this, "Unknown action", Toast.LENGTH_SHORT).show();
    }
}

@Override
public void onSwipeLeft() {
    SettingsManager settings = new SettingsManager(this);
    String action = settings.getSetting("swipe_left_action", "app_drawer");
    executeGestureAction(action);
}
```

## Quick Copy-Paste Template

Update all gestures at once:

```java
@Override
public void onSwipeLeft() {
    // Your action here
}

@Override
public void onSwipeRight() {
    // Your action here
}

@Override
public void onSwipeUp() {
    // Your action here
}

@Override
public void onSwipeDown() {
    // Your action here
}

@Override
public void onDoubleTap(float x, float y) {
    // Your action here
}

@Override
public void onLongPress(float x, float y) {
    // Your action here
}

@Override
public void onSingleTap(float x, float y) {
    // Your action here
}
```

## Rebuild & Test

```bash
./gradlew assembleDebug
./gradlew installDebug
```

Then test your custom gestures on device! 🎯
