package com.example.burger42.Game.Restaurant.Item;

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
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Restaurant.Restaurant;

public abstract class ItemView extends RelativeLayout {

    private Restaurant restaurant;


    ItemView(Context context, Restaurant restaurant) {
        super(context);
        init(null, context);
        this.restaurant = restaurant;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void init(@Nullable AttributeSet attrs, Context context) {
        addView(new Background(context));
    }

    protected abstract int drawableId();

    public void addItem(ItemView itemView) {
        addView(itemView);
        itemView.setTranslationY(-20);
    }


    @Override
    public void setTranslationX(float translationX) {
        super.setTranslationX(translationX - (scale() * restaurant.height() / 2));
    }

    @Override
    public void setTranslationY(float translationY) {
        super.setTranslationY(translationY - (scale() * restaurant.height() / 2) + offsetY());
    }

    protected abstract float scale();

    protected abstract float offsetY();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                startDragAndDrop(data, shadowBuilder, this, 0);
            } else {
                startDrag(data, shadowBuilder, this, 0);
            }
            setVisibility(View.INVISIBLE);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            setVisibility(View.VISIBLE);
            return true;
        }

        return false;
    }

    private class Background extends View {

        private Bitmap image;

        public Background(Context context) {
            super(context);
            init(null, context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension((int) (restaurant.height() * scale()), (int) (restaurant.height() * scale()));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            @SuppressLint("DrawAllocation") Rect destinationRect = canvas.getClipBounds();
            canvas.drawBitmap(image, null, destinationRect, null);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        void init(@Nullable AttributeSet attrs, Context context) {
            image = BitmapFactory.decodeResource(getResources(), drawableId());
        }
    }

}
