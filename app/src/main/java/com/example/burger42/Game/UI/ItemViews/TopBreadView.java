package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.RestaurantLevel.RestaurantFragment;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

public class TopBreadView extends ItemView {


    public TopBreadView(Context context, RestaurantFragment restaurantFragment) {
        super(context, restaurantFragment);
    }

    public TopBreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int drawableId() {
        return R.drawable.topbread;
    }

    @Override
    protected float scaling() {
        return 127f / 500f;
    }


    @Override
    public boolean setItemAbove(int index, ItemView itemAbove) {
        return false;
    }

    @Override
    public boolean hasItemAbove(int index) {
        return false;
    }

    @Override
    protected ItemAbove[] itemAboveSetUp() {
        return new ItemAbove[0];
    }
}
