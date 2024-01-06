package com.example.burger42.Game.UI.Scaffolding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Recipe;
import com.example.burger42.Game.UI.ItemViews.OrderView;
import com.example.burger42.Ingredients.Ingredient;

import java.util.LinkedList;
import java.util.List;

/**
 * PayableItemView is a specific ItemView, that allows items to be payed.
 * It has methods to create a recipe from the itemviews.
 */
public abstract class PayableItemView extends ItemView {
    public PayableItemView(Context context) {
        super(context);
    }

    public PayableItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * adds a OnDragAreaListener, that serves if an orderView will be dragged on the payableView
     */
    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnDragAreaListener(new OnDragAreaListener() {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    ((OrderView) event.getLocalState()).serve(self());
                    return true;
                }
                return false;
            }
        }.setUseFilter(true).addFilterTag("Order"));
    }

    /**
     * self returns the item self to use it inside an anonymous class.
     *
     * @return this
     */
    private PayableItemView self() {
        return this;
    }

    /**
     * adds Payable to the itemFilter list
     */
    @Override
    protected List<ItemFilterTag> itemFilterTags() {
        List<ItemFilterTag> list = super.itemFilterTags();
        list.add(ItemFilterTag.Payable);
        return list;
    }

    /**
     * createRecipe creates a {@link Recipe} based on the itemAbove at the given itemAboveIndex.
     * if there is no ItemAbove it will return an empty recipe.
     *
     * @param itemAboveIndex the index of the itemAbove that should be used as root for the created recipe
     * @return the created recipe
     */
    public Recipe createRecipe(int itemAboveIndex) {
        Recipe recipe = new Recipe();
        if (hasItemAbove(itemAboveIndex)) {
            ItemView itemView = getItemAbove(itemAboveIndex);
            if (itemView.hasAtLeastOneEqualFilterTag(new BasicDragFilter().addFilterTag(ItemFilterTag.Ingredient.name()))) {
                IngredientView item = (IngredientView) itemView;
                List<Ingredient> list = new LinkedList<>();
                item.addToList(list);
                recipe.list().add(list);
            } else if (itemView.hasAtLeastOneEqualFilterTag(new BasicDragFilter().addFilterTag(ItemFilterTag.Payable.name()))) {
                PayableItemView item = (PayableItemView) itemView;
                recipe.addRecipe(item.createRecipe());
            }
        }
        return recipe;
    }

    /**
     * createRecipe creates a {@link Recipe} based on this ItemView as root.
     * The methode combines the recipes from the items above based on the structure of this item.
     *
     * @return the created recipe
     */
    public abstract Recipe createRecipe();
}
