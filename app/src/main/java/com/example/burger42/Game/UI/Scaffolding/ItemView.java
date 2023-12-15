package com.example.burger42.Game.UI.Scaffolding;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Game.BasicDragFilter;
import com.example.burger42.Game.DragFilter;

import java.util.Iterator;

public abstract class ItemView extends CustomView implements DragFilter {

    public enum ItemFilterTag {
        Ingredient, CleanBase
    }

    protected abstract ItemFilterTag[] itemFilterTags();

    protected DragFilter dragFilter;

    public void addFilterTag(String tag) {
        dragFilter.addFilterTag(tag);
    }

    public void removeFilterTag(String tag) {
        dragFilter.removeFilterTag(tag);
    }

    public boolean hasAtLeastOneEqualFilterTag(DragFilter other) {
        return dragFilter.hasAtLeastOneEqualFilterTag(other);
    }

    @NonNull
    @Override
    public Iterator<String> iterator() {
        return dragFilter.iterator();
    }

    private ItemView parent = null;

    public void removeFromParent() {
        if (parent != null) {
            parent.removeItemAbove(this);
            parent = null;
        }
        if (restaurantFragment != null) {
            restaurantFragment.removeItem(this);
            restaurantFragment = null;
        }
    }

    private RestaurantFragment restaurantFragment;

    private ItemAboveNode[] itemsAboveNode;

    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRestaurantFragment(RestaurantFragment restaurantFragment) {
        this.restaurantFragment = restaurantFragment;
    }

    @Override
    protected final void init(Context context, @Nullable AttributeSet attrs) {
        itemsAboveNode = itemAboveSetUp();
        dragFilter = new BasicDragFilter();
        addFilterTag(name());
        for (ItemFilterTag x : itemFilterTags()) {
            addFilterTag(x.name());
        }
        super.init(context, attrs);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        setTouchFallback(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                drag();
                return true;
            }
        });
    }

    public boolean drag() {
        return drag(this);
    }

    public boolean drag(ItemView item) {
        if (parent != null) return parent.drag(item);
        else return super.drag(item);
    }

    protected ItemAboveNode[] itemAboveSetUp() {
        return new ItemAboveNode[]{new ItemAboveNode(0, 0.1f) {
        }};
    }

    protected abstract int drawableId();

    @Override
    protected final int calculateHeight(int referenceHeight) {
        int maxHeight = super.calculateHeight(referenceHeight);
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.hasItemView())
                maxHeight = Math.max(x.yOffset(referenceHeight) + x.itemAbove().calculateHeight(referenceHeight), maxHeight);
        }
        return maxHeight;
    }

    @Override
    protected final int calculateWidth(int referenceHeight) {
        int right = super.calculateWidth(referenceHeight) / 2;
        int left = -right;
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.hasItemView()) {
                int cWidth = x.itemAbove().calculateWidth(referenceHeight) / 2;
                right = Math.max(x.xOffset(referenceHeight) + cWidth, right);
                left = Math.min(x.xOffset(referenceHeight) - cWidth, left);
            }
        }
        return right - left;
    }

    @Override
    protected final void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        super.drawItemOnCanvas(canvas, xOffset, yOffset, referenceHeight, width, height);
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.hasItemView())
                x.itemAbove.drawItemOnCanvas(canvas, x.xOffset(referenceHeight) + xOffset, x.yOffset(referenceHeight) + yOffset, referenceHeight, width, height);
        }
        if (isInEditMode()) {
            for (ItemAboveNode x : itemsAboveNode) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                canvas.drawRect(width / 2 + xOffset + x.xOffset(referenceHeight) - 10, height - yOffset - x.yOffset(referenceHeight) - 20, width / 2 + xOffset + x.xOffset(referenceHeight) + 10, height - yOffset - x.yOffset(referenceHeight), paint);
            }
        }
        if (isInEditMode())
            drawDragTouchAreas(canvas, xOffset, yOffset, referenceHeight, width, height);
    }

    public void removeItemAbove(ItemView itemAbove) {
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.itemAbove == itemAbove) {
                x.setItemAbove(null);
                redraw();
            }
        }
    }

    public void setItemAbove(int index, ItemView itemAbove) {
        if (!itemIsPartOfParentChain(itemAbove)) {
            itemAbove.removeFromParent();
            itemAbove.parent = this;
            itemsAboveNode[index].setItemAbove(itemAbove);
            redraw();
        }
    }

    public boolean itemIsPartOfParentChain(ItemView itemView) {
        return itemView == this || (parent != null && parent.itemIsPartOfParentChain(itemView));
    }

    protected void redraw() {
        if (parent != null) parent.redraw();
        else {
            setLayoutParams(new FrameLayout.LayoutParams(referenceHeight, referenceHeight));
        }
    }

    public boolean hasItemAbove(int index) {
        return itemsAboveNode[index].hasItemView();
    }

    @Override
    protected final void recalculateDimensions(int referenceHeight) {
        super.recalculateDimensions(referenceHeight);
        setTranslationY(translationY);
    }

    private float translationY = 0;

    @Override
    public final boolean onTouchArea(MotionEvent event, int xOffset, int yOffset, int width, int height, int referenceHeight) {
        boolean result = false;
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.hasItemView()) {
                if (x.itemAbove().onTouchArea(event, xOffset + x.xOffset(referenceHeight), yOffset + x.yOffset(referenceHeight), width, height, referenceHeight))
                    result = true;
            }
        }
        if (!result) {
            result = super.onTouchArea(event, xOffset, yOffset, width, height, referenceHeight);
        }
        return result;
    }

    @Override
    public boolean onDragArea(DragEvent event, int xOffset, int yOffset, int width, int height, int referenceHeight) {
        boolean result = false;
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.hasItemView()) {
                if (x.itemAbove().onDragArea(event, xOffset + x.xOffset(referenceHeight), yOffset + x.yOffset(referenceHeight), width, height, referenceHeight))
                    result = true;
            }
        }
        if (super.onDragArea(event, xOffset, yOffset, width, height, referenceHeight))
            result = true;
        return result;
    }

    @Override
    public void setTranslationY(float translationY) {
        this.translationY = translationY;
        if (isInEditMode()) super.setTranslationY(translationY);
        else super.setTranslationY(translationY - getCustomHeight());
    }

    public boolean hasNoItemAbove() {
        for (int i = 0; i < itemsAboveNode.length; i++)
            if (hasItemAbove(i)) return false;
        return true;
    }

    @Override
    public float getTranslationY() {
        return translationY;
    }

    protected static class ItemAboveNode {

        private final float relativeXOffset;
        private final float relativeYOffset;

        protected ItemAboveNode(float relativeXOffset, float relativeYOffset) {
            this.relativeXOffset = relativeXOffset;
            this.relativeYOffset = relativeYOffset;
        }

        private ItemView itemAbove;

        private boolean hasItemView() {
            return itemAbove != null;
        }

        private int xOffset(int referenceHeight) {
            return (int) (relativeXOffset * referenceHeight);
        }

        private int yOffset(int referenceHeight) {
            return (int) (relativeYOffset * referenceHeight);
        }

        private ItemView itemAbove() {
            return itemAbove;
        }

        private void setItemAbove(ItemView itemAbove) {
            this.itemAbove = itemAbove;
        }
    }

    public DragShadow dragShadow(int referenceHeight) {
        return new DragShadow(referenceHeight);
    }

    public class DragShadow extends DragShadowBuilder {
        private final int referenceHeight;

        DragShadow(int referenceHeight) {
            this.referenceHeight = referenceHeight;
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            drawItemOnCanvas(canvas, 0, 0, referenceHeight, canvas.getWidth(), canvas.getHeight());
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            recalculateDimensions(referenceHeight);
            shadowSize.set(getCustomWidth(), getCustomHeight());
            shadowTouchPoint.set(shadowSize.x / 2, (int) (shadowSize.y * 0.7));
            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
        }
    }

    public abstract String name();

    @Override
    public String toString() {
        String result = name() + "(";
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.hasItemView()) result += " " + x.itemAbove().toString();
        }
        result += ")";
        return result;
    }
}
