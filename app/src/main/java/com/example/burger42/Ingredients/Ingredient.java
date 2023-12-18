package com.example.burger42.Ingredients;

/**
 * Class for generating different kinds of Burger Ingredients.
 */
public abstract class Ingredient {
    private String ingredientName;
    private int drawPriority;

    /**
     * Constructor for creating Ingredients.
     * @param ingredientName a name for the Ingredient.
     */
    public Ingredient(String ingredientName){
        this.ingredientName = ingredientName;
    }

    /**
     * Method to access the Ingredient Name.
     * @return the Ingredient Name.
     */
    public String name(){
        return ingredientName;
    }
}
