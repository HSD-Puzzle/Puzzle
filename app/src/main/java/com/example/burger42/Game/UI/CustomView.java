package com.example.burger42.Game.UI;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.ItemView;
import com.example.burger42.Game.UI.RestaurantLevel.RestaurantFragment;

import java.util.LinkedList;
import java.util.List;

public abstract class CustomView extends View {

    protected int referenceHeight;
    protected Bitmap image;
    protected float ratio;

    protected RestaurantFragment restaurantFragment;

    public CustomView(Context context, RestaurantFragment restaurantFragment) {
        super(context);
        this.restaurantFragment = restaurantFragment;
        init(context, null);
    }


    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        referenceHeight = MeasureSpec.getSize(heightMeasureSpec);
        height = calculatedFullHeight(referenceHeight);
        setMeasuredDimension((int) (referenceHeight * ratio * scaling()), height);

    }

    protected int calculatedFullHeight(int referenceHeight) {
        return (int) (referenceHeight * scaling());
    }

    protected int height = -1;

    public int height(int referenceHeight) {
        if (height == -1)
            height = calculatedFullHeight(referenceHeight);
        return height;
    }

    protected abstract int drawableId();

    protected void init(Context context, @Nullable AttributeSet attrs) {
        image = BitmapFactory.decodeResource(getResources(), drawableId());
        ratio = (float) image.getWidth() / (float) image.getHeight();
        onInit(context, attrs);
    }

    protected void onInit(Context context, @Nullable AttributeSet attrs) {
    }

    protected float scaling() {
        return 1.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawItemOnCanvas(canvas, 0, 0, referenceHeight, MeasureSpec.getSize(getMeasuredWidth()), MeasureSpec.getSize(getMeasuredHeight()));
        if (isInEditMode()) {
            @SuppressLint("DrawAllocation") Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setAlpha(100);
            for (DragArea x : dragAreas()) {
                canvas.drawRect(x.left, x.top, x.right, x.bottom, paint);
            }
            paint.setColor(Color.GREEN);
            paint.setAlpha(100);
            for (TouchArea x : touchAreas()) {
                canvas.drawRect(x.left, x.top, x.right, x.bottom, paint);
            }
        }
    }

    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        @SuppressLint("DrawAllocation") Rect destinationRect = canvas.getClipBounds();
        canvas.drawBitmap(image, null, destinationRect, null);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        if (dragAreas().size() > 0) {
            if (event.getLocalState() == this)
                return false;
            boolean used = false;
            for (DragArea x : dragAreas()) {
                if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    if (x.onDragEvent(event)) {
                        used = true;
                    }
                } else if (x.dragEventInsideDragArea(event)) {
                    if (x.onDragEvent(event)) {
                        return true;
                    }
                }
            }
            return used;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchAreas().size() > 0) {
            boolean used = false;
            for (TouchArea x : touchAreas()) {
                if (x.touchEventInsideDragArea(event)) {
                    return x.onTouchEvent(event);
                }
            }
            return used;
        }
        return false;
    }

    public List<DragArea> dragAreas() {
        return new LinkedList<>();
    }

    public List<TouchArea> touchAreas() {
        return new LinkedList<>();
    }

    protected abstract class DragArea {

        final int top;
        final int bottom;
        final int right;
        final int left;

        protected DragArea(float top, float bottom, float left, float right) {
            this.top = (int) (top * getHeight());
            this.bottom = (int) (bottom * getHeight());
            this.right = (int) (right * getWidth());
            this.left = (int) (left * getWidth());
        }

        private boolean dragEventInsideDragArea(DragEvent event) {
            return top < event.getY() && bottom > event.getY() && left < event.getX() && right > event.getX();
        }

        protected abstract boolean onDragEvent(DragEvent event);

        @NonNull
        @Override
        public String toString() {
            return "DragArea{top: " + top + ", bottom:" + bottom + ", right:" + right + ", left:" + left + "}";
        }
    }

    protected abstract class TouchArea {

        final int top;
        final int bottom;
        final int right;
        final int left;

        protected TouchArea(float top, float bottom, float left, float right) {
            this.top = (int) (top * getHeight());
            this.bottom = (int) (bottom * getHeight());
            this.right = (int) (right * getWidth());
            this.left = (int) (left * getWidth());
        }

        private boolean touchEventInsideDragArea(MotionEvent event) {
            return top < event.getY() && bottom > event.getY() && left < event.getX() && right > event.getX();
        }

        protected abstract boolean onTouchEvent(MotionEvent event);

        @NonNull
        @Override
        public String toString() {
            return "TouchArea{top: " + top + ", bottom:" + bottom + ", right:" + right + ", left:" + left + "}";
        }
    }

    public void drag(ItemView itemView) {
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = itemView.dragShadow(restaurantFragment.itemSize());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startDragAndDrop(data, shadowBuilder, itemView, DRAG_FLAG_OPAQUE);
        } else {
            startDrag(data, shadowBuilder, itemView, 1);
        }
    }
}
