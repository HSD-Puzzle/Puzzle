package com.example.burger42.Game.StarChalanges;

import android.content.Context;

import com.example.burger42.Game.GamePuppeteer;
import com.example.burger42.Item.StarItem;
import com.example.burger42.R;

public class TipForEveryBurgerStar implements StarItem {

    private int neededAmountBurgers = 0;

    private int burgerResult = -1;

    private boolean tipForEveryBurger = true;

    public TipForEveryBurgerStar(int neededAmountBurgers) {
        this.neededAmountBurgers = neededAmountBurgers;
    }

    @Override
    public boolean done() {
        return burgerResult >= neededAmountBurgers && tipForEveryBurger;
    }

    @Override
    public String degreeOfSuccess(Context context) {
        return "";//String.format("%.2f", streakResult) + "/" + String.format("%.2f", neededStreak);
    }

    @Override
    public String title(Context context) {
        return context.getString(R.string.alwaysTipStarTitle1) + neededAmountBurgers + context.getString(R.string.alwaysTipStarTitle2);
    }

    @Override
    public void calculate(GamePuppeteer.GameResultStatistics statistics) {
        burgerResult = statistics.income();
    }
}
