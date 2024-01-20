package com.example.burger42.Game.UI.Scaffolding;

import android.content.ClipData;
import android.view.DragEvent;

/**
 * An Implementation of an OnDragAreaListener, that places the dragged item on top of this.
 */
public class DragAreaSetItemAbove extends OnDragAreaListener {
    /**
     * The index, where to place the drawn item.
     */
    private int index = 0;
    /**
     * the itemView, where the item will placed above.
     */
    private final ItemView itemView;

    /**
     * @param itemView the itemView, where the item will placed above.
     */
    public DragAreaSetItemAbove(ItemView itemView) {
        super();
        this.itemView = itemView;
    }

    /**
     * @param index The index, where to place the drawn item.
     * @return this object self, to used the methode in a settings chain.
     */
    public DragAreaSetItemAbove setIndex(int index) {
        this.index = index;
        return this;
    }

    /**
     * adds a item on top of the itemView, when there is no item yet.
     *
     * @param event  contains all information of the dragEvent
     * @param inArea true when the dragged item is inside the area else false.
     * @return true when the item is added.
     */
    @Override
    protected boolean onDrag(DragEvent event, boolean inArea) {
        if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
            ItemView itemView2 = (ItemView) event.getLocalState();
            if (itemView2 != itemView)
                if (!itemView.hasItemAbove(index)) {
                    itemView.setItemAbove(index, itemView2);
                    return true;
                }
        }
        return false;
    }


}
