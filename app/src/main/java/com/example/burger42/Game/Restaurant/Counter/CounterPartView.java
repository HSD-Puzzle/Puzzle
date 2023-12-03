package com.example.burger42.Game.Restaurant.Counter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Restaurant.Item.BottomBreadItem;
import com.example.burger42.Game.Restaurant.Restaurant;

public abstract class CounterPartView extends RelativeLayout {

    protected Restaurant restaurant;

    CounterPartView(Context context, Restaurant restaurant) {
        super(context);
        this.restaurant = restaurant;
        init(null, context);
    }

    protected abstract int drawableId();

    @SuppressLint("UseCompatLoadingForDrawables")
    void init(@Nullable AttributeSet attrs, Context context) {
        addView(new Background(context));
    }

    private class Background extends View {

        private Bitmap image;
        private float ratio;

        public Background(Context context) {
            super(context);
            init(null, context);
        }

        public Background(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public Background(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public Background(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
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
        void init(@Nullable AttributeSet attrs, Context context) {
            image = BitmapFactory.decodeResource(getResources(), drawableId());
            ratio = (float) image.getWidth() / (float) image.getHeight();
        }
    }
}
