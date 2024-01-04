package com.example.burger42.Ingredients;

import com.example.burger42.R;

/**
 * Class to Create Cheese.
 */
public class Chesse extends Ingredient{
    public Chesse(){
        super(R.string.cheese);
    }
    public boolean equals(Object obj){
        return obj instanceof Chesse;
    }
}
