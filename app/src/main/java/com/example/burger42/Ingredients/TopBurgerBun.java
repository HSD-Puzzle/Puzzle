package com.example.burger42.Ingredients;

import androidx.annotation.NonNull;

import com.example.burger42.R;

/**
 * Class to Create a Top Burger Bun.
 */
public class TopBurgerBun extends Ingredient {
    public TopBurgerBun() {
        super(R.string.topburgerbun);
    }

    public boolean equals(Object obj){
        return obj instanceof TopBurgerBun;
    }
    @NonNull
    @Override
    public String toString() {
        return "TopBurgerBun";
    }
}
