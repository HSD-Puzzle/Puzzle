package com.example.burger42.Game.Restaurant.Item;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.R;

public class BottomBreadItem extends ItemView {
    public BottomBreadItem(Context context) {
        super(context);
    }

    public BottomBreadItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomBreadItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BottomBreadItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int drawableId() {
        return R.drawable.bottombread;
    }
}
