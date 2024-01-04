package com.example.burger42.Ingredients;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Class for generating different kinds of Burger Ingredients.
 */
public abstract class Ingredient {
    private int ingredientName;
    private int drawPriority;

    /**
     * Constructor for creating Ingredients.
     *
     * @param ingredientName a name for the Ingredient.
     */
    public Ingredient(int ingredientName) {
        this.ingredientName = ingredientName;
    }

    /**
     * Method to access the Ingredient Name.
     *
     * @return the Ingredient Name.
     */
    public String name(Context context) {
        return context.getString(ingredientName);
    }

    @NonNull
    @Override
    public String toString() {
        return "Ingredient";
    }
}
