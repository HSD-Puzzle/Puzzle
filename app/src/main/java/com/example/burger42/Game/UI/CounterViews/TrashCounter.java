package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.CheeseView;
import com.example.burger42.Game.UI.Scaffolding.BottomCounterItemSpawnCounterView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

public class TrashCounter extends BottomCounterItemSpawnCounterView {

    private boolean isOpen = false;

    public TrashCounter(Context context) {
        super(context);
    }

    public TrashCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnTouchAreaListener(new OnTouchAreaListener(0.7f, 0.2f, 0.05f, 0.95f) {
            @Override
            protected boolean onTouch(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setIsOpen(!isOpen);
                    return true;
                }
                return false;
            }
        });

        addOnDragAreaListener(new OnDragAreaListener(0.7f, 0.2f, 0.05f, 0.95f) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (isOpen && inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    ItemView view = (ItemView) event.getLocalState();
                    view.removeFromParent();
                    return true;
                }
                return false;
            }
        }.setUseFilter(true).addFilterTag(ItemView.ItemFilterTag.Ingredient));

        addOnDragAreaListener(new OnDragAreaListener(0.7f, 0.2f, 0.05f, 0.95f) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (isOpen && inArea && event.getAction() == DragEvent.ACTION_DRAG_LOCATION) {
                    ItemView view = (ItemView) event.getLocalState();
                    view.clearChildren();
                    return true;
                }
                return false;
            }
        }.setUseFilter(true).addFilterTag(ItemView.ItemFilterTag.CleanBase));
    }

    private void setIsOpen(boolean value) {
        isOpen = value;
        loadTexture();
        invalidate();
    }

    @Override
    protected int drawableId() {
        if (isOpen)
            return R.drawable.trashbinopen;
        else
            return R.drawable.trashbinclose;
    }
}
