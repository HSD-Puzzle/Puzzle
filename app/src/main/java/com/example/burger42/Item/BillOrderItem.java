package com.example.burger42.Item;

import com.example.burger42.Game.Recipe;

public class BillOrderItem {
    private String name;
    private int beforeStreak;

    private float streak;
    private boolean isDone;


    public BillOrderItem(String name, int beforeStreak, float streak, boolean isDone) {
        this.name = name;
        this.beforeStreak = beforeStreak;
        this.streak = streak;
        this.isDone = isDone;
    }
}
