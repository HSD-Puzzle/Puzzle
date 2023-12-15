package com.example.burger42.Game.UI;

import com.example.burger42.Game.UI.CounterViews.BreadCounterView;
import com.example.burger42.Game.UI.CounterViews.MillCounterView;
import com.example.burger42.Game.UI.CounterViews.TopRecepieCounter;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.MainActivity;

public class RestaurantFragmentLevel1 extends RestaurantFragment {
    public RestaurantFragmentLevel1(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    protected CounterView[] bottomCounter() {
        return new CounterView[]{new BreadCounterView(mainActivity), new MillCounterView(mainActivity), new BreadCounterView(mainActivity), new MillCounterView(mainActivity), new BreadCounterView(mainActivity), new MillCounterView(mainActivity)};
    }

    @Override
    protected CounterView[] topCounter() {
        return new CounterView[]{new TopRecepieCounter(mainActivity), new TopRecepieCounter(mainActivity), new TopRecepieCounter(mainActivity), new TopRecepieCounter(mainActivity)};
    }
}
