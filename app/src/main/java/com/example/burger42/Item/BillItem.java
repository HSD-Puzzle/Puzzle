package com.example.burger42.Item;

import com.example.burger42.Ingredients.Ingredient;

import java.util.List;

public class BillItem {
    private int orderNumber;
    private float totalValue;

    private List<BillOrderItem> billOrderItems;



    public BillItem(int orderNumber, float totalValue, List<BillOrderItem> billOrderItems) {
        this.orderNumber = orderNumber;
        this.totalValue = totalValue;
        this.billOrderItems = billOrderItems;
    }

    public int amount() {
        return orderNumber;
    }

    public float totalValue() {
        return totalValue;
    }

}
