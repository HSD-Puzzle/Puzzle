package com.example.burger42.Game.UI.Counter;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.burger42.Game.OnTouchListenerDragable;
import com.example.burger42.Game.UI.ItemViews.BottomBreadView;
import com.example.burger42.Game.UI.ItemViews.ItemView;
import com.example.burger42.Game.UI.ItemViews.TabletView;
import com.example.burger42.Game.UI.ItemViews.TopBreadView;
import com.example.burger42.R;

import java.util.List;
import java.util.zip.Deflater;

public class BreadCounter extends CounterView {
    public BreadCounter(Context context) {
        super(context);
    }

    public BreadCounter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BreadCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BreadCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int drawableId() {
        return R.drawable.burgerbreadcorner;
    }

    private List<TouchArea> touchAreas;

    @Override
    public List<TouchArea> touchAreas() {
        if (touchAreas == null) {
            touchAreas = super.touchAreas();
            touchAreas.add(new TouchArea(0.63f, 0.93f, 0.03f, 0.49f) {
                @Override
                boolean onTouchEvent(MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ItemView itemView = new BottomBreadView(getContext());
                        drag(itemView);
                        return true;
                    }
                    return false;
                }
            });

            touchAreas.add(new TouchArea(0.63f, 0.93f, 0.51f, 0.96f) {
                @Override
                boolean onTouchEvent(MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ItemView itemView = new TopBreadView(getContext());
                        drag(itemView);
                        return true;
                    }
                    return false;
                }
            });

        }
        return touchAreas;
    }

    private void drag(ItemView itemView) {
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = itemView.dragShadow(200);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startDragAndDrop(data, shadowBuilder, itemView, DRAG_FLAG_OPAQUE);
        } else {
            startDrag(data, shadowBuilder, itemView, 1);
        }
    }
}
