package com.jeropmelanie.iem1chr.gestures;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.util.Log;

public class GestureDetectorHandler extends SimpleOnGestureListener {
    private static final String TAG = "GestureDetector";
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private GestureListener listener;

    public interface GestureListener {
        void onSwipeLeft();
        void onSwipeRight();
        void onSwipeUp();
        void onSwipeDown();
        void onDoubleTap(float x, float y);
        void onLongPress(float x, float y);
        void onSingleTap(float x, float y);
    }

    public GestureDetectorHandler(GestureListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1 == null || e2 == null) return false;

        float diffX = e2.getX() - e1.getX();
        float diffY = e2.getY() - e1.getY();

        // Detect horizontal swipes
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    Log.d(TAG, "Swipe Right");
                    listener.onSwipeRight();
                } else {
                    Log.d(TAG, "Swipe Left");
                    listener.onSwipeLeft();
                }
                return true;
            }
        }
        // Detect vertical swipes
        else {
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    Log.d(TAG, "Swipe Down");
                    listener.onSwipeDown();
                } else {
                    Log.d(TAG, "Swipe Up");
                    listener.onSwipeUp();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "Double Tap at (" + e.getX() + ", " + e.getY() + ")");
        listener.onDoubleTap(e.getX(), e.getY());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "Long Press at (" + e.getX() + ", " + e.getY() + ")");
        listener.onLongPress(e.getX(), e.getY());
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "Single Tap at (" + e.getX() + ", " + e.getY() + ")");
        listener.onSingleTap(e.getX(), e.getY());
        return true;
    }
}
