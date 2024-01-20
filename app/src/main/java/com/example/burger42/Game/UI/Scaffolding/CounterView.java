package com.example.burger42.Game.UI.Scaffolding;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.DragEvent;

import androidx.annotation.Nullable;

/**
 * An more specific CustomView, that adds features, that every CounterView needs.
 * It adds an DragAreaListener to be able to place plates and trays on all counter elements.
 * It adds a method to bound the RestaurantFragment where this view is used to this.
 * The RestaurantFragment can be used to add new ItemViews to the restaurant.
 */
public abstract class CounterView extends CustomView {

    /**
     * the RestaurantFragment this is a part of.
     */
    protected RestaurantFragment restaurantFragment;

    public CounterView(Context context) {
        super(context);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected final void init(Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);
    }

    /**
     * sets the restaurant fragment this is apart of.
     *
     * @param restaurantFragment the restaurant fragment this is apart of.
     */
    public final void setRestaurantFragment(RestaurantFragment restaurantFragment) {
        this.restaurantFragment = restaurantFragment;
        onRestaurantBound(restaurantFragment);
    }

    /**
     * this methode will be triggered after binding RestaurantFragment to counter view.
     *
     * @param restaurantFragment the bound restaurant fragment.
     */
    protected void onRestaurantBound(RestaurantFragment restaurantFragment) {
    }

    /**
     * @return the scaling of this view 1 if in edit mode to show correctly on the right side in split
     */
    @Override
    protected float scaling() {
        if (isInEditMode()) return 1;
        return 688f / 300;
    }

    /**
     * Adds the ability to place tablets and plates on the counter
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        addOnDragAreaListener(new OnDragAreaListener(1f, 0.7f, 0, 1) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    ItemView itemView = (ItemView) event.getLocalState();
                    itemView.removeFromParent();
                    restaurantFragment.addItem(itemView);
                    itemView.setTranslationY(getHeight() * 0.94f);
                    itemView.setTranslationX(Math.max(getX() + event.getX() - itemView.getCustomWidth() / 2f, 0));
                    return true;
                }
                return false;
            }
        }.setUseFilter(true).addFilterTag(ItemView.ItemFilterTag.CleanBase));
    }

    /**
     * @param referenceHeight the reference size to be used for this operation
     */
    @Override
    protected final void recalculateDimensions(int referenceHeight) {
        super.recalculateDimensions(referenceHeight);
    }

    /**
     * @param referenceHeight a reference size for calculating this height
     * @return the calculated height
     */
    @Override
    protected final int calculateHeight(int referenceHeight) {
        return super.calculateHeight(referenceHeight);
    }

    /**
     * @param referenceHeight a reference size for calculating this width
     * @return the calculated width
     */
    @Override
    protected final int calculateWidth(int referenceHeight) {
        return super.calculateWidth(referenceHeight);
    }

    /**
     * in editmode the AreaListeners are drawn as well, can be used in Split ore designe view.
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
        super.drawItemOnCanvas(canvas, xOffset, yOffset, referenceHeight, width, height);
        if (isInEditMode())
            drawDragTouchAreas(canvas, xOffset, yOffset, referenceHeight, width, height);
    }
}
