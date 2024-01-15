package com.example.burger42.Game.StarChalanges;

import android.content.Context;

import com.example.burger42.Game.GamePuppeteer;
import com.example.burger42.Item.StarItem;
import com.example.burger42.R;

public class CorrectBurgerStar extends BasicStarItem {

    private int neededAmountOfCorrectBurger = 0;

    private int amountOfCorrectBurgerResult = -1;

    public CorrectBurgerStar(int neededAmountOfCorrectBurger) {
        this.neededAmountOfCorrectBurger = neededAmountOfCorrectBurger;
    }

    @Override
    public String degreeOfSuccess(Context context) {
        return amountOfCorrectBurgerResult + "/" + neededAmountOfCorrectBurger;
    }

    @Override
    public String title(Context context) {
        return context.getString(R.string.correctBurgerStarTitle1) + neededAmountOfCorrectBurger + context.getString(R.string.correctBurgerStarTitle2);
    }

    @Override
    public void calculate(GamePuppeteer.GameResultStatistics statistics) {
        amountOfCorrectBurgerResult = statistics.amountOfSoldBurger();
        setIsDone(amountOfCorrectBurgerResult >= neededAmountOfCorrectBurger);
    }
}
