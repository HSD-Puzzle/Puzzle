package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.RestaurantLevel.RestaurantFragment;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

public class PlateView extends ItemView {

    public PlateView(Context context, RestaurantFragment restaurantFragment) {
        super(context, restaurantFragment);
    }

    public PlateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int drawableId() {
        return R.drawable.cleanplate;
    }

    @Override
    protected ItemAbove[] itemAboveSetUp() {
        return new ItemAbove[]{
                new ItemAbove(0, 1, 0, 0.5f) {
                    @Override
                    protected int xOffset(int reference) {
                        return (int) (reference * -0.2f);
                    }

                    @Override
                    protected int yOffset(int reference) {
                        return (int) (reference * 0.13f);
                    }
                },
                new ItemAbove(0, 1, 0.5f, 1) {
                    @Override
                    protected int xOffset(int reference) {
                        return (int) (reference * 0.2f);
                    }

                    @Override
                    protected int yOffset(int reference) {
                        return (int) (reference * 0.04f);
                    }
                }
        };
    }

    @Override
    protected float scaling() {
        return 176f / 500f;
    }
}
