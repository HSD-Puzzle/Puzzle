package com.example.burger42.Game.UI.Scaffolding;

import android.view.DragEvent;
import android.view.MotionEvent;

public abstract class OnTouchAreaListener {
    private float relativeTop;
    private float relativeBottom;
    private float relativeLeft;
    private float relativeRight;

    float relativeBottom() {
        return relativeBottom;
    }

    float relativeLeft() {
        return relativeLeft;
    }

    float relativeRight() {
        return relativeRight;
    }

    float relativeTop() {
        return relativeTop;
    }

    public OnTouchAreaListener setRelativeBottom(float relativeBottom) {
        this.relativeBottom = relativeBottom;
        return this;
    }

    public OnTouchAreaListener setRelativeLeft(float relativeLeft) {
        this.relativeLeft = relativeLeft;
        return this;
    }

    public OnTouchAreaListener setRelativeRight(float relativeRight) {
        this.relativeRight = relativeRight;
        return this;
    }

    public OnTouchAreaListener setRelativeTop(float relativeTop) {
        this.relativeTop = relativeTop;
        return this;
    }

    public OnTouchAreaListener() {
        this(1, 0, 0, 1);
    }

    public OnTouchAreaListener(float relativeTop, float relativeBottom, float relativeLeft, float relativeRight) {
        this.relativeBottom = relativeBottom;
        this.relativeLeft = relativeLeft;
        this.relativeTop = relativeTop;
        this.relativeRight = relativeRight;
        if (relativeTop <= relativeBottom)
            throw new ValueNotValid("relativeTop has to be smaller than relativeBottom");
        if (relativeLeft >= relativeRight)
            throw new ValueNotValid("relativeLeft has to be smaller than relativeRight");
    }

    boolean isInArea(float x, float y) {
        return relativeTop > y && relativeBottom < y && relativeLeft < x && relativeRight > x;
    }

    protected abstract boolean onTouch(MotionEvent event);

}
