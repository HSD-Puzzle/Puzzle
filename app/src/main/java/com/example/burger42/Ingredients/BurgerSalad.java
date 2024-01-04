package com.example.burger42.Ingredients;

import androidx.annotation.Nullable;

import com.example.burger42.R;

/**
 * Class to Create Salad.
 */
public class BurgerSalad extends Ingredient{
    public BurgerSalad(){
        super(R.string.salad);
    }
    public boolean equals(Object obj){
        return obj instanceof BurgerSalad;
    }

}
