package com.example.burger42.Game;

/**
 * A class for the ingame time
 */
public class Time {

    /**
     * The time in milliseconds
     */
    private long timeInMilliSeconds;

    /**
     * constructor, that creates a new time at 00:00
     */
    public Time() {
        this(0);
    }

    /**
     * constructor, that creates a new time that has the same time than the other.
     *
     * @param timeToCopy the time to copy from
     */
    public Time(Time timeToCopy) {
        this(timeToCopy.timeInMilliSeconds);
    }

    /**
     * constructor, that creates a time with an fix start hour and minute value
     *
     * @param hours   the hour value
     * @param minutes the minute value
     */
    public Time(int hours, int minutes) {
        this(hours * 3600000L + minutes * 60000L);
    }

    /**
     * constructor, that creates a Time from a given millisecondAmount done that day.
     *
     * @param timeInMilliSeconds the amount of milliseconds done in this time.
     */
    public Time(long timeInMilliSeconds) {
        this.timeInMilliSeconds = timeInMilliSeconds;
    }

    /**
     * adds the amount of time done from an other time to this one.
     *
     * @param timeToAdd the time to add to this
     */
    public void addTime(Time timeToAdd) {
        addTimeInMilliSeconds(timeToAdd.timeInMilliSeconds);
    }

    /**
     * @param timeToAddInSeconds
     */
    public void addSeconds(int timeToAddInSeconds) {
        addTimeInMilliSeconds(timeToAddInSeconds * 1000L);
    }

    public void addTimeInMilliSeconds(long timeToAddInMilliSeconds) {
        timeInMilliSeconds += timeToAddInMilliSeconds;
    }

    public void subTime(Time timeToSub) {
        subMilliSeconds(timeToSub.timeInMilliSeconds);
    }

    public void subMilliSeconds(long timeToSubInMilliSeconds) {
        timeInMilliSeconds -= timeToSubInMilliSeconds;
    }

    public int minute() {
        return (int) ((timeInMilliSeconds / 60000) % 60);
    }

    public int minutes() {
        return (int) (timeInMilliSeconds / 60000);
    }

    public int hour() {
        return (int) ((timeInMilliSeconds / 3600000) % 24);
    }

    public String timeAsText() {
        if (minute() < 10)
            return hour() + ":0" + minute();
        else
            return hour() + ":" + minute();
    }
}
