package com.example.burger42.Game;

import com.example.burger42.Ingredients.Ingredient;

import java.util.LinkedList;
import java.util.List;

public class Recipe {

    private List<List<Ingredient>> ingredients = new LinkedList<>();

    public enum PlaceToEat {
        ONSITE, TOGO
    }

    public List<List<Ingredient>> list(){
        return ingredients;
    }
    private PlaceToEat onSite = PlaceToEat.ONSITE;

    private Time orderTakenTime = new Time(12, 33);

    private Time timeToDeliver = new Time(0, 15);


    public PlaceToEat onSite() {
        return onSite;
    }

    public Time orderTakenTime() {
        return orderTakenTime;
    }

    public Time timeToDeliver() {
        return timeToDeliver;
    }
}
