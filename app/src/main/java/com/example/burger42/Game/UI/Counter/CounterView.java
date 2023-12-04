package com.example.burger42.Game.UI.Counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class CounterView extends View {
    private Bitmap image;
    private float ratio;

    public CounterView(Context context) {
        super(context);
        init(context, null);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (MeasureSpec.getSize(heightMeasureSpec) * ratio), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isInEditMode()) {
        } else {
        }
        @SuppressLint("DrawAllocation") Rect destinationRect = canvas.getClipBounds();
        canvas.drawBitmap(image, null, destinationRect, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void init(Context context, @Nullable AttributeSet attrs) {
        image = BitmapFactory.decodeResource(getResources(), drawableId());
        ratio = (float) image.getWidth() / (float) image.getHeight();
    }

    protected abstract int drawableId();
}
