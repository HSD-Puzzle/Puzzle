package com.example.burger42.Game.Restaurant;

import android.content.Context;

import com.example.burger42.Game.Restaurant.Counter.BreadCounterPart;
import com.example.burger42.Game.Restaurant.Counter.CounterPartView;
import com.example.burger42.Game.Restaurant.Counter.MillCounterPart;

public class RestaurantLevel1 extends Restaurant {
    public RestaurantLevel1(Context context) {
        super(context);
    }

    @Override
    public CounterPartView[] bottomCounterParts(Context context) {
        return new CounterPartView[]
                {
                        new BreadCounterPart(context, this),
                        new MillCounterPart(context, this)
                };
    }

    @Override
    public CounterPartView[] topCounterParts(Context context) {
        return new CounterPartView[0];
    }

}
