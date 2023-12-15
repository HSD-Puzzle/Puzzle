package com.example.burger42.Game.UI.ItemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.CounterViews.MillCounterView;
import com.example.burger42.Game.UI.Scaffolding.DragAreaSetItemAbove;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

public class PlateView extends ItemView {

    public enum state {
        CLEAN, DIRTY
    }

    private state currentState;

    public state getCurrentState() {
        return currentState;
    }

    @Override
    protected ItemFilterTag[] itemFilterTags() {
        return new ItemFilterTag[]{ItemFilterTag.CleanBase};
    }

    public PlateView(Context context) {
        super(context);
    }

    public PlateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void beforeInit(Context context, @Nullable AttributeSet attrs) {
        currentState = state.CLEAN;
    }

    public void setCurrentState(state currentState) {
        this.currentState = currentState;
        loadTexture();
        invalidate();
    }

    @Override
    protected int drawableId() {
        switch (currentState) {
            case CLEAN:
                return R.drawable.cleanplate;
            case DIRTY:
                return R.drawable.dirtyplate;
            default:
                return R.drawable.cleanplate;

        }
    }

    @Override
    public String name() {
        return "PlateView";
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnDragAreaListener(new DragAreaSetItemAbove(this).setIndex(0).setRelativeRight(0.5f));
        addOnDragAreaListener(new DragAreaSetItemAbove(this).setIndex(1).setRelativeLeft(0.5f));
        addOnTouchAreaListener(new OnTouchAreaListener(1, 0, 0, 1) {

            @Override
            protected boolean onTouch(MotionEvent event) {
                drag();
                return true;
            }
        });
    }


    private static class DragAreaSetItemAbove extends OnDragAreaListener {

        public DragAreaSetItemAbove setIndex(int index) {
            this.index = index;
            return this;
        }

        public DragAreaSetItemAbove(ItemView itemView) {
            super();
            this.itemView = itemView;
        }

        private int index = 0;
        private ItemView itemView;

        @Override
        protected boolean onDrag(DragEvent event, boolean inArea) {
            if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                ItemView itemView2 = (ItemView) event.getLocalState();
                if (itemView2 != itemView) {
                    if (itemView2 instanceof PlateView) {
                        if (itemView.hasNoItemAbove()) {
                            itemView.setItemAbove(2, itemView2);
                            return true;
                        }
                    } else if (!itemView.hasItemAbove(index) && !itemView.hasItemAbove(2)) {
                        itemView.setItemAbove(index, itemView2);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    protected ItemAboveNode[] itemAboveSetUp() {
        return new ItemAboveNode[]{
                new ItemAboveNode(-0.2f, 0.13f) {
                },
                new ItemAboveNode(0.2f, 0.04f) {
                },
                new ItemAboveNode(0, 0.05f) {
                }
        };
    }

    @Override
    protected float scaling() {
        return 176f / 500f;
    }
}
