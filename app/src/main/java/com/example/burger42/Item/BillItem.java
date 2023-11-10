package com.example.burger42.Item;

public class BillItem {
    private int amount;
    private String title;
    private int totalValue;

    public BillItem(int amount, String title, int totalValue) {
        this.amount = amount;
        this.totalValue = totalValue;
        this.title = title;
    }

    public int amount() {
        return amount;
    }

    public int totalValue() {
        return totalValue;
    }

    public String title() {
        return title;
    }
}
