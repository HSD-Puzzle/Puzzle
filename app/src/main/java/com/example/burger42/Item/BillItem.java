package com.example.burger42.Item;

import com.example.burger42.Ingredients.Ingredient;

import java.util.List;

public class BillItem {
    private int orderNumber;
    private String title; // TODO Order String
    private int totalValue;

    private List<BillOrderItem> ingredientList;



    public BillItem(int amount, String title, int totalValue) {
        this.orderNumber = amount;
        this.totalValue = totalValue;
        this.title = title;
    }

    public int amount() {
        return orderNumber;
    }

    public int totalValue() {
        return totalValue;
    }

    public String title() {
        return title;
    }
}
