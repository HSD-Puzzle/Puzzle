package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.BottomBreadView;
import com.example.burger42.Game.UI.ItemViews.TopBreadView;
import com.example.burger42.Game.UI.Scaffolding.BottomCounterItemSpawnCounterView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

/**
 * The implementation of the bread counter view
 */
public class BreadCounterView extends BottomCounterItemSpawnCounterView {

    public BreadCounterView(Context context) {
        super(context);
    }

    public BreadCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * adds TouchListeners, that summon new bread ItemViews
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnTouchAreaListener(new OnTouchAreaListener(0.6f, 0.1f, 0.023f, 0.48f) {
            @Override
            protected boolean onTouch(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ItemView itemView = new BottomBreadView(getContext());
                    itemView.setReferenceHeight(referenceHeight);
                    drag(itemView);
                    return true;
                }
                return false;
            }
        });
        addOnTouchAreaListener(new OnTouchAreaListener(0.6f, 0.1f, 0.5f, 0.97f) {
            @Override
            protected boolean onTouch(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ItemView itemView = new TopBreadView(getContext());
                    itemView.setReferenceHeight(referenceHeight);
                    drag(itemView);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int drawableId() {
        return R.drawable.burgerbreadcorner;
    }
}
