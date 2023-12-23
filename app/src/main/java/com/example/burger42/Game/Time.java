package com.example.burger42.Game;

public class Time {

    private int timeInSeconds;

    public Time() {
        this(0);
    }

    public Time(Time timeToCopie) {
        this(timeToCopie.timeInSeconds);
    }

    public Time(int hours, int minutes) {
        this(hours * 3600 + minutes * 60);
    }

    public Time(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public void addTime(Time timeToAdd) {
        addSeconds(timeToAdd.timeInSeconds);
    }

    public void addSeconds(int timeToAddInSeconds) {
        timeInSeconds += timeToAddInSeconds;
    }

    public void subTime(Time timeToSub) {
        subSeconds(timeToSub.timeInSeconds);
    }

    public void subSeconds(int timeToSubInSeconds) {
        timeInSeconds -= timeToSubInSeconds;
    }

    public int minute() {
        return (timeInSeconds / 60) % 60;
    }

    public int minutes() {
        return (timeInSeconds / 60);
    }

    public int hour() {
        return (timeInSeconds / 3600) % 24;
    }

    public String timeAsText() {
        return hour() + ":" + minute();
    }
}
