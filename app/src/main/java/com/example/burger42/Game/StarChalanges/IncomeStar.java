package com.example.burger42.Game.StarChalanges;

import android.content.Context;

import com.example.burger42.Game.GamePuppeteer;
import com.example.burger42.Item.StarItem;
import com.example.burger42.R;

public class IncomeStar extends BasicStarItem {

    private int neededIncome = 0;

    private int incomeResult = -1;

    public IncomeStar(int neededIncome) {
        this.neededIncome = neededIncome;
    }

    @Override
    public String degreeOfSuccess(Context context) {
        return incomeResult + "$/" + neededIncome + "$";
    }

    @Override
    public String title(Context context) {
        return context.getString(R.string.incomeStarTitle) + neededIncome + "$.";
    }

    @Override
    public void calculate(GamePuppeteer.GameResultStatistics statistics) {
        incomeResult = statistics.income();
        setIsDone(incomeResult >= neededIncome);
    }
}
