package com.example.burger42.Game.UI.Scaffolding;

import android.view.DragEvent;

/**
 * OnDragAreaListener marks an area of a {@link CustomView}.
 * When you drag something on this area, this onDrag method is triggered.
 */
public abstract class OnDragAreaListener extends BasicDragFilter {
    private boolean useFilter = false;
    private float relativeTop;
    private float relativeBottom;
    private float relativeLeft;
    private float relativeRight;

    /**
     * set the relative bottom area boundary
     *
     * @param relativeBottom the relative bottom area boundary
     * @return this OnDragAreaListener it self to use in a setup chain
     */
    public OnDragAreaListener setRelativeBottom(float relativeBottom) {
        this.relativeBottom = relativeBottom;
        return this;
    }

    /**
     * set the relative left area boundary
     *
     * @param relativeLeft the relative left area boundary
     * @return this OnDragAreaListener it self to use in a setup chain
     */
    public OnDragAreaListener setRelativeLeft(float relativeLeft) {
        this.relativeLeft = relativeLeft;
        return this;
    }

    /**
     * set the relative right area boundary
     *
     * @param relativeRight the relative right area boundary
     * @return this OnDragAreaListener it self to use in a setup chain
     */
    public OnDragAreaListener setRelativeRight(float relativeRight) {
        this.relativeRight = relativeRight;
        return this;
    }

    /**
     * set the relative top area boundary
     *
     * @param relativeTop the relative top area boundary
     * @return this OnDragAreaListener it self to use in a setup chain
     */
    public OnDragAreaListener setRelativeTop(float relativeTop) {
        this.relativeTop = relativeTop;
        return this;
    }

    /**
     * setUseFilter can enable the use of DragFilter.
     * If the DragFilter is enabled onDrag will only be triggered from dragged items,
     * that has at least one same filter tag.
     *
     * @param useFilter the value to set useFilter
     * @return this OnDragAreaListener it self to use in a setup chain
     */
    public OnDragAreaListener setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
        return this;
    }

    /**
     * addFilterTag adds a filter tag to the DragFilter from this OnDragAreaListener
     *
     * @param tag the filter tag to add
     * @return this OnDragAreaListener it self to use in a setup chain
     */
    public OnDragAreaListener addFilterTag(ItemView.ItemFilterTag tag) {
        super.addFilterTag(tag.name());
        return this;
    }

    /**
     * addFilterTag adds a filter tag to the DragFilter from this OnDragAreaListener
     *
     * @param tag the filter tag to add
     * @return this OnDragAreaListener it self to use in a setup chain
     */
    @Override
    public OnDragAreaListener addFilterTag(String tag) {
        super.addFilterTag(tag);
        return this;
    }

    /**
     * @return useFilter returns true if this OnDragAreaListener uses a DragFilter else false
     */
    public boolean useFilter() {
        return useFilter;
    }

    protected float relativeBottom() {
        return relativeBottom;
    }

    protected float relativeLeft() {
        return relativeLeft;
    }

    protected float relativeRight() {
        return relativeRight;
    }

    protected float relativeTop() {
        return relativeTop;
    }

    /**
     * Constructor, that used the whole surface area for the listener.
     * relativeTop: 1
     * relativeBottom: 0
     * relativeLeft: 0
     * relativeRight: 1
     */
    public OnDragAreaListener() {
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
    public OnDragAreaListener(float relativeTop, float relativeBottom, float relativeLeft, float relativeRight) {
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
     * onTouch will be called if the onDrag methode of a {@link android.view.View} will be called.
     *
     * @param event  contains all information of the dragEvent
     * @param inArea true when the dragged item is inside the area else false.
     * @return true if the onDrag has an influence of this methode else false
     */
    protected abstract boolean onDrag(DragEvent event, boolean inArea);

}
