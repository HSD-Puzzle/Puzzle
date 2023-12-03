package com.example.burger42.Game.Restaurant.Counter;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Restaurant.Item.BottomBreadItem;
import com.example.burger42.Game.Restaurant.Item.TopBreadItem;
import com.example.burger42.Game.Restaurant.Restaurant;
import com.example.burger42.R;

public class BreadCounterPart extends CounterPartView {

    public BreadCounterPart(Context context, Restaurant restaurant) {
        super(context, restaurant);
    }

    @Override
    public void init(@Nullable AttributeSet attrs, Context context) {
        super.init(attrs, context);
    }

    @Override
    protected int drawableId() {
        return R.drawable.burgerbreadcorner;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view;
            if (event.getX() < 140) {
                view = new BottomBreadItem(getContext(), restaurant);

            } else {
                view = new TopBreadItem(getContext(), restaurant);
            }
            addView(view);
            view.setTranslationX(event.getX());
            view.setTranslationY(event.getY());
        }
        return true;
    }

}
