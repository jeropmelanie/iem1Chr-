package com.jeropmelanie.iem1chr.ui;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.jeropmelanie.iem1chr.R;
import com.jeropmelanie.iem1chr.gestures.GestureDetectorHandler;

public class MainActivity extends AppCompatActivity implements GestureDetectorHandler.GestureListener {
    private GestureDetector gestureDetector;
    private GestureDetectorHandler gestureHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureHandler = new GestureDetectorHandler(this);
        gestureDetector = new GestureDetector(this, gestureHandler);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public void onSwipeLeft() {
        showToast("Swiped Left");
        // Open app drawer or next screen
    }

    @Override
    public void onSwipeRight() {
        showToast("Swiped Right");
        // Previous screen or quick settings
    }

    @Override
    public void onSwipeUp() {
        showToast("Swiped Up");
        // Open notifications or app menu
    }

    @Override
    public void onSwipeDown() {
        showToast("Swiped Down");
        // Open settings or status
    }

    @Override
    public void onDoubleTap(float x, float y) {
        showToast("Double Tapped at " + x + ", " + y);
        // Lock screen or custom action
    }

    @Override
    public void onLongPress(float x, float y) {
        showToast("Long Pressed at " + x + ", " + y);
        // Open widget selector or edit mode
    }

    @Override
    public void onSingleTap(float x, float y) {
        showToast("Tapped at " + x + ", " + y);
        // Launch app or open widget
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
