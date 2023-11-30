package com.example.burger42.Game.Restaurant.Counter;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.R;

public class MillCounterPart extends CounterPartView {

    public MillCounterPart(Context context) {
        super(context);
    }

    public MillCounterPart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MillCounterPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MillCounterPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int drawableId() {
        return R.drawable.millcleancorner;
    }
}
