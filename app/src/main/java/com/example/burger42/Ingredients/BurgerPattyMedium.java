package com.example.burger42.Ingredients;

import com.example.burger42.R;

public class BurgerPattyMedium extends Ingredient{
    public BurgerPattyMedium(){
        super(R.string.burgerpattymedium);
    }
    public boolean equals(Object obj){
        return obj instanceof BurgerPattyMedium;
    }
}
