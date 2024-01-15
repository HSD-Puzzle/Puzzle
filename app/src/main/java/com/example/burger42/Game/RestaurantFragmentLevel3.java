package com.example.burger42.Game;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.UI.CounterViews.BottomEndCounterView;
import com.example.burger42.Game.UI.CounterViews.BottomStartCounterView;
import com.example.burger42.Game.UI.CounterViews.BreadCounterView;
import com.example.burger42.Game.UI.CounterViews.CheeseCounterView;
import com.example.burger42.Game.UI.CounterViews.MillCounterView;
import com.example.burger42.Game.UI.CounterViews.RawPattyCounterView;
import com.example.burger42.Game.UI.CounterViews.SaladCounterView;
import com.example.burger42.Game.UI.CounterViews.StoveCounterView;
import com.example.burger42.Game.UI.CounterViews.TopEndCounterView;
import com.example.burger42.Game.UI.CounterViews.TopRecepieCounter;
import com.example.burger42.Game.UI.CounterViews.TopStartCounterView;
import com.example.burger42.Game.UI.CounterViews.TrashCounter;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class RestaurantFragmentLevel3 extends RestaurantFragment {
    public RestaurantFragmentLevel3(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public GamePuppeteer gamePuppeteer() {
        return new GamePuppeteer(this, new RecipeGenerator(1));
    }

    @Override
    protected CounterView[] bottomCounter() {
        return new CounterView[]{new BottomStartCounterView(mainActivity), new MillCounterView(mainActivity), new TrashCounter(mainActivity), new BreadCounterView(mainActivity), new SaladCounterView(mainActivity), new CheeseCounterView(mainActivity), new StoveCounterView(mainActivity), new RawPattyCounterView(mainActivity), new BottomEndCounterView(mainActivity)};
    }

    @Override
    protected CounterView[] topCounter() {
        return new CounterView[]{new TopStartCounterView(mainActivity), new TopRecepieCounter(mainActivity), new TopRecepieCounter(mainActivity), new TopRecepieCounter(mainActivity), new TopRecepieCounter(mainActivity), new TopRecepieCounter(mainActivity), new TopEndCounterView(mainActivity)};
    }

    @Override
    public String title() {
        return mainActivity.getString(R.string.level3Title);
    }

    @Override
    public int thumbnailId() {
        return R.drawable.thumbnail3;
    }
}
