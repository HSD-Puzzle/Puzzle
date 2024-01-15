package com.example.burger42.Ingredients;

import com.example.burger42.R;

/**
 * Class to Create a Burger Patty.
 */
public class BurgerPattyRare extends Ingredient{
    public BurgerPattyRare(){
        super(R.string.burgerpattyrare);
    }
    public boolean equals(Object obj){
        return obj instanceof BurgerPattyRare;
    }
}
