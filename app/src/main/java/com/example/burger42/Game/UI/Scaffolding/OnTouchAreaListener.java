package com.example.burger42.Game.UI.Scaffolding;

import android.view.MotionEvent;

/**
 * OnTouchAreaListener marks an area of a {@link CustomView}.
 * When you click on this area, this onTouch method is triggered.
 */
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

    /**
     * set the relative bottom area boundary
     *
     * @param relativeBottom the relative bottom area boundary
     * @return this OnTouchAreaListener it self to use in a setup chain
     */
    public OnTouchAreaListener setRelativeBottom(float relativeBottom) {
        this.relativeBottom = relativeBottom;
        return this;
    }

    /**
     * set the relative left area boundary
     *
     * @param relativeLeft the relative left area boundary
     * @return this OnTouchAreaListener it self to use in a setup chain
     */
    public OnTouchAreaListener setRelativeLeft(float relativeLeft) {
        this.relativeLeft = relativeLeft;
        return this;
    }

    /**
     * set the relative right area boundary
     *
     * @param relativeRight the relative right area boundary
     * @return this OnTouchAreaListener it self to use in a setup chain
     */
    public OnTouchAreaListener setRelativeRight(float relativeRight) {
        this.relativeRight = relativeRight;
        return this;
    }

    /**
     * set the relative top area boundary
     *
     * @param relativeTop the relative top area boundary
     * @return this OnTouchAreaListener it self to use in a setup chain
     */
    public OnTouchAreaListener setRelativeTop(float relativeTop) {
        this.relativeTop = relativeTop;
        return this;
    }

    /**
     * Constructor, that used the whole surface area for the listener.
     * relativeTop: 1
     * relativeBottom: 0
     * relativeLeft: 0
     * relativeRight: 1
     */
    public OnTouchAreaListener() {
        this(1, 0, 0, 1);
    }

    /**
     * Constructor, with values, that limits the area.
     *
     * @param relativeTop    a value between 0(bottom) and 1(top)
     * @param relativeBottom a value between 0(bottom) and 1(top)
     * @param relativeLeft   a value between 0(left) and 1(right)
     * @param relativeRight  a value between 0(left) and 1(right)
     */
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

    /**
     * this methode checks if the cords are inside the area
     *
     * @param x the x cord, between 0 and 1
     * @param y the y cord, between 0 and 1
     * @return true if both cords are inside the area and else false
     */
    boolean isInArea(float x, float y) {
        return relativeTop > y && relativeBottom < y && relativeLeft < x && relativeRight > x;
    }

    /**
     * onTouch will be called if the onTouch methode of a {@link android.view.View} will be called
     * and the touch in the area happens.
     *
     * @param event contains all information of the touch
     * @return true if the onTouch has an influence of this methode else false
     */
    protected abstract boolean onTouch(MotionEvent event);
}
