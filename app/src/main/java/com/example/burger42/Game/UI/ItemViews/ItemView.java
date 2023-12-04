package com.example.burger42.Game.UI.ItemViews;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class ItemView extends View {

    private Bitmap image;
    private float ratio;

    private int referenceHeight;

    private ItemAbove[] itemsAbove;

    public ItemView(Context context) {
        super(context);
        init(context, null);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void init(Context context, @Nullable AttributeSet attrs) {
        image = BitmapFactory.decodeResource(getResources(), drawableId());
        ratio = (float) image.getWidth() / (float) image.getHeight();
        itemsAbove = itemAboveSetUp();
        onInit(context, attrs);
    }

    protected ItemAbove[] itemAboveSetUp() {
        return new ItemAbove[]{new ItemAbove() {
            @Override
            protected int xOffset(int reference) {
                return 0;
            }

            @Override
            protected int yOffset(int reference) {
                return (int) (reference * 0.1f);
            }
        }};
    }

    protected void onInit(Context context, @Nullable AttributeSet attrs) {
    }

    protected abstract int drawableId();

    public void setItemAbove(int index, ItemView itemAbove) {
        this.itemsAbove[index].setItemAbove(itemAbove);
    }

    public void setItemAbove(ItemView itemAbove) {
        setItemAbove(0, itemAbove);
    }

    public boolean hasItemAbove(int index) {
        return itemsAbove[index].itemAbove() != null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        referenceHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) (referenceHeight * ratio * scaling()), calculatedFullHeight(referenceHeight));
    }

    protected int calculatedFullHeight(int referenceHeight) {
        int itemAboveHeight = 0;
        for (ItemAbove x : itemsAbove) {
            if (x.itemAbove != null) {
                int temp = x.itemAbove.calculatedFullHeight(referenceHeight) + x.yOffset(referenceHeight);
                if (temp > itemAboveHeight)
                    itemAboveHeight = temp;
            }
        }
        return Math.max(itemAboveHeight, (int) (referenceHeight * scaling()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawItemOnCanvas(canvas, 0, 0, referenceHeight, MeasureSpec.getSize(getMeasuredWidth()), MeasureSpec.getSize(getMeasuredHeight()));
    }

    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, (int) (referenceHeight * scaling() * ratio), (int) (referenceHeight * scaling()), false);
        int centerWidth = (width - scaledBitmap.getWidth()) / 2;
        int centerHeight = height - scaledBitmap.getHeight();

        canvas.drawBitmap(scaledBitmap, centerWidth + xOffset, centerHeight - yOffset, null);
        for (ItemAbove x : itemsAbove) {
            if (x.itemAbove != null) {
                x.itemAbove.drawItemOnCanvas(canvas, xOffset + x.xOffset(referenceHeight), yOffset + x.yOffset(referenceHeight), referenceHeight, width, height);
            } else if (isInEditMode()) {
                int halfSize = referenceHeight / 30;
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                int widthCenter = scaledBitmap.getWidth() / 2;
                int heightCenter = scaledBitmap.getHeight();
                canvas.drawRect(widthCenter + xOffset + x.xOffset(referenceHeight) - halfSize, heightCenter - yOffset - x.yOffset(referenceHeight) - halfSize * 2, widthCenter + xOffset + x.xOffset(referenceHeight) + halfSize, heightCenter - yOffset - x.yOffset(referenceHeight), paint);
            }
        }
    }

    protected float scaling() {
        return 1.0f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            DragShadowBuilder shadowBuilder = dragShadow(referenceHeight);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                startDragAndDrop(data, shadowBuilder, this, DRAG_FLAG_OPAQUE);
            } else {
                startDrag(data, shadowBuilder, this, 1);
            }
            return true;
        }
        return false;
    }

    public DragShadow dragShadow(int referenceHeight) {
        return new DragShadow(referenceHeight);
    }

    public class DragShadow extends DragShadowBuilder {
        private int referenceHeight;

        DragShadow(int referenceHeight) {
            this.referenceHeight = referenceHeight;
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            drawItemOnCanvas(canvas, 0, 0, referenceHeight, canvas.getWidth(), canvas.getHeight());
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            shadowSize.set((int) (referenceHeight * ratio * scaling()), calculatedFullHeight(referenceHeight));
            shadowTouchPoint.set(shadowSize.x / 2, shadowSize.y / 2);
            super.onProvideShadowMetrics(
                    shadowSize,
                    shadowTouchPoint);
        }
    }

    protected abstract class ItemAbove {
        private ItemView itemAbove;

        protected abstract int xOffset(int reference);

        protected abstract int yOffset(int reference);

        public ItemView itemAbove() {
            return itemAbove;
        }

        public void setItemAbove(ItemView itemAbove) {
            this.itemAbove = itemAbove;
        }
    }
}
