package com.example.burger42.Ingredients;

public abstract class Ingredient {
    private String ingredientName;
    private int drawPriority;

    public Ingredient(String ingredientName, int drawPriority){
        this.ingredientName = ingredientName;
        this.drawPriority = drawPriority;
    }
    public String name(){
        return ingredientName;
    }
}
