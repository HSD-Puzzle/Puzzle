package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.R;

public class BurgerPattyRareView extends ItemView {
    public BurgerPattyRareView(Context context) {
        super(context);
    }

    public BurgerPattyRareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BurgerPattyRareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BurgerPattyRareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int drawableId() {
        return R.drawable.burgerpattyrare;
    }

    @Override
    protected float scaling() {
        return 117f / 500f;
    }

    @Override
    protected ItemAbove[] itemAboveSetUp() {
        return new ItemAbove[]{
                new ItemAbove() {
                    @Override
                    protected int xOffset(int reference) {
                        return 0;
                    }

                    @Override
                    protected int yOffset(int reference) {
                        return (int) (reference * 0.08f);
                    }
                }
        };
    }
}
