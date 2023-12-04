package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.R;

public class TopBreadView extends ItemView {
    public TopBreadView(Context context) {
        super(context);
    }

    public TopBreadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TopBreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TopBreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int drawableId() {
        return R.drawable.topbread;
    }

    @Override
    protected float scaling() {
        return 127f / 500f;
    }

    @Override
    protected ItemAbove[] itemAboveSetUp() {
        return new ItemAbove[0];
    }
}
