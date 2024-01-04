package com.example.burger42.Ingredients;

import com.example.burger42.R;

/**
 * Class to Create a Burger Patty.
 */
public class BurgerPatty extends Ingredient{
    public BurgerPatty(){
        super(R.string.burgerpatty);
    }
    public boolean equals(Object obj){
        return obj instanceof BurgerPatty;
    }
}
