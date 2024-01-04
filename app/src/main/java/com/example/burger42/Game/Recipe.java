package com.example.burger42.Game;

import androidx.annotation.NonNull;

import com.example.burger42.Ingredients.Ingredient;

import java.util.LinkedList;
import java.util.List;

public class Recipe {

    private List<List<Ingredient>> ingredients = new LinkedList<>();
    private Time time;
    public Recipe(){
        System.out.println("Test: " + GamePuppeteer.currentime.hour());
        time = new Time(GamePuppeteer.currentime.hour(),GamePuppeteer.currentime.minutes());
    }


    public enum PlaceToEat {
        ONSITE, TOGO
    }

    public List<List<Ingredient>> list() {
        return ingredients;
    }

    private PlaceToEat onSite = PlaceToEat.ONSITE;

    private Time orderTakenTime = new Time(8,0);

    private Time timeToDeliver = new Time(orderTakenTime.hour(), orderTakenTime.minute()+30);


    public PlaceToEat onSite() {
        return onSite;
    }

    public Time orderTakenTime() {
        return orderTakenTime;
    }

    public Time timeToDeliver() {
        return timeToDeliver;
    }

    public void addRecipe(Recipe recipe) {
        ingredients.addAll(recipe.ingredients);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Recipe {\n");
        for (List<Ingredient> x : ingredients) {
            result.append("Burger\n[");
            for (Ingredient y : x) {
                result.append(y.toString()).append(" ");
            }
            result.append("]\n");
        }
        result.append("\n}");
        return result.toString();
    }
}
