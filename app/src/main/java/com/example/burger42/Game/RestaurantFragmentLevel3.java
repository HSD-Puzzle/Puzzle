package com.example.burger42.Game;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.StarChalanges.CorrectOrdersStar;
import com.example.burger42.Game.StarChalanges.IncomeStar;
import com.example.burger42.Game.StarChalanges.TipStar;
import com.example.burger42.Game.UI.CounterViews.BottomEndCounterView;
import com.example.burger42.Game.UI.CounterViews.BottomStartCounterView;
import com.example.burger42.Game.UI.CounterViews.BreadCounterView;
import com.example.burger42.Game.UI.CounterViews.CheeseCounterView;
import com.example.burger42.Game.UI.CounterViews.MillCounterView;
import com.example.burger42.Game.UI.CounterViews.PlateCounterView;
import com.example.burger42.Game.UI.CounterViews.RawPattyCounterView;
import com.example.burger42.Game.UI.CounterViews.SaladCounterView;
import com.example.burger42.Game.UI.CounterViews.StoveCounterView;
import com.example.burger42.Game.UI.CounterViews.TopEndCounterView;
import com.example.burger42.Game.UI.CounterViews.TopRecipeCounter;
import com.example.burger42.Game.UI.CounterViews.TopStartCounterView;
import com.example.burger42.Game.UI.CounterViews.TrashCounter;
import com.example.burger42.Game.UI.ItemViews.PlateView;
import com.example.burger42.Game.UI.ItemViews.TabletView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Item.StarItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * The specific settings for the {@link RestaurantFragment} of level3
 * A detailed description of what a methode does can find in {@link RestaurantFragment}.
 */
public class RestaurantFragmentLevel3 extends RestaurantFragment {
    public RestaurantFragmentLevel3(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public GamePuppeteer gamePuppeteer() {
        return new GamePuppeteer(this, new RecipeGenerator(3));
    }

    @Override
    protected CounterView[] bottomCounter() {
        return new CounterView[]{new BottomStartCounterView(mainActivity), new TrashCounter(mainActivity), new MillCounterView(mainActivity), new PlateCounterView(mainActivity, 2), new BreadCounterView(mainActivity), new SaladCounterView(mainActivity), new CheeseCounterView(mainActivity), new StoveCounterView(mainActivity), new RawPattyCounterView(mainActivity), new BottomEndCounterView(mainActivity)};
    }

    @Override
    protected CounterView[] topCounter() {
        return new CounterView[]{new TopStartCounterView(mainActivity), new TopRecipeCounter(mainActivity), new TopRecipeCounter(mainActivity), new TopRecipeCounter(mainActivity), new TopRecipeCounter(mainActivity), new TopRecipeCounter(mainActivity), new TopEndCounterView(mainActivity)};
    }

    @Override
    public String title() {
        return mainActivity.getString(R.string.level3Title);
    }

    @Override
    public int thumbnailId() {
        return R.drawable.thumbnail3;
    }

    @Override
    protected StarItem[] createStarItems() {
        return new StarItem[]{new IncomeStar(1000), new CorrectOrdersStar(6), new TipStar(500)};
    }

    @Override
    protected String levelId() {
        return "level3";
    }

    @Override
    protected Collection<ItemView> itemsToSpawnAtStart() {
        List<ItemView> itemViews = new LinkedList<>();
        PlateView dirtyPlateView = new PlateView(mainActivity, PlateView.state.DIRTY);
        dirtyPlateView.setItemAbove(2, new PlateView(mainActivity, PlateView.state.DIRTY));

        TabletView tabletView = new TabletView(mainActivity);
        itemViews.add(tabletView);
        for (int i = 0; i < 5; i++) {
            TabletView nextTabletView = new TabletView(mainActivity);
            tabletView.setItemAbove(2, nextTabletView);
            tabletView = nextTabletView;
        }
        itemViews.add(dirtyPlateView);
        return itemViews;
    }
}
