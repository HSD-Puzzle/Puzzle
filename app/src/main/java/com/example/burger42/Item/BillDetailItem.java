package com.example.burger42.Item;

public class BillDetailItem {
    private final String name;
    private final int beforeStreak;

    private final float streak;
    private final boolean isDone;


    public BillDetailItem(String name, int beforeStreak, float streak, boolean isDone) {
        this.name = name;
        this.beforeStreak = beforeStreak;
        this.streak = streak;
        this.isDone = isDone;
    }

    public float streak() {
        return streak;
    }

    public String name() {
        return name;
    }

    public int beforeStreak() {
        return beforeStreak;
    }

    public boolean isDone() {
        return isDone;
    }
}
