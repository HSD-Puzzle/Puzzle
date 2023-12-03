package com.example.burger42.Game.Restaurant.Item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Restaurant.Restaurant;
import com.example.burger42.R;

public class BurgerPattyItem extends ItemView {


    public BurgerPattyItem(Context context, Restaurant restaurant) {
        super(context, restaurant);
    }

    @Override
    protected int drawableId() {
        return R.drawable.burgerpattyrare;
    }

    @Override
    protected float scale() {
        return 0.2f;
    }

    @Override
    protected float offsetY() {
        return -20;
    }
}
