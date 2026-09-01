# Test Gestures

## Setup Emulator/Device

### Build & Deploy
```bash
cd iem1Chr-
./gradlew clean assembleDebug
./gradlew installDebug
```

### Verify Installation
```bash
adb shell pm list packages | grep iem1chr
```

### Open App
```bash
adb shell am start -n com.jeropmelanie.iem1chr/com.jeropmelanie.iem1chr.ui.MainActivity
```

## Testing Each Gesture

### Swipe Left
- Touch screen and drag finger to the left rapidly
- Should show toast: "Swiped Left"
- Check logcat: `adb logcat | grep "Swipe Left"`

### Swipe Right
- Touch screen and drag finger to the right rapidly
- Should show toast: "Swiped Right"

### Swipe Up
- Touch screen and drag finger upward rapidly
- Should show toast: "Swiped Up"

### Swipe Down
- Touch screen and drag finger downward rapidly
- Should show toast: "Swiped Down"

### Double Tap
- Quickly tap screen twice on same spot
- Should show toast: "Double Tapped at X, Y"

### Long Press
- Press and hold on screen for ~500ms
- Should show toast: "Long Pressed at X, Y"

### Single Tap
- Tap screen once
- Should show toast: "Tapped at X, Y"

## View Logs in Real-Time
```bash
adb logcat | grep "GestureDetector"
```

## Debug Info
```bash
adb logcat
```

Filter for app:
```bash
adb logcat | grep "iem1chr"
```

## Troubleshooting

**Gestures not working?**
- Check if app is in foreground
- Make sure device screen is active
- Verify app has focus (not in split-screen mode)

**Toast not showing?**
- Check volume/notification settings
- Enable toasts in developer options

**App crashes?**
```bash
adb logcat | grep "FATAL\|Exception\|Error"
```
