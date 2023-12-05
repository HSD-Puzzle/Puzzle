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

import com.example.burger42.Game.UI.CustomView;
import com.example.burger42.Game.UI.RestaurantLevel.RestaurantFragment;

import java.util.List;

public abstract class ItemView extends CustomView {

    private List<TouchArea> touchAreas;
    private List<DragArea> dragAreas;

    private ItemAbove[] itemsAbove;

    private ItemAbove[] itemsAbove() {
        if (itemsAbove == null) {
            itemsAbove = itemAboveSetUp();
            for (int i = 0; i < itemsAbove.length; i++) {
                itemsAbove[i].setIndex(i);
            }
        }
        return itemsAbove;
    }

    public ItemView(Context context, RestaurantFragment restaurantFragment) {
        super(context, restaurantFragment);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
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

    protected abstract int drawableId();

    private float translationY;

    @Override
    public void setTranslationY(float translationY) {
        this.translationY = translationY;
        super.setTranslationY(translationY - height(restaurantFragment.itemSize()));
    }

    public boolean setItemAbove(int index, ItemView itemAbove) {
        if (hasItemAbove(index)) {
            return this.itemsAbove()[index].itemAbove.setItemAbove(itemAbove);
        } else {
            this.itemsAbove()[index].setItemAbove(itemAbove);
        }
        height = -1;
        setTranslationY(translationY);
        return true;
    }

    public boolean setItemAbove(ItemView itemAbove) {
        return setItemAbove(0, itemAbove);
    }

    public boolean hasItemAbove(int index) {
        return itemsAbove()[index].itemAbove() != null;
    }

    protected int calculatedFullHeight(int referenceHeight) {
        int itemAboveHeight = 0;
        for (ItemAbove x : itemsAbove()) {
            if (x.itemAbove() != null) {
                int temp = x.itemAbove().calculatedFullHeight(referenceHeight) + x.yOffset(referenceHeight);
                if (temp > itemAboveHeight)
                    itemAboveHeight = temp;
            }
        }
        return Math.max(itemAboveHeight, (int) (referenceHeight * scaling()));
    }

    protected void drawItemOnCanvas(Canvas canvas, float xOffset, float yOffset, int referenceHeight, int width, int height) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, (int) (referenceHeight * scaling() * ratio), (int) (referenceHeight * scaling()), false);
        int centerWidth = (width - scaledBitmap.getWidth()) / 2;
        int centerHeight = height - scaledBitmap.getHeight();

        canvas.drawBitmap(scaledBitmap, centerWidth + xOffset, centerHeight - yOffset, null);
        for (ItemAbove x : itemsAbove()) {
            if (x.itemAbove() != null) {
                x.itemAbove().drawItemOnCanvas(canvas, xOffset + x.xOffset(referenceHeight), yOffset + x.yOffset(referenceHeight), referenceHeight, width, height);
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

    @Override
    public List<DragArea> dragAreas() {
        if (dragAreas == null) {
            dragAreas = super.dragAreas();
            for (ItemAbove x : itemsAbove()) {
                dragAreas.add(x.dragArea());
            }
        }
        return dragAreas;
    }

    private ItemView item() {
        return this;
    }

    @Override
    public List<TouchArea> touchAreas() {
        if (touchAreas == null) {
            touchAreas = super.touchAreas();
            touchAreas.add(new TouchArea(0, 1, 0, 1) {
                @Override
                protected boolean onTouchEvent(MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        DragShadowBuilder shadowBuilder = dragShadow(referenceHeight);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            startDragAndDrop(data, shadowBuilder, item(), DRAG_FLAG_OPAQUE);
                        } else {
                            startDrag(data, shadowBuilder, item(), 1);
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
        return touchAreas;
    }

    public DragShadow dragShadow(int referenceHeight) {
        return new DragShadow(referenceHeight);
    }

    public class DragShadow extends DragShadowBuilder {
        private final int referenceHeight;

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

        final float top;
        final float bottom;
        final float right;
        final float left;

        private ItemView itemAbove;

        private int index;

        private DragArea dragArea;


        private void setIndex(int index) {
            this.index = index;
        }

        protected ItemAbove(float top, float bottom, float left, float right) {
            this.top = top;
            this.bottom = bottom;
            this.right = right;
            this.left = left;
        }

        protected ItemAbove() {
            this(0, 1, 0, 1);
        }

        private DragArea dragArea() {
            if (dragArea == null) {
                dragArea = new DragArea(top, bottom, left, right) {
                    @Override
                    protected boolean onDragEvent(DragEvent event) {
                        if (event.getAction() == DragEvent.ACTION_DROP) {
                            ItemView itemView = (ItemView) event.getLocalState();
                            if (item().setItemAbove(index, itemView)) {
                                restaurantFragment.removeItem(itemView);
                                requestLayout();
                                invalidate();
                                return true;
                            } else
                                return false;
                        }
                        return true;
                    }
                };
            }
            return dragArea;
        }

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
