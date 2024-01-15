package com.example.burger42.Game.StarChalanges;

import android.content.Context;

import com.example.burger42.Game.GamePuppeteer;
import com.example.burger42.Item.StarItem;
import com.example.burger42.R;

public class StreakMultiplierStar implements StarItem {

    private float neededStreak = 0;

    private float streakResult = -1;

    public StreakMultiplierStar(float neededStreak) {
        this.neededStreak = neededStreak;
    }

    @Override
    public boolean done() {
        return streakResult >= neededStreak;
    }

    @Override
    public String degreeOfSuccess(Context context) {
        return String.format("%.2f", streakResult) + "/" + String.format("%.2f", neededStreak);
    }

    @Override
    public String title(Context context) {
        return context.getString(R.string.streakStarTitle) + String.format("%.2f", neededStreak) + ".";
    }

    @Override
    public void calculate(GamePuppeteer.GameResultStatistics statistics) {
        streakResult = statistics.largestStreakAchieved();
    }
}
