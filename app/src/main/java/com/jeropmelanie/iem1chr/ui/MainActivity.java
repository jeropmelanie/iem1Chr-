package com.jeropmelanie.iem1chr.ui;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.jeropmelanie.iem1chr.R;
import com.jeropmelanie.iem1chr.gestures.GestureDetectorHandler;
import com.jeropmelanie.iem1chr.icons.IconPackManager;
import com.jeropmelanie.iem1chr.core.SettingsManager;
import com.jeropmelanie.iem1chr.updates.UpdateChecker;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements GestureDetectorHandler.GestureListener {
    private static final String TAG = "MainActivity";
    private GestureDetector gestureDetector;
    private GestureDetectorHandler gestureHandler;
    private IconPackManager iconManager;
    private SettingsManager settingsManager;
    private UpdateChecker updateChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize managers
        settingsManager = new SettingsManager(this);
        iconManager = new IconPackManager(this);
        updateChecker = new UpdateChecker(this);

        // Load saved settings
        String activePack = settingsManager.getSetting("active_icon_pack", "default");
        iconManager.loadIconPack(activePack);

        // Setup gesture detection
        gestureHandler = new GestureDetectorHandler(this);
        gestureDetector = new GestureDetector(this, gestureHandler);

        // Check for updates
        checkForUpdates();

        Log.d(TAG, "MainActivity initialized");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public void onSwipeLeft() {
        showToast("Opening App Drawer");
        Intent intent = new Intent(this, AppDrawerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSwipeRight() {
        showToast("Swiped Right - Back to Home");
        // Already on home screen
    }

    @Override
    public void onSwipeUp() {
        showToast("Swiped Up - Notifications");
        // Open notifications (placeholder)
    }

    @Override
    public void onSwipeDown() {
        showToast("Opening Settings");
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDoubleTap(float x, float y) {
        showToast("Double Tapped - Lock Screen");
        lockScreen();
    }

    @Override
    public void onLongPress(float x, float y) {
        showToast("Long Press - Widget Menu");
        showWidgetMenu();
    }

    @Override
    public void onSingleTap(float x, float y) {
        showToast("Tapped at " + String.format("%.0f", x) + ", " + String.format("%.0f", y));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d(TAG, message);
    }

    private void lockScreen() {
        try {
            Runtime.getRuntime().exec("input keyevent 26"); // KEYCODE_POWER
        } catch (Exception e) {
            Log.e(TAG, "Error locking screen", e);
        }
    }

    private void showWidgetMenu() {
        // Placeholder for widget menu
        showToast("Widget menu would appear here");
    }

    private void checkForUpdates() {
        updateChecker.checkForUpdates(new UpdateChecker.UpdateListener() {
            @Override
            public void onUpdateAvailable(String version) {
                Log.d(TAG, "Update available: " + version);
                showToast("Update available: " + version);
                // In production, show download dialog
            }

            @Override
            public void onUpToDate() {
                Log.d(TAG, "App is up to date");
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Update check error", e);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "App resumed - checking for updates");
        checkForUpdates();
    }
}
