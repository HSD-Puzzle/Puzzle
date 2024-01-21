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

/**
 * The implementation of the trash counter view, that allows items to be thrown away
 */
public class TrashCounter extends BottomCounterItemSpawnCounterView {

    /**
     * the status off the trash bin open(true) or closed(false)
     */
    private boolean isOpen = false;

    public TrashCounter(Context context) {
        super(context);
    }

    public TrashCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * adds a Listener, that allows to open and close the trash bin
     * and adds a Listener, that clean the plates and tablets
     * and adds a Listener, that removes dropped items
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
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

    /**
     * set the trash-bin open or closed and redraws it.
     *
     * @param value true if the trash-bin should be open else false.
     */
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
