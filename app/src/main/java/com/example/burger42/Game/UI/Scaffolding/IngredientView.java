package com.example.burger42.Game.UI.Scaffolding;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Ingredients.Ingredient;

import java.util.List;

/**
 * a specific item view for ingredients, that adds the ability to convert them into a recipe
 * and converts the ui into the domain
 */
public abstract class IngredientView extends ItemView {
    public IngredientView(Context context) {
        super(context);
    }

    public IngredientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected List<ItemFilterTag> itemFilterTags() {
        List<ItemFilterTag> list = super.itemFilterTags();
        list.add(ItemFilterTag.Ingredient);
        return list;
    }

    /**
     * adds this ingredient to the list.
     *
     * @param list the list to add this ingredient to
     */
    public void addToList(List<Ingredient> list) {
        list.add(toIngredientWrapper());
        if (hasItemAbove(0)) {
            ((IngredientView) itemAbove(0)).addToList(list);
        }
    }

    /**
     * an adapter methode that creates from the ui the domain
     *
     * @return the ingredient this is.
     */
    public abstract Ingredient toIngredientWrapper();
}
