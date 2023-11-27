package com.example.burger42.Game.Restaurant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.example.burger42.R;

public abstract class CounterPartView extends View {

    private Bitmap image;
    private float ratio;

    public CounterPartView(Context context) {
        super(context);
        init(null, context);
    }

    public CounterPartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);
    }

    public CounterPartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    public CounterPartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, context);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void init(@Nullable AttributeSet attrs, Context context) {
        image = BitmapFactory.decodeResource(getResources(), drawableId());
        ratio = (float) image.getWidth() / (float) image.getHeight();
    }

    protected abstract int drawableId();

    @Override
    protected void onDraw(Canvas canvas) {
        if (isInEditMode()) {
        } else {
        }
        @SuppressLint("DrawAllocation") Rect destinationRect = canvas.getClipBounds();
        canvas.drawBitmap(image, null, destinationRect, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (MeasureSpec.getSize(heightMeasureSpec) * ratio), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("ACTION_DOWN");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("ACTION_MOVE");
                break;
            default:
                System.out.println("Other " + event.getAction());
        }
        return true;
    }

}
