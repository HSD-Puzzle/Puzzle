package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.RestaurantLevel.RestaurantFragment;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

public class TabletView extends ItemView {

    private List<DragArea> dragAreas;

    public TabletView(Context context, RestaurantFragment restaurantFragment) {
        super(context, restaurantFragment);
    }

    public TabletView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int drawableId() {
        return R.drawable.tablet;
    }

    @Override
    protected float scaling() {
        return 299f / 500f;
    }

    @Override
    protected ItemAbove[] itemAboveSetUp() {
        return new ItemAbove[]{
                new ItemAbove(0, 1, 0, 0.5f) {
                    @Override
                    protected int xOffset(int reference) {
                        return (int) (reference * -0.48f);
                    }

                    @Override
                    protected int yOffset(int reference) {
                        return (int) (reference * 0.2f);
                    }
                },
                new ItemAbove(0, 1, 0.5f, 1) {
                    @Override
                    protected int xOffset(int reference) {
                        return (int) (reference * 0.47f);
                    }

                    @Override
                    protected int yOffset(int reference) {
                        return (int) (reference * 0.11f);
                    }
                }
        };
    }
}
