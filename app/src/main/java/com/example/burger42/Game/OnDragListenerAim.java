package com.example.burger42.Game;

import android.view.DragEvent;
import android.view.View;

public class OnDragListenerAim implements View.OnDragListener {

    private DragFilter dragFilter;
    private float xOffset, yOffset;
    private boolean center;

    public DragFilter dragFilter() {
        return dragFilter;
    }

    public OnDragListenerAim() {
        this(0, 0);
    }

    public OnDragListenerAim(float xOffset, float yOffset) {
        this(xOffset, yOffset, false);
    }

    public OnDragListenerAim(float xOffset, float yOffset, boolean center) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.center = center;
        dragFilter = new DragFilter();
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        OnTouchListenerDragable onTouchListenerDragable = (OnTouchListenerDragable) dragEvent.getLocalState();
        View dragView = onTouchListenerDragable.view();
        if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
            dragView.setVisibility(View.VISIBLE);
            dragView.bringToFront();
        }
        if (dragEvent.getAction() == DragEvent.ACTION_DROP && dragFilter.hasAtLeastOneEqualFilterTag(onTouchListenerDragable.dragFilter())) {
            dragView.setTranslationX(view.getTranslationX() + view.getWidth() * xOffset - (center ? dragView.getWidth() / 2.0f : 0));
            dragView.setTranslationY(view.getTranslationY() + view.getHeight() * yOffset - (center ? dragView.getHeight() / 2.0f : 0));
        }
        return true;
    }
}
