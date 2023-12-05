package com.example.burger42.Game.UI.RestaurantLevel;

import android.content.Context;

import com.example.burger42.Game.UI.Counter.BreadCounter;
import com.example.burger42.Game.UI.Counter.CounterView;
import com.example.burger42.Game.UI.ItemViews.ItemView;
import com.example.burger42.Game.UI.ItemViews.TabletView;
import com.example.burger42.MainActivity;

public class RestaurantLevel1 extends RestaurantFragment {
    public RestaurantLevel1(MainActivity mainActivity) {
        super(mainActivity);
        ItemView itemView = new TabletView(mainActivity, this);
        addItem(itemView);
    }

    @Override
    public CounterView[] bottomCounterParts(Context context) {
        return new CounterView[]{new BreadCounter(context, this), new BreadCounter(context, this), new BreadCounter(context, this), new BreadCounter(context, this)};
    }

    @Override
    public CounterView[] topCounterParts(Context context) {
        return new CounterView[0];
    }
}
