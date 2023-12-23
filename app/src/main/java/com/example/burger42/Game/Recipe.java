package com.example.burger42.Game;

public class Recipe {

    public enum PlaceToEat {
        ONSITE, TOGO
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
