package com.example.burger42.Game.Restaurant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.burger42.R;

public class BreadCounterPart extends CounterPartView {

    public BreadCounterPart(Context context) {
        super(context);
    }

    public BreadCounterPart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BreadCounterPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BreadCounterPart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int drawableId() {
        return R.drawable.burgerbreadcorner;
    }
}
