package com.example.burger42.Game.Restaurant.Item;

import android.content.Context;

import com.example.burger42.Game.Restaurant.Restaurant;
import com.example.burger42.R;

public class TopBreadItem extends ItemView {
    public TopBreadItem(Context context, Restaurant restaurant) {
        super(context, restaurant);
    }

    @Override
    protected int drawableId() {
        return R.drawable.topbread;
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
