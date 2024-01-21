package com.example.burger42.Game.UI.ItemViews;

import static com.example.burger42.Game.UI.Scaffolding.ItemView.ItemFilterTag.Ingredient;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Recipe;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.Game.UI.Scaffolding.PayableItemView;
import com.example.burger42.R;

import java.util.List;

/**
 * The graphical representation of an plate
 */
public class PlateView extends PayableItemView {

    /**
     * the states a plate can have
     */
    public enum state {
        CLEAN, DIRTY
    }

    /**
     * the state this plate currently have
     */
    private state currentState;

    /**
     * @return the state this plate currently have
     */
    public state currentState() {
        return currentState;
    }

    @Override
    protected List<ItemFilterTag> itemFilterTags() {
        List<ItemFilterTag> list = super.itemFilterTags();
        list.add(ItemFilterTag.CleanBase);
        return list;
    }

    /**
     * adds the left and right burger to the recipe that will be served
     *
     * @return the recipe, that this plate has on it
     */
    @Override
    public Recipe createRecipe() {
        Recipe recipe = new Recipe();
        recipe.addRecipe(super.createRecipe(0));
        recipe.addRecipe(super.createRecipe(1));
        return recipe;
    }

    public PlateView(Context context, state state) {
        super(context);
        setCurrentState(state);
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

    /**
     * sets the state of the plate and redraws it
     *
     * @param currentState the state the plate should have
     */
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
        return "Plate";
    }

    /**
     * adds listeners to place ingredients on the left and right and plate centered above
     * and a listener to drag this plate.
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnDragAreaListener(new DragAreaSetItemAbove(this)
                .setIndex(0).setRelativeRight(0.5f));
        addOnDragAreaListener(new DragAreaSetItemAbove(this)
                .setIndex(1)
                .setRelativeLeft(0.5f));
        addOnTouchAreaListener(new OnTouchAreaListener(1, 0, 0, 1) {

            @Override
            protected boolean onTouch(MotionEvent event) {
                drag();
                return true;
            }
        });
    }


    /**
     * The Listener with the logic to place the ingredient to the left and right and plates centerd above
     */
    private static class DragAreaSetItemAbove extends OnDragAreaListener {
        {
            addFilterTag(Ingredient);
            addFilterTag("Plate");
            setUseFilter(true);
        }

        public DragAreaSetItemAbove setIndex(int index) {
            this.index = index;
            return this;
        }

        public DragAreaSetItemAbove(PlateView itemView) {
            super();
            this.itemView = itemView;
        }

        private int index = 0;
        private final PlateView itemView;

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
                    } else if (!itemView.hasItemAbove(index) && !itemView.hasItemAbove(2) && itemView.currentState() == state.CLEAN) {
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

    /**
     * checks if only plates are above this plate
     *
     * @return if there are only plates above it returns true
     */
    public boolean onlyPlatesAbove() {
        if (hasNoItemAbove())
            return true;
        if (hasItemAbove(2))
            return ((PlateView) itemAbove(2)).onlyPlatesAbove();
        return false;
    }
}
