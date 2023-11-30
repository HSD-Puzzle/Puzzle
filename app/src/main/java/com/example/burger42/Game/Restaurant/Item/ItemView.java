package com.example.burger42.Game.Restaurant.Item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class ItemView extends View {

    private Bitmap image;
    private float ratio;

    ItemView(Context context) {
        super(context);
        init(null, context);
    }

    ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);

    }

    ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, context);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void init(@Nullable AttributeSet attrs, Context context) {
        image = BitmapFactory.decodeResource(getResources(), drawableId());
        ratio = (float) image.getWidth() / (float) image.getHeight();
    }

    protected abstract int drawableId();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (MeasureSpec.getSize(heightMeasureSpec) * ratio / 4), MeasureSpec.getSize(heightMeasureSpec) / 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        @SuppressLint("DrawAllocation") Rect destinationRect = canvas.getClipBounds();
        canvas.drawBitmap(image, null, destinationRect, null);
    }
}
