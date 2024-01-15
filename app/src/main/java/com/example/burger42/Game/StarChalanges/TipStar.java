package com.example.burger42.Game.StarChalanges;

import android.content.Context;

import com.example.burger42.Game.GamePuppeteer;
import com.example.burger42.Item.StarItem;
import com.example.burger42.R;

public class TipStar extends BasicStarItem {

    private int neededTip = 0;

    private int tipResult = -1;

    public TipStar(int neededTip) {
        this.neededTip = neededTip;
    }

    @Override
    public String degreeOfSuccess(Context context) {
        return tipResult + "$/" + neededTip + "$";
    }

    @Override
    public String title(Context context) {
        return context.getString(R.string.tipStarTitle) + neededTip + "$.";
    }

    @Override
    public void calculate(GamePuppeteer.GameResultStatistics statistics) {
        tipResult = statistics.amountOfTipValueReceived();
        setIsDone(tipResult >= neededTip);
    }
}
