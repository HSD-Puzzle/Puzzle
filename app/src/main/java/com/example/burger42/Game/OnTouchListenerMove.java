package com.example.burger42.Game;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class OnTouchListenerMove implements View.OnTouchListener {
    int _xDelta = 0;
    int _yDelta = 0;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        System.out.println(event.getPointerCount() + " touches detected");

        System.out.println(event.getAction());

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("X: " + X + " Y: " + Y);
                break;
            case MotionEvent.ACTION_MOVE:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                view.setLayoutParams(layoutParams);
                break;
        }
        return true;
    }
}
