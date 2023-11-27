package com.example.burger42.Game;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

public class OnTouchListenerDragable implements View.OnTouchListener {

    private DragFilter dragFilter;

    private final View view;

    public DragFilter dragFilter() {
        return dragFilter;
    }

    public OnTouchListenerDragable(View view) {
        this.view = view;
        dragFilter = new DragFilter();
    }

    public View view() {
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(data, shadowBuilder, this, 1);
            } else {
                view.startDrag(data, shadowBuilder, this, 1);
            }
            view.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }
}
