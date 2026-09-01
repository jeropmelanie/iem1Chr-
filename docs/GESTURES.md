# Gesture System Architecture

## Overview
Gesture detection for home screen interactions: swipes, double-taps, long-presses.

## Core Components

### GestureDetector.java
```java
package com.jeropmelanie.iem1chr.gestures;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GestureDetector extends SimpleOnGestureListener {
    private GestureListener listener;

    public interface GestureListener {
        void onSwipeLeft();
        void onSwipeRight();
        void onSwipeUp();
        void onSwipeDown();
        void onDoubleTap(float x, float y);
        void onLongPress(float x, float y);
    }

    public GestureDetector(GestureListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float diffX = e2.getX() - e1.getX();
        float diffY = e2.getY() - e1.getY();

        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX > 0) {
                listener.onSwipeRight();
            } else {
                listener.onSwipeLeft();
            }
        } else {
            if (diffY > 0) {
                listener.onSwipeDown();
            } else {
                listener.onSwipeUp();
            }
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        listener.onDoubleTap(e.getX(), e.getY());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        listener.onLongPress(e.getX(), e.getY());
    }
}
```

## Usage in MainActivity
```java
public class MainActivity extends AppCompatActivity implements GestureDetector.GestureListener {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onSwipeLeft() {
        // Handle swipe left
    }

    @Override
    public void onSwipeRight() {
        // Handle swipe right
    }

    // ... implement other gestures
}
```

## Configuration
Gestures can be customized via SharedPreferences:
```java
SharedPreferences prefs = getSharedPreferences("gestures", MODE_PRIVATE);
prefs.edit().putString("swipe_left_action", "open_app_drawer").apply();
```