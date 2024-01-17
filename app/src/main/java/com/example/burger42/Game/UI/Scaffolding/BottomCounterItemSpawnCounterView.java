package com.example.burger42.Game.UI.Scaffolding;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.Queue;

public abstract class BottomCounterItemSpawnCounterView extends CounterView implements BottomCounterItemSpawn {
    public BottomCounterItemSpawnCounterView(Context context) {
        super(context);
    }

    public BottomCounterItemSpawnCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean drawn = false;

    private Queue<ItemView> notPlaced = new LinkedList<>();


    @Override
    public void placeItemOnCounter(ItemView itemView) {
        System.out.println("Spawn " + itemView);
        restaurantFragment.addItem(itemView);
        if (drawn) {
            itemView.setTranslationY(getHeight() * 0.94f);
            itemView.setTranslationX((int) Math.max(getX() + getWidth() * Math.random() - itemView.getCustomWidth() / 2f, 0));
        } else {
            notPlaced.add(itemView);
        }
    }

    @Override
    protected void afterDraw() {
        super.afterDraw();
        drawn = true;
        while (!notPlaced.isEmpty()) {
            ItemView itemView = notPlaced.poll();
            itemView.setTranslationY(getHeight() * 0.94f);
            itemView.setTranslationX((float) (getX() + Math.random() * getWidth()));
        }
    }

    @Override
    protected void onRestaurantBound(RestaurantFragment restaurantFragment) {
        restaurantFragment.addBottomCounterItemSpawn(this);
    }
}
