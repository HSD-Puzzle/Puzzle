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
     * addSeconds adds a amount of seconds to the time.
     *
     * @param timeToAddInSeconds the amount of seconds to add.
     */
    public void addSeconds(int timeToAddInSeconds) {
        addTimeInMilliSeconds(timeToAddInSeconds * 1000L);
    }

    /**
     * addTimeInMilliSeconds add a amount of milliseconds to the time
     *
     * @param timeToAddInMilliSeconds the amount of milliseconds
     */
    public void addTimeInMilliSeconds(long timeToAddInMilliSeconds) {
        timeInMilliSeconds += timeToAddInMilliSeconds;
    }

    /**
     * subTime subtracts the from this the amount of time done from the other.
     *
     * @param timeToSub the Time that will be subtract from this.
     */
    public void subTime(Time timeToSub) {
        subMilliSeconds(timeToSub.timeInMilliSeconds);
    }

    /**
     * subMilliSeconds subtracts a amount of milliseconds.
     *
     * @param timeToSubInMilliSeconds is the amount of milliseconds to subtract.
     */
    public void subMilliSeconds(long timeToSubInMilliSeconds) {
        timeInMilliSeconds -= timeToSubInMilliSeconds;
    }

    /**
     * minute returns the number of minutes within the current hour.
     *
     * @return the current minute. The result is between 0-59
     */
    public int minute() {
        return (int) ((timeInMilliSeconds / 60000) % 60);
    }

    /**
     * minutes returns the current time in minutes.
     *
     * @return the current time in minutes. 2:22 am = 142 minutes
     */
    public int minutes() {
        return (int) (timeInMilliSeconds / 60000);
    }

    /**
     * hour returns the number of hours within the current day.
     *
     * @return the current hour. The result is between 0-23
     */
    public int hour() {
        return (int) ((timeInMilliSeconds / 3600000) % 24);
    }

    /**
     * timeAsText returns the time as String with the following notation hh:mm
     *
     * @return the time as String hh:mm
     */
    public String timeAsText() {
        return ((hour() < 10) ? ("0" + hour()) : "" + hour()) +
                ":" +
                ((minute() < 10) ? ("0" + minute()) : "" + minute());
    }
}
