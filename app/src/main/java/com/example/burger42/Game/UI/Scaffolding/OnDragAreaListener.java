package com.example.burger42.Game.UI.Scaffolding;

import android.view.DragEvent;

import com.example.burger42.Game.BasicDragFilter;
import com.example.burger42.Game.DragFilter;

public abstract class OnDragAreaListener extends BasicDragFilter {

    private boolean useFilter = false;
    private float relativeTop;
    private float relativeBottom;
    private float relativeLeft;
    private float relativeRight;

    public OnDragAreaListener setRelativeBottom(float relativeBottom) {
        this.relativeBottom = relativeBottom;
        return this;
    }

    public OnDragAreaListener setRelativeLeft(float relativeLeft) {
        this.relativeLeft = relativeLeft;
        return this;
    }

    public OnDragAreaListener setRelativeRight(float relativeRight) {
        this.relativeRight = relativeRight;
        return this;
    }

    public OnDragAreaListener setRelativeTop(float relativeTop) {
        this.relativeTop = relativeTop;
        return this;
    }

    public OnDragAreaListener setUseFilter(boolean useFilter) {
        this.useFilter = useFilter;
        return this;
    }

    public OnDragAreaListener addFilterTag(ItemView.ItemFilterTag tag) {
        super.addFilterTag(tag.name());
        return this;
    }

    public OnDragAreaListener addFilterTagR(String tag) {
        super.addFilterTag(tag);
        return this;
    }

    public boolean useFilter() {
        return useFilter;
    }

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

    public OnDragAreaListener() {
        this(1, 0, 0, 1);
    }

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

    boolean isInArea(float x, float y) {
        return relativeTop > y && relativeBottom < y && relativeLeft < x && relativeRight > x;
    }

    protected abstract boolean onDrag(DragEvent event, boolean inArea);

}
