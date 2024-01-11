package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.BurgerPattyView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

public class RawPattyCounterView extends CounterView {

    public RawPattyCounterView(Context context) {
        super(context);
    }

    public RawPattyCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnTouchAreaListener(new OnTouchAreaListener(0.7f, 0.3f, 0.023f, 0.5f) {
            @Override
            protected boolean onTouch(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ItemView itemView = new BurgerPattyView(getContext());
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
        return R.drawable.rawburgercorner;
    }
}
