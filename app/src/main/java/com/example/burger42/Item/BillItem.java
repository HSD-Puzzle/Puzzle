package com.example.burger42.Item;

import com.example.burger42.Ingredients.Ingredient;

import java.util.List;

public class BillItem {
    private int orderNumber;
    private float totalValue;
    private List<BillOrderItem> billOrderItems;
    private boolean detailView = false;

    public BillItem(int orderNumber, float totalValue, List<BillOrderItem> billOrderItems) {
        this.orderNumber = orderNumber;
        this.totalValue = totalValue;
        this.billOrderItems = billOrderItems;
    }

    public float totalValue() {
        return totalValue;
    }

    public int orderNumber() {
        return orderNumber;
    }

    public List<BillOrderItem> billOrderItems() {
        return billOrderItems;
    }

    public boolean isDetailView() {
        return detailView;
    }

    public void setDetailView(boolean detailView) {
        this.detailView = detailView;
    }
}
