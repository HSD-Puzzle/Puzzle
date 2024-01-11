package com.example.burger42.Game.UI.Scaffolding;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public abstract class CustomView extends View {

    protected int referenceHeight;
    protected Bitmap image;
    protected float ratio;

    private OnTouchListener touchFallback;

    public void setTouchFallback(OnTouchListener touchFallback) {
        this.touchFallback = touchFallback;
    }

    private final List<OnDragAreaListener> onDragAreaListeners = new LinkedList<>();

    private final List<OnTouchAreaListener> onTouchAreaListeners = new LinkedList<>();

    public void addOnDragAreaListener(OnDragAreaListener onDragAreaListener) {
        onDragAreaListeners.add(onDragAreaListener);
    }

    public void addOnTouchAreaListener(OnTouchAreaListener onTouchAreaListener) {
        onTouchAreaListeners.add(onTouchAreaListener);
    }

    public CustomView(Context context) {
        super(context);
        init(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.referenceHeight = MeasureSpec.getSize(heightMeasureSpec);
        recalculateDimensions(referenceHeight);
    }

    private int height;
    private int width;

    public int getCustomHeight() {
        return height;
    }

    public int getCustomWidth() {
        return width;
    }

    protected void recalculateDimensions(int referenceHeight) {
        width = calculateWidth(referenceHeight);
        height = calculateHeight(referenceHeight);
        setMeasuredDimension(width, height);
    }

    protected int calculateHeight(int referenceHeight) {
        return (int) (referenceHeight * scaling());
    }

    protected int calculateWidth(int referenceHeight) {
        return (int) (referenceHeight * ratio * scaling());
    }

    public void setReferenceHeight(int height) {
        this.referenceHeight = height;
        recalculateDimensions(referenceHeight);
        invalidate();
    }

    protected abstract int drawableId();

    protected void init(Context context, @Nullable AttributeSet attrs) {
        beforeInit(context, attrs);
        image = BitmapFactory.decodeResource(getResources(), drawableId());
        ratio = (float) image.getWidth() / (float) image.getHeight();
        onInit(context, attrs);
    }

    protected void loadTexture() {
        image = BitmapFactory.decodeResource(getResources(), drawableId());
        ratio = (float) image.getWidth() / (float) image.getHeight();
    }

    protected void beforeInit(Context context, @Nullable AttributeSet attrs) {
    }

    protected void onInit(Context context, @Nullable AttributeSet attrs) {
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (onTouchArea(event, 0, 0, getCustomWidth(), getCustomHeight(), referenceHeight)) {
                return true;
            }
            return touchFallback != null && touchFallback.onTouch(this, event);
        }
        return false;
    }

    public boolean onTouchArea(MotionEvent event, int xOffset, int yOffset, int width, int height, int referenceHeight) {
        boolean result = false;
        float objectWidth = (referenceHeight * scaling() * ratio);
        float objectHeight = (referenceHeight * scaling());
        for (OnTouchAreaListener x : onTouchAreaListeners)
            if (x.isInArea((event.getX() + (objectWidth - width) / 2f - xOffset) / objectWidth, (height - yOffset - event.getY()) / objectHeight)) {
                if (x.onTouch(event)) result = true;
            }
        return result;
    }

    @Override
    public final boolean onDragEvent(DragEvent event) {
        return onDragArea(event, 0, 0, getCustomWidth(), getCustomHeight(), referenceHeight);
    }

    public boolean onDragArea(DragEvent event, int xOffset, int yOffset, int width, int height, int referenceHeight) {
        boolean result = false;
        float objectWidth = (referenceHeight * scaling() * ratio);
        float objectHeight = (referenceHeight * scaling());
        ItemView itemView = (ItemView) event.getLocalState();
        for (OnDragAreaListener x : onDragAreaListeners) {
            if ((!x.useFilter() || x.hasAtLeastOneEqualFilterTag(itemView)) && x.onDrag(event, x.isInArea((event.getX() + (objectWidth - width) / 2f - xOffset) / objectWidth, (height - yOffset - event.getY()) / objectHeight))) {
                result = true;
            }
        }
        return result || event.getAction() == DragEvent.ACTION_DRAG_STARTED;
    }

    protected float scaling() {
        return 1.0f;
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        drawItemOnCanvas(canvas, 0, 0, referenceHeight, MeasureSpec.getSize(getMeasuredWidth()), MeasureSpec.getSize(getMeasuredHeight()));
        afterDraw();
    }

    protected void afterDraw() {
    }

    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, (int) (referenceHeight * scaling() * ratio), (int) (referenceHeight * scaling()), false);
        int centerWidth = (width - scaledBitmap.getWidth()) / 2;
        int centerHeight = height - scaledBitmap.getHeight();
        canvas.drawBitmap(scaledBitmap, centerWidth + xOffset, centerHeight - yOffset, null);
    }

    protected void drawDragTouchAreas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        int sWidth = (int) (referenceHeight * scaling() * ratio);
        int sHeight = (int) (referenceHeight * scaling());
        float wCenter = width / 2f + xOffset;
        float hCenter = height - yOffset;
        for (OnDragAreaListener x : onDragAreaListeners) {
            Paint paint = new Paint();
            paint.setARGB(60, 255, 0, 0);
            canvas.drawRect(wCenter + sWidth * (x.relativeLeft() - 0.5f), hCenter - sHeight * x.relativeTop(), wCenter + sWidth * (x.relativeRight() - 0.5f), hCenter - sHeight * x.relativeBottom(), paint);
        }
        for (OnTouchAreaListener x : onTouchAreaListeners) {
            Paint paint = new Paint();
            paint.setARGB(60, 0, 255, 0);
            canvas.drawRect(wCenter + sWidth * (x.relativeLeft() - 0.5f), hCenter - sHeight * x.relativeTop(), wCenter + sWidth * (x.relativeRight() - 0.5f), hCenter - sHeight * x.relativeBottom(), paint);
        }
    }


    public boolean drag(ItemView itemView) {
        itemView.setReferenceHeight(referenceHeight);
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = itemView.dragShadow(referenceHeight);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return startDragAndDrop(data, shadowBuilder, itemView, DRAG_FLAG_OPAQUE);
        } else {
            return startDrag(data, shadowBuilder, itemView, 512);
        }
    }
}
