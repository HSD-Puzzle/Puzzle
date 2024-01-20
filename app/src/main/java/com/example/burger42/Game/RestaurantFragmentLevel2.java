package com.example.burger42.Game;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.StarChalanges.CorrectBurgerStar;
import com.example.burger42.Game.StarChalanges.IncomeStar;
import com.example.burger42.Game.StarChalanges.StreakMultiplierStar;
import com.example.burger42.Game.UI.CounterViews.BottomEndCounterView;
import com.example.burger42.Game.UI.CounterViews.BottomStartCounterView;
import com.example.burger42.Game.UI.CounterViews.BreadCounterView;
import com.example.burger42.Game.UI.CounterViews.CheeseCounterView;
import com.example.burger42.Game.UI.CounterViews.PlateCounterView;
import com.example.burger42.Game.UI.CounterViews.RawPattyCounterView;
import com.example.burger42.Game.UI.CounterViews.SaladCounterView;
import com.example.burger42.Game.UI.CounterViews.StoveCounterView;
import com.example.burger42.Game.UI.CounterViews.TopEndCounterView;
import com.example.burger42.Game.UI.CounterViews.TopRecipeCounter;
import com.example.burger42.Game.UI.CounterViews.TopStartCounterView;
import com.example.burger42.Game.UI.CounterViews.TrashCounter;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Item.StarItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

/**
 * The specific settings for the {@link RestaurantFragment} of level2
 * A detailed description of what a methode does can find in {@link RestaurantFragment}.
 */
public class RestaurantFragmentLevel2 extends RestaurantFragment {
    public RestaurantFragmentLevel2(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public GamePuppeteer gamePuppeteer() {
        return new GamePuppeteer(this, new RecipeGenerator(2));
    }

    @Override
    protected CounterView[] bottomCounter() {
        return new CounterView[]{new BottomStartCounterView(mainActivity), new TrashCounter(mainActivity), new PlateCounterView(mainActivity, 20), new BreadCounterView(mainActivity), new SaladCounterView(mainActivity), new CheeseCounterView(mainActivity), new StoveCounterView(mainActivity), new RawPattyCounterView(mainActivity), new BottomEndCounterView(mainActivity)};
    }

    @Override
    protected CounterView[] topCounter() {
        return new CounterView[]{new TopStartCounterView(mainActivity), new TopRecipeCounter(mainActivity), new TopRecipeCounter(mainActivity), new TopRecipeCounter(mainActivity), new TopRecipeCounter(mainActivity), new TopRecipeCounter(mainActivity), new TopEndCounterView(mainActivity)};
    }

    @Override
    public String title() {
        return mainActivity.getString(R.string.level2Title);
    }

    @Override
    public int thumbnailId() {
        return R.drawable.thumbnail2;
    }

    @Override
    protected StarItem[] createStarItems() {
        return new StarItem[]{new IncomeStar(800), new CorrectBurgerStar(10), new StreakMultiplierStar(1.8f)};
    }

    @Override
    protected String levelId() {
        return "level2";
    }
}
