package com.example.burger42.Game.UI.Scaffolding;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.DragEvent;

import androidx.annotation.Nullable;

public abstract class CounterView extends CustomView {

    protected RestaurantFragment restaurantFragment;

    public CounterView(Context context) {
        super(context);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected final void init(Context context, @Nullable AttributeSet attrs) {
        super.init(context, attrs);
    }

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


    @Override
    protected float scaling() {
        if (isInEditMode()) return 1;
        return 688f / 300;
    }

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

    @Override
    protected final void recalculateDimensions(int referenceHeight) {
        super.recalculateDimensions(referenceHeight);
    }

    @Override
    protected final int calculateHeight(int referenceHeight) {
        return super.calculateHeight(referenceHeight);
    }

    @Override
    protected final int calculateWidth(int referenceHeight) {
        return super.calculateWidth(referenceHeight);
    }

    @Override
    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        super.drawItemOnCanvas(canvas, xOffset, yOffset, referenceHeight, width, height);
        if (isInEditMode())
            drawDragTouchAreas(canvas, xOffset, yOffset, referenceHeight, width, height);

    }
}
