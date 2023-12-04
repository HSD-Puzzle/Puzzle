package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.R;

public class BottomBreadView extends ItemView {
    public BottomBreadView(Context context) {
        super(context);
    }

    public BottomBreadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomBreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BottomBreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int drawableId() {
        return R.drawable.bottombread;
    }

    @Override
    protected ItemAbove[] itemAboveSetUp() {
        return new ItemAbove[]{new ItemAbove() {
            @Override
            protected int xOffset(int reference) {
                return 0;
            }

            @Override
            protected int yOffset(int reference) {
                return (int) (reference * 0.075f);
            }
        }};
    }


    @Override
    protected float scaling() {
        return 121f / 500f;
    }
}
