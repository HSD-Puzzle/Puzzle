package com.example.burger42.Game.UI.Counter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.burger42.Game.OnTouchListenerDragable;
import com.example.burger42.Game.UI.ItemViews.ItemView;
import com.example.burger42.R;

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.println(Log.DEBUG, "BreadCounter", "ACTION_DRAG_STARTED");
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                Log.println(Log.DEBUG, "BreadCounter", "ACTION_DRAG_LOCATION");
                break;
            case DragEvent.ACTION_DROP:
                Log.println(Log.DEBUG, "BreadCounter", "ACTION_DROP");
                ItemView itemView = (ItemView) event.getLocalState();
                itemView.setTranslationY(getHeight() / 2.28f);
                itemView.setTranslationX(Math.max(getX() + event.getX() - itemView.getWidth() / 2, 0));
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                Log.println(Log.DEBUG, "BreadCounter", "ACTION_DRAG_ENDED");
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.println(Log.DEBUG, "BreadCounter", "ACTION_DRAG_ENTERED");
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                Log.println(Log.DEBUG, "BreadCounter", "ACTION_DRAG_EXITED");
                break;
            default:
                Log.println(Log.DEBUG, "BreadCounter", "OTHER");
                break;
        }
        return true;
    }
}
