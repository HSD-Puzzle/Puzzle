package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.CheeseView;
import com.example.burger42.Game.UI.ItemViews.SaladView;
import com.example.burger42.Game.UI.Scaffolding.BottomCounterItemSpawnCounterView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

public class CheeseCounterView extends BottomCounterItemSpawnCounterView {

    public CheeseCounterView(Context context) {
        super(context);
    }

    public CheeseCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnTouchAreaListener(new OnTouchAreaListener(0.7f, 0.3f, 0.05f, 0.95f) {
            @Override
            protected boolean onTouch(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ItemView itemView = new CheeseView(getContext());
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
        return R.drawable.cheesecorner;
    }
}
