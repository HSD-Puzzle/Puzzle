package com.example.burger42.Game;

import androidx.annotation.NonNull;

import com.example.burger42.Ingredients.Ingredient;

import java.util.LinkedList;
import java.util.List;

public class Recipe {

    private final List<List<Ingredient>> ingredients = new LinkedList<>();

    public enum PlaceToEat {
        ONSITE, TOGO
    }

    public List<List<Ingredient>> list() {
        return ingredients;
    }

    private final PlaceToEat onSite = PlaceToEat.ONSITE;

    private Time orderTakenTime;

    private Time timeToDeliver;

    public Recipe(Time currentTime, Time timeToDeliver) {
        orderTakenTime = new Time(currentTime);
        this.timeToDeliver = timeToDeliver;
    }

    public Recipe() {
    }

    public PlaceToEat onSite() {
        return onSite;
    }

    public Time orderTakenTime() {
        return orderTakenTime;
    }

    public Time timeToDeliver() {
        return timeToDeliver;
    }

    public int restOfTheTime(Time currentime) {
        Time deliverytime = new Time(timeToDeliver);
        deliverytime.addTime(orderTakenTime);
        deliverytime.subTime(currentime);
        return deliverytime.minutes();
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
