package com.example.burger42.Item;

/**
 * BillDetailItem contains all the information that is displayed in the detailed view of the bill.
 * A bill detail is a part of the total order, e.g. one burger out of many or the tip.
 */
public class BillDetailItem {
    /**
     * the name of the order part.
     */
    private final String name;
    /**
     * beforeStreak is the value of the partial order before the Streak is multiplied on it.
     */
    private final int beforeStreak;
    /**
     * streak is the streak by which the value of the partial order is multiplied.
     */
    private final float streak;
    /**
     * isDone indicates whether the order was successful. If not, the streak for the next burger is reset.
     */
    private final boolean isDone;

    /**
     * @param name         the name of the order part.
     * @param beforeStreak beforeStreak is the value of the partial order before the Streak is multiplied on it.
     * @param streak       streak is the streak by which the value of the partial order is multiplied.
     * @param isDone       isDone indicates whether the order was successful. If not, the streak for the next burger is reset.
     */
    public BillDetailItem(String name, int beforeStreak, float streak, boolean isDone) {
        this.name = name;
        this.beforeStreak = beforeStreak;
        this.streak = streak;
        this.isDone = isDone;
    }

    /**
     * @return streak is the streak by which the value of the partial order is multiplied.
     */
    public float streak() {
        return streak;
    }

    /**
     * @return the name of the order part.
     */
    public String name() {
        return name;
    }

    /**
     * @return beforeStreak is the value of the partial order before the Streak is multiplied on it.
     */
    public int beforeStreak() {
        return beforeStreak;
    }

    /**
     * @return isDone indicates whether the order was successful. If not, the streak for the next burger is reset.
     */
    public boolean isDone() {
        return isDone;
    }
}
