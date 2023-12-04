package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.burger42.R;

public class PlateView extends ItemView {

    private ItemView itemAbove2;

    public PlateView(Context context) {
        super(context);
    }

    public PlateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected int drawableId() {
        return R.drawable.cleanplate;
    }

    @Override
    protected ItemAbove[] itemAboveSetUp() {
        return new ItemAbove[]{
                new ItemAbove() {
                    @Override
                    protected int xOffset(int reference) {
                        return (int) (reference * -0.2f);
                    }

                    @Override
                    protected int yOffset(int reference) {
                        return (int) (reference * 0.13f);
                    }
                },
                new ItemAbove() {
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
