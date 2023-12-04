package com.example.burger42.Game.UI.Counter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.ItemView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
        @SuppressLint("DrawAllocation") Rect destinationRect = canvas.getClipBounds();
        canvas.drawBitmap(image, null, destinationRect, null);
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

    @SuppressLint("UseCompatLoadingForDrawables")
    void init(Context context, @Nullable AttributeSet attrs) {
        image = BitmapFactory.decodeResource(getResources(), drawableId());
        ratio = (float) image.getWidth() / (float) image.getHeight();
    }

    protected abstract int drawableId();

    @Override
    public boolean onDragEvent(DragEvent event) {
        if (dragAreas().size() > 0) {
            boolean used = false;
            for (DragArea x : dragAreas()) {
                if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    if (x.onDragEvent(event)) {
                        used = true;
                    }
                } else if (x.dragEventInsideDragArea(event)) {
                    return x.onDragEvent(event);
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

    private LinkedList<DragArea> dragAreas;

    public List<DragArea> dragAreas() {
        if (dragAreas == null) {
            dragAreas = new LinkedList<>();
            dragAreas.add(new DragArea(0.4f, 0.6f, 0, 1) {
                @Override
                boolean onDragEvent(DragEvent event) {
                    Log.println(Log.DEBUG, "CounterView", event.getAction() + "");
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            break;
                        case DragEvent.ACTION_DRAG_LOCATION:
                            break;
                        case DragEvent.ACTION_DROP:
                            ItemView itemView = (ItemView) event.getLocalState();
                            itemView.setTranslationY(getHeight() / 2.28f);
                            itemView.setTranslationX(Math.max(getX() + event.getX() - itemView.getWidth() / 2, 0));
                            break;
                        case DragEvent.ACTION_DRAG_ENDED:
                            break;
                        case DragEvent.ACTION_DRAG_ENTERED:
                            break;
                        case DragEvent.ACTION_DRAG_EXITED:
                            break;
                    }
                    return true;
                }
            });
        }
        return dragAreas;
    }

    public List<TouchArea> touchAreas() {
        return new LinkedList<>();
    }


    abstract class DragArea {

        final int top;
        final int bottom;
        final int right;
        final int left;

        DragArea(float top, float bottom, float left, float right) {
            this.top = (int) (top * getHeight());
            this.bottom = (int) (bottom * getHeight());
            this.right = (int) (right * getWidth());
            this.left = (int) (left * getWidth());
        }

        private boolean dragEventInsideDragArea(DragEvent event) {
            return top < event.getY() && bottom > event.getY() && left < event.getX() && right > event.getX();
        }

        abstract boolean onDragEvent(DragEvent event);

        @NonNull
        @Override
        public String toString() {
            return "DragArea{top: " + top + ", bottom:" + bottom + ", right:" + right + ", left:" + left + "}";
        }
    }

    abstract class TouchArea {

        final int top;
        final int bottom;
        final int right;
        final int left;

        TouchArea(float top, float bottom, float left, float right) {
            this.top = (int) (top * getHeight());
            this.bottom = (int) (bottom * getHeight());
            this.right = (int) (right * getWidth());
            this.left = (int) (left * getWidth());
        }

        private boolean touchEventInsideDragArea(MotionEvent event) {
            return top < event.getY() && bottom > event.getY() && left < event.getX() && right > event.getX();
        }

        abstract boolean onTouchEvent(MotionEvent event);

        @NonNull
        @Override
        public String toString() {
            return "TouchArea{top: " + top + ", bottom:" + bottom + ", right:" + right + ", left:" + left + "}";
        }
    }
}
