package com.example.burger42.Game.UI.Scaffolding;

import android.content.ClipData;
import android.view.DragEvent;

public class DragAreaSetItemAbove extends OnDragAreaListener {
    private int index = 0;
    private ItemView itemView;

    public DragAreaSetItemAbove(ItemView itemView) {
        super();
        this.itemView = itemView;
    }

    public DragAreaSetItemAbove setIndex(int index) {
        this.index = index;
        return this;
    }

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
