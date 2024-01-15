package com.example.burger42.Game;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.StarChalanges.CorrectOrdersStar;
import com.example.burger42.Game.StarChalanges.IncomeStar;
import com.example.burger42.Game.StarChalanges.StreakMultiplierStar;
import com.example.burger42.Game.StarChalanges.TipStar;
import com.example.burger42.Game.UI.CounterViews.BottomEndCounterView;
import com.example.burger42.Game.UI.CounterViews.BottomStartCounterView;
import com.example.burger42.Game.UI.CounterViews.BreadCounterView;
import com.example.burger42.Game.UI.CounterViews.PlateCounterView;
import com.example.burger42.Game.UI.CounterViews.RawPattyCounterView;
import com.example.burger42.Game.UI.CounterViews.StoveCounterView;
import com.example.burger42.Game.UI.CounterViews.TopEndCounterView;
import com.example.burger42.Game.UI.CounterViews.TopRecepieCounter;
import com.example.burger42.Game.UI.CounterViews.TopStartCounterView;
import com.example.burger42.Game.UI.CounterViews.TrashCounter;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Item.StarItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class RestaurantFragmentLevel1 extends RestaurantFragment {
    public RestaurantFragmentLevel1(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public GamePuppeteer gamePuppeteer() {
        return new GamePuppeteer(this, new RecipeGenerator(1));
    }

    @Override
    protected String levelId() {
        return "level1";
    }

    @Override
    public String title() {
        return mainActivity.getString(R.string.level1Title);
    }

    @Override
    public int thumbnailId() {
        return R.drawable.thumbnail1;
    }

    @Override
    protected StarItem[] createStarItems() {
        return new StarItem[]{new IncomeStar(400), new TipStar(100), new StreakMultiplierStar(1.5f)};
    }

    @Override
    protected CounterView[] bottomCounter() {
        return new CounterView[]{new BottomStartCounterView(mainActivity), new TrashCounter(mainActivity), new PlateCounterView(mainActivity, 20), new BreadCounterView(mainActivity), new StoveCounterView(mainActivity), new RawPattyCounterView(mainActivity), new BottomEndCounterView(mainActivity)};
    }

    @Override
    protected CounterView[] topCounter() {
        return new CounterView[]{new TopStartCounterView(mainActivity), new TopRecepieCounter(mainActivity), new TopRecepieCounter(mainActivity), new TopRecepieCounter(mainActivity), new TopEndCounterView(mainActivity)};
    }
}