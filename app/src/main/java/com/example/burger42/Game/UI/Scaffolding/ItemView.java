package com.example.burger42.Game.UI.Scaffolding;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * An special CustomView, that adds the ability to drag this item and layers multiple above each other.
 */
public abstract class ItemView extends CustomView implements DragFilter {

    /**
     * ItemFilterTags to group the different items
     */
    public enum ItemFilterTag {
        Ingredient, CleanBase, Tool, Payable
    }

    /**
     * @return a list of filterTags used for the specific item
     */
    protected List<ItemFilterTag> itemFilterTags() {
        return new LinkedList<>();
    }

    /**
     * The DragFilterImplementation used for this ItemView
     */
    private DragFilter dragFilter;

    /**
     * @param tag the tag to add.
     * @return this ItemView to use it in a SettingsChain
     */
    public ItemView addFilterTag(String tag) {
        dragFilter.addFilterTag(tag);
        return this;
    }

    /**
     * @param tag the tag to remove.
     * @return this ItemView to use it in a SettingsChain
     */
    public ItemView removeFilterTag(String tag) {
        dragFilter.removeFilterTag(tag);
        return this;
    }

    /**
     * @param other the other DragFilter to compare with this.
     * @return true if a filter at least one filter tag exists in both DragFilter else false
     */
    public boolean hasAtLeastOneEqualFilterTag(DragFilter other) {
        return dragFilter.hasAtLeastOneEqualFilterTag(other);
    }

    @NonNull
    @Override
    public Iterator<String> iterator() {
        return dragFilter.iterator();
    }

    /**
     * If this  ItemView is child of an other itemView the parent.
     */
    private ItemView parent = null;

    /**
     * removes the ItemView from its parent
     */
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

    /**
     * the restaurant parent this itemview is child of. Or null, if it is not child of the restaurantfragment
     */
    private RestaurantFragment restaurantFragment;

    /**
     * The nodes that contains the ItemViews above and the information to render it.
     */
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

    /**
     * Sets additional up the items above nodes and the drag-filter
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
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

    /**
     * adds an ontouch listener, that allows dragging the item by clicking on it.
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        setTouchFallback(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return drag();
            }
        });
    }

    /**
     * drag this item.
     *
     * @return true if the drag was successfully
     */
    public boolean drag() {
        return drag(this);
    }

    /**
     * drags an item.
     *
     * @param item the ItemView to drag.
     * @return true if the drag was successfully
     */
    public boolean drag(ItemView item) {
        if (parent != null) return parent.drag(item);
        else return super.drag(item);
    }

    /**
     * Sets up the nodes where to place any items above.
     *
     * @return the nodes that control where to place any items above.
     */
    protected ItemAboveNode[] itemAboveSetUp() {
        return new ItemAboveNode[]{new ItemAboveNode(0, 0.1f) {
        }};
    }

    /**
     * @param referenceHeight a reference size for calculating this height
     * @return the height of this itemview with its children
     */
    @Override
    protected final int calculateHeight(int referenceHeight) {
        int maxHeight = super.calculateHeight(referenceHeight);
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.hasItemView())
                maxHeight = Math.max(x.yOffset(referenceHeight) + x.itemAbove().calculateHeight(referenceHeight), maxHeight);
        }
        return maxHeight;
    }

    /**
     * @param referenceHeight a reference size for calculating this width
     * @return the with of this itemVIew with all of its children
     */
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

    /**
     * @param visibility the visibility this item should have after it.
     */
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        redraw();
    }

    /**
     * draws this item on the canvas, with all of its children
     *
     * @param canvas          the canvas to draw the view on.
     * @param xOffset         the x offset where to draw the view on the canvas.
     * @param yOffset         the y offset where to draw the view on the canvas.
     * @param referenceHeight the referenceheight used to draw the items the correct size.
     * @param width           the width of the view size
     * @param height          the height of the view size
     */
    @Override
    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        //draws this item alone on the canvas
        super.drawItemOnCanvas(canvas, xOffset, yOffset, referenceHeight, width, height);
        //draws every item above on the correct position on the canvas
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.hasItemView() && x.itemAbove().getVisibility() == VISIBLE)
                x.itemAbove.drawItemOnCanvas(canvas, x.xOffset(referenceHeight) + xOffset, x.yOffset(referenceHeight) + yOffset, referenceHeight, width, height);
        }
        //draws the position where to place the item above and the listeners on the canvas if in editmodes
        if (isInEditMode()) {
            for (ItemAboveNode x : itemsAboveNode) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                canvas.drawRect(width / 2 + xOffset + x.xOffset(referenceHeight) - 10, height - yOffset - x.yOffset(referenceHeight) - 20, width / 2 + xOffset + x.xOffset(referenceHeight) + 10, height - yOffset - x.yOffset(referenceHeight), paint);
            }
            drawDragTouchAreas(canvas, xOffset, yOffset, referenceHeight, width, height);
        }
    }

    /**
     * removes the given item from above
     *
     * @param itemAbove item to remove
     */
    public void removeItemAbove(ItemView itemAbove) {
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.itemAbove == itemAbove) {
                x.setItemAbove(null);
                redraw();
            }
        }
    }

    /**
     * sets an item above at the given id
     *
     * @param index     the index where to place the item above. Which itemAboveNode to use.
     * @param itemAbove the item to place above
     */
    public void setItemAbove(int index, ItemView itemAbove) {
        if (!itemIsPartOfParentChain(itemAbove)) {
            itemAbove.removeFromParent();
            itemAbove.parent = this;
            itemsAboveNode[index].setItemAbove(itemAbove);
            redraw();
        }
    }

    /**
     * tests if the item is already child of this item.
     *
     * @param itemView the item to check if it is part of this
     * @return true if the item is child of this item or any of its children.
     */
    public boolean itemIsPartOfParentChain(ItemView itemView) {
        return itemView == this || (parent != null && parent.itemIsPartOfParentChain(itemView));
    }

    /**
     * redraws the on the fragment bound part of this ItemView
     */
    protected void redraw() {
        if (parent != null) parent.redraw();
        else {
            setLayoutParams(new FrameLayout.LayoutParams(referenceHeight, referenceHeight));
        }
    }

    /**
     * checks if there is an item above at the given index
     *
     * @param index the index to check if there is an item above
     * @return true if there is an item
     */
    public boolean hasItemAbove(int index) {
        if (index < itemsAboveNode.length)
            return itemsAboveNode[index].hasItemView();
        return false;
    }

    /**
     * @param index the index to get the item above from
     * @return the item at the given index
     */
    public ItemView itemAbove(int index) {
        return itemsAboveNode[index].itemAbove();
    }

    /**
     * recalculateDimensions determines and sets the dimensions for this view.
     *
     * @param referenceHeight the reference size to be used for this operation
     */
    @Override
    protected final void recalculateDimensions(int referenceHeight) {
        super.recalculateDimensions(referenceHeight);
        setTranslationY(translationY);
    }

    /**
     * the translation of this item in the overall context
     */
    private float translationY = 0;

    /**
     * onTouchArea will be called, when the onTouchEvent methode is triggered.
     * this method triggers all TouchAreaListeners, that are at the position of the touch.
     * First the TouchAreas of the child will be triggered. this itemOntouchLister is only be used if all childs return false.
     *
     * @param event           the event from the onTouchAreaEvent.
     * @param xOffset         if the listeners only use an piece of the whole view the offset from the bottom
     * @param yOffset         if the listeners only use an piece of the whole view the offset from the left
     * @param width           the width used for the area listener.
     * @param height          the height used for the area listener.
     * @param referenceHeight the referenceHeight of the view
     * @return true if any listener returns true and an interaction with this view happens.
     */
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

    /**
     * onDragArea will be called, when the onDragEvent methode is triggered.
     * this method triggers all OnDragAreaListeners.
     *
     * @param event           the event from the onDragEvent.
     * @param xOffset         if the listeners only use an piece of the whole view the offset from the bottom
     * @param yOffset         if the listeners only use an piece of the whole view the offset from the left
     * @param width           the width used for the area listener.
     * @param height          the height used for the area listener.
     * @param referenceHeight the referenceHeight of the view
     * @return true if any listener returns true and an interaction with this view happens, or the drag started to show interest of more information.
     */
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

    /**
     * sets the y translation
     *
     * @param translationY the y translation
     */
    @Override
    public void setTranslationY(float translationY) {
        this.translationY = translationY;
        if (isInEditMode()) super.setTranslationY(translationY);
        else super.setTranslationY(translationY - getCustomHeight());
    }

    /**
     * checks if there is any item above
     *
     * @return true if there is no item above.
     */
    public boolean hasNoItemAbove() {
        for (int i = 0; i < itemsAboveNode.length; i++)
            if (hasItemAbove(i)) return false;
        return true;
    }

    /**
     * @return the yTranslation
     */
    @Override
    public float getTranslationY() {
        return translationY;
    }

    /**
     * The container that controls a specific place where to place an item above.
     */
    protected static class ItemAboveNode {
        /**
         * the x position of where the item had to be placed relative to the reference size
         */
        private final float relativeXOffset;
        /**
         * the y position of where the item had to be placed relative to the reference size
         */
        private final float relativeYOffset;

        /**
         * @param relativeXOffset the x position of where the item had to be placed relative to the object size
         * @param relativeYOffset the y position of where the item had to be placed relative to the object size
         */
        protected ItemAboveNode(float relativeXOffset, float relativeYOffset) {
            this.relativeXOffset = relativeXOffset;
            this.relativeYOffset = relativeYOffset;
        }

        /**
         * the current item that is placed at this place above
         */
        private ItemView itemAbove;

        /**
         * @return true if there is an item above else false
         */
        private boolean hasItemView() {
            return itemAbove != null;
        }

        /**
         * @param referenceHeight the reference size to calculate the position
         * @return the absolut x position
         */
        private int xOffset(int referenceHeight) {
            return (int) (relativeXOffset * referenceHeight);
        }

        /**
         * @param referenceHeight the reference size to calculate the position
         * @return the absolut y position
         */
        private int yOffset(int referenceHeight) {
            return (int) (relativeYOffset * referenceHeight);
        }

        /**
         * @return the item above
         */
        private ItemView itemAbove() {
            return itemAbove;
        }

        /**
         * sets an item above
         *
         * @param itemAbove the item to set above
         */
        private void setItemAbove(ItemView itemAbove) {
            this.itemAbove = itemAbove;
        }
    }

    /**
     * The DragShadow to use as drag shadow while dragging
     */
    private DragShadow dragShadow;

    /**
     * @param referenceHeight a reference size to calculate the drag shadow in the right size
     * @return The DragShadow to use as drag shadow while dragging
     */
    public DragShadow dragShadow(int referenceHeight) {
        if (dragShadow == null)
            dragShadow = new DragShadow(referenceHeight);
        return dragShadow;
    }

    /**
     * The DragShadow to use as drag shadow while dragging
     */
    public class DragShadow extends DragShadowBuilder {
        /**
         * a reference size to calculate the drag shadow in the right size
         */
        private final int referenceHeight;

        /**
         * @param referenceHeight a reference size to calculate the drag shadow in the right size
         */
        DragShadow(int referenceHeight) {
            this.referenceHeight = referenceHeight;
        }

        /**
         * called to draw the shadow on a canvas
         *
         * @param canvas the canvas to draw the item on
         */
        @Override
        public void onDrawShadow(Canvas canvas) {
            drawItemOnCanvas(canvas, 0, 0, referenceHeight, canvas.getWidth(), canvas.getHeight());
        }

        /**
         * redraws the shadow if there are changes while dragging on the itemview.
         */
        public void shadowInvalidate() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateDragShadow(this);
            }
        }

        /**
         * sets the size and position
         *
         * @param shadowSize       the size this shadow should have
         * @param shadowTouchPoint the point where relative to the touch position on the screen the shadow should be drawn
         */
        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            recalculateDimensions(referenceHeight);
            shadowSize.set(getCustomWidth(), getCustomHeight());
            shadowTouchPoint.set(shadowSize.x / 2, (int) (shadowSize.y * 0.7));
            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
        }
    }

    /**
     * @return the name of this item
     */
    public abstract String name();

    @NonNull
    @Override
    public String toString() {
        String result = name() + "(";
        for (ItemAboveNode x : itemsAboveNode) {
            if (x.hasItemView()) result += " " + x.itemAbove().toString();
        }
        result += ")";
        return result;
    }

    /**
     * removes all children from this.
     */
    public void clearChildren() {
        for (ItemAboveNode x : itemsAboveNode) {
            x.setItemAbove(null);
        }
        if (dragShadow != null)
            dragShadow.shadowInvalidate();
    }
}
