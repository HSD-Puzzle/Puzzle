package com.example.burger42.Game.UI.Scaffolding;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Ingredients.Ingredient;

import java.util.List;

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

    public void addToList(List<Ingredient> list) {
        list.add(toIngredientWrapper());
        if (hasItemAbove(0)) {
            ((IngredientView) getItemAbove(0)).addToList(list);
        }
    }

    public abstract Ingredient toIngredientWrapper();
}
