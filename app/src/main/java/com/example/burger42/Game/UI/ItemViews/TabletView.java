package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.burger42.R;

public class TabletView extends ItemView {

    private ItemView itemAbove2;

    public TabletView(Context context) {
        super(context);
    }

    public TabletView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabletView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TabletView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        setItemAbove(new PlateView(context));
        setItemAbove(1, new PlateView(context));
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
                new ItemAbove() {
                    @Override
                    protected int xOffset(int reference) {
                        return (int) (reference * -0.48f);
                    }

                    @Override
                    protected int yOffset(int reference) {
                        return (int) (reference * 0.2f);
                    }
                },
                new ItemAbove() {
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
