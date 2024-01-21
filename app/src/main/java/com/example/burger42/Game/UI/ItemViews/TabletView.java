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
 * The graphical representation of an tablet
 */
public class TabletView extends PayableItemView {
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

    public TabletView(Context context) {
        super(context);
    }

    public TabletView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int drawableId() {
        return R.drawable.tablet;
    }

    @Override
    public String name() {
        return "Tablet";
    }

    /**
     * adds listeners to place plates on the left and right and tablets centered above
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
     * The Listener with the logic to place the plates to the left and right and tablets centered above
     */
    private static class DragAreaSetItemAbove extends OnDragAreaListener {
        {
            addFilterTag(ItemFilterTag.CleanBase);
            setUseFilter(true);
        }

        public DragAreaSetItemAbove setIndex(int index) {
            this.index = index;
            return this;
        }

        public DragAreaSetItemAbove(TabletView itemView) {
            super();
            this.itemView = itemView;
        }

        private int index = 0;
        private final TabletView itemView;

        @Override
        protected boolean onDrag(DragEvent event, boolean inArea) {
            if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                ItemView itemView2 = (ItemView) event.getLocalState();
                if (itemView2 != itemView) {
                    if (itemView2 instanceof TabletView) {
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
                new ItemAboveNode(-0.48f, 0.2f) {
                },
                new ItemAboveNode(0.48f, 0.11f) {
                },
                new ItemAboveNode(0, 0.11f) {
                }
        };
    }

    @Override
    protected float scaling() {
        return 299f / 500f;
    }
}
