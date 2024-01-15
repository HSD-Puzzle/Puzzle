package com.example.burger42.Game.StarChalanges;

import android.content.Context;

import com.example.burger42.Game.GamePuppeteer;
import com.example.burger42.Item.StarItem;
import com.example.burger42.R;

public class CorrectOrdersStar extends BasicStarItem {

    private int neededAmountOfCorrectOrders = 0;

    private int amountOfCorrectOrdersResult = -1;

    public CorrectOrdersStar(int neededAmountOfCorrectOrders) {
        this.neededAmountOfCorrectOrders = neededAmountOfCorrectOrders;
    }

    @Override
    public String degreeOfSuccess(Context context) {
        return amountOfCorrectOrdersResult + "/" + neededAmountOfCorrectOrders;
    }

    @Override
    public String title(Context context) {
        return context.getString(R.string.correctOrdersStarTitle1) + neededAmountOfCorrectOrders + context.getString(R.string.correctOrdersStarTitle2);
    }

    @Override
    public void calculate(GamePuppeteer.GameResultStatistics statistics) {
        amountOfCorrectOrdersResult = statistics.amountOfCorrectOrders();
        setIsDone(amountOfCorrectOrdersResult >= neededAmountOfCorrectOrders);
    }
}
