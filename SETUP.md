# Setup Guide - IEM1Chr Launcher

## Prerequisites
- Android Studio (latest)
- Android SDK 26+
- Gradle 8.0+
- Git

## Initial Setup

### 1. Clone & Open Project
```bash
git clone https://github.com/jeropmelanie/iem1Chr-.git
cd iem1Chr-
```

### 2. Create Android Project Structure
Run this to scaffold the Android project:
```bash
mkdir -p app/src/main/java/com/jeropmelanie/iem1chr/{core,gestures,icons,updates,ui}
mkdir -p app/src/main/res/{layout,drawable,values,menu}
mkdir -p app/src/test/java/com/jeropmelanie/iem1chr
mkdir -p app/src/androidTest/java/com/jeropmelanie/iem1chr
```

### 3. Build Files
Create `build.gradle` (root):
```gradle
plugins {
    id 'com.android.application' version '8.1.0' apply false
}

androidComponents {
    beforeVariants(selector().all()) { variant ->
    }
}
```

Create `app/build.gradle`:
```gradle
plugins {
    id 'com.android.application'
}

android {
    namespace 'com.jeropmelanie.iem1chr'
    compileSdk 34

    defaultConfig {
        applicationId "com.jeropmelanie.iem1chr"
        minSdk 26
        targetSdk 34
        versionCode 1
        versionName "1.0.0"
    }

    buildTypes {
        release {
            minifyEnabled false
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.android.material:material:1.10.0'
}
```

### 4. Android Manifest
Create `app/src/main/AndroidManifest.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.AppCompat.Light.DarkActionBar">

        <activity
            android:name=".ui.MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>

</manifest>
```

### 5. Build & Run
```bash
./gradlew assembleDebug
./gradlew installDebug
```

## Next Steps
1. Start with **Gesture System** - see `docs/GESTURES.md`
2. Build **Icon Pack Loader** - see `docs/ICON_PACKS.md`
3. Implement **Update System** - see `docs/UPDATES.md`