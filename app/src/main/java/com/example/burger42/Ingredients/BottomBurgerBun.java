package com.example.burger42.Ingredients;

import androidx.annotation.NonNull;

import com.example.burger42.R;

/**
 * Class to Create a Lower Burger Bun.
 */
public class BottomBurgerBun extends Ingredient {
    public BottomBurgerBun() {
        super(R.string.bottomburgerbun);
    }

    @NonNull
    @Override
    public String toString() {
        return "BottomBurgerBun";
    }
}
