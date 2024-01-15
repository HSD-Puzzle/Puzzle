package com.example.burger42.Ingredients;

import com.example.burger42.R;

public class BurgerPattyRawOrBurned extends Ingredient {
    public BurgerPattyRawOrBurned() {
        super(0);
    }

    public boolean equals(Object obj) {
        return obj instanceof BurgerPattyRawOrBurned;
    }
}
