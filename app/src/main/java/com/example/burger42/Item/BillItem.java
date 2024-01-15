package com.example.burger42.Item;

import java.util.List;

/**
 * A BillItem contains all the information of a single order.
 * The information is represented in the {@link com.example.burger42.Fragments.BillFragment} by the {@link com.example.burger42.ArrayAdapter.BillItemAdapter}.
 */
public class BillItem {
    /**
     * The number of processing of this order.
     */
    private final int orderNumber;
    /**
     * The total value of this order. In other words, the sum of the values of all components. (All burgers, tip)
     */
    private final float totalValue;
    /**
     * All information of the order components. Used for a detailed view of the order.
     */
    private final List<BillDetailItem> billOrderItems;
    /**
     * detailView indicates whether the detail view is currently displayed.
     */
    private boolean detailView = false;

    /**
     * @param orderNumber    the number of processing of this order.
     * @param totalValue     The total value of this order. In other words, the sum of the values of all components. (All burgers, tip)
     * @param billOrderItems All information of the order components. Used for a detailed view of the order.
     */
    public BillItem(int orderNumber, float totalValue, List<BillDetailItem> billOrderItems) {
        this.orderNumber = orderNumber;
        this.totalValue = totalValue;
        this.billOrderItems = billOrderItems;
    }

    /**
     * @return The total value of this order. In other words, the sum of the values of all components. (All burgers, tip)
     */
    public float totalValue() {
        return totalValue;
    }

    /**
     * @return The number of processing of this order.
     */
    public int orderNumber() {
        return orderNumber;
    }

    /**
     * @return All information of the order components. Used for a detailed view of the order.
     */
    public List<BillDetailItem> billOrderItems() {
        return billOrderItems;
    }

    /**
     * @return detailView indicates whether the detail view is currently displayed.
     */
    public boolean isDetailView() {
        return detailView;
    }

    /**
     * @param detailView detailView indicates whether the detail view is currently displayed.
     */
    public void setDetailView(boolean detailView) {
        this.detailView = detailView;
    }
}
