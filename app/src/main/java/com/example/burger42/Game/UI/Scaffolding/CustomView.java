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
 * The CustomView class is a special view, i.e. an element that can be displayed.
 * What is special is that the size does not have to be known before creating it.
 * This class calculates its own size depending on what it displays itself.
 * All that is needed is a reference size so that the objects look the same on all devices.
 * Furthermore, this view adds the possibility to react to clicks and drags of a sub-area of this view.
 */
public abstract class CustomView extends View {

    /**
     * a size to which this view adjusts its size.
     */
    protected int referenceHeight;
    /**
     * the image to be displayed for this view.
     */
    protected Bitmap image;
    /**
     * the aspect ratio of the presentation
     */
    protected float ratio;

    /**
     * The TouchListener to be called if there was no response from a subarea listener.
     */
    private OnTouchListener touchFallback;

    /**
     * @param touchFallback The TouchListener to be called if there was no response from a subarea listener.
     */
    public void setTouchFallback(OnTouchListener touchFallback) {
        this.touchFallback = touchFallback;
    }

    /**
     * List of all OnDragAreaListeners important  for this view
     */
    private final List<OnDragAreaListener> onDragAreaListeners = new LinkedList<>();

    /**
     * List of all onTouchAreaListeners important  for this view
     */
    private final List<OnTouchAreaListener> onTouchAreaListeners = new LinkedList<>();

    /**
     * adds a listener OnDragAreaListener
     *
     * @param onDragAreaListener the listener to add
     */
    public void addOnDragAreaListener(OnDragAreaListener onDragAreaListener) {
        onDragAreaListeners.add(onDragAreaListener);
    }

    /**
     * adds a listener OnTouchAreaListener
     *
     * @param onTouchAreaListener the listener to add
     */
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

    /**
     * the height calculated for this view for presentation
     */
    private int height;

    /**
     * the width calculated for this view for presentation
     */
    private int width;

    /**
     * This may differ from getHeight, as this is set after the calculation and getHeight is set after the display.
     *
     * @return the height calculated for this view for presentation
     */
    public int getCustomHeight() {
        return height;
    }

    /**
     * This may differ from getWidth, as this is set after the calculation and getWidth is set after the display.
     *
     * @return the width calculated for this view for presentation
     */
    public int getCustomWidth() {
        return width;
    }

    /**
     * recalculateDimensions determines and sets the dimensions for this view.
     *
     * @param referenceHeight the reference size to be used for this operation
     */
    protected void recalculateDimensions(int referenceHeight) {
        width = calculateWidth(referenceHeight);
        height = calculateHeight(referenceHeight);
        setMeasuredDimension(width, height);
    }

    /**
     * calculateHeight calculates the height that this view should have.
     *
     * @param referenceHeight a reference size for calculating this height
     * @return the calculated height
     */
    protected int calculateHeight(int referenceHeight) {
        return (int) (referenceHeight * scaling());
    }

    /**
     * calculateHeight calculates the width that this view should have.
     *
     * @param referenceHeight a reference size for calculating this width
     * @return the calculated width
     */
    protected int calculateWidth(int referenceHeight) {
        return (int) (referenceHeight * ratio * scaling());
    }

    /**
     * sets the new referenceHeight and redraws this view.
     *
     * @param height the new referenceHeight
     */
    public void setReferenceHeight(int height) {
        this.referenceHeight = height;
        recalculateDimensions(referenceHeight);
        invalidate();
    }

    /**
     * @return the Id of the Image used for displaying this
     */
    protected abstract int drawableId();

    /**
     * init will be called in every construnctor and loads every data for this view and sets it up
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    protected void init(Context context, @Nullable AttributeSet attrs) {
        beforeInit(context, attrs);
        loadTexture();
        onInit(context, attrs);
    }

    /**
     * loads the Texture from the id into a bitmap and sets the ration.
     */
    protected void loadTexture() {
        image = BitmapFactory.decodeResource(getResources(), drawableId());
        ratio = (float) image.getWidth() / (float) image.getHeight();
    }

    /**
     * beforeInit is called before the texture is loaded.
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    protected void beforeInit(Context context, @Nullable AttributeSet attrs) {
    }

    /**
     * onInit is called after init and can be used to setup this view with i.e. listener.
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
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

    /**
     * onTouchArea will be called, when the onTouchEvent methode is triggered.
     * this method triggers all TouchAreaListeners, that are at the position of the touch.
     *
     * @param event           the event from the onTouchAreaEvent.
     * @param xOffset         if the listeners only use an piece of the whole view the offset from the bottom
     * @param yOffset         if the listeners only use an piece of the whole view the offset from the left
     * @param width           the width used for the area listener.
     * @param height          the height used for the area listener.
     * @param referenceHeight the referenceHeight of the view
     * @return true if any listener returns true and an interaction with this view happens.
     */
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

    /**
     * onDragArea will be called, when the onDragEvent methode is triggered.
     * this method triggers all OnDragAreaListeners.
     *
     * @param event           the event from the onDragEvent.
     * @param xOffset         if the listeners only use an piece of the whole view the offset from the bottom
     * @param yOffset         if the listeners only use an piece of the whole view the offset from the left
     * @param width           the width used for the area listener.
     * @param height          the height used for the area listener.
     * @param referenceHeight the referenceHeight of the view
     * @return true if any listener returns true and an interaction with this view happens, or the drag started to show interest of more information.
     */
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

    /**
     * @return the scaling this view should have relative to the referenceheight.
     */
    protected float scaling() {
        return 1.0f;
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        drawItemOnCanvas(canvas, 0, 0, referenceHeight, MeasureSpec.getSize(getMeasuredWidth()), MeasureSpec.getSize(getMeasuredHeight()));
        afterDraw();
    }

    /**
     * this methode will be called after the view is drawn on the canvas.
     */
    protected void afterDraw() {
    }

    /**
     * drawItemOnCanvas draws the view on the canvas.
     *
     * @param canvas          the canvas to draw the view on.
     * @param xOffset         the x offset where to draw the view on the canvas.
     * @param yOffset         the y offset where to draw the view on the canvas.
     * @param referenceHeight the referenceheight used to draw the items the correct size.
     * @param width           the width of the view size
     * @param height          the height of the view size
     */
    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, (int) (referenceHeight * scaling() * ratio), (int) (referenceHeight * scaling()), false);
        int centerWidth = (width - scaledBitmap.getWidth()) / 2;
        int centerHeight = height - scaledBitmap.getHeight();
        canvas.drawBitmap(scaledBitmap, centerWidth + xOffset, centerHeight - yOffset, null);
    }

    /**
     * a methode that draws the DragAreas and the Touch Areas on the canvas
     *
     * @param canvas          the canvas to draw the view on.
     * @param xOffset         the x offset where to draw the view on the canvas.
     * @param yOffset         the y offset where to draw the view on the canvas.
     * @param referenceHeight the referenceheight used to draw the items the correct size.
     * @param width           the width of the view size
     * @param height          the height of the view size
     */
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

    /**
     * drag starts dragging an  itemview. This object is used to draw it.
     *
     * @param itemView the ItemView to drag.
     * @return true if the drag was successfully and false if not.
     */
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
