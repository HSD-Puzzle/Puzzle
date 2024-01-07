package com.example.burger42.Game.Timer;

/**
 * GameTimers are implemented by all classes that are to be called cyclically depending on the elapsed game time.
 * These must be registered with the GameThread.
 */
public interface GameTimer {
    /**
     * this method is called every cycle this methode will not be triggered at the start or on finish.
     *
     * @param timeSinceRegistration the time that has elapsed since registration.
     */
    public void tick(long timeSinceRegistration);

    /**
     * this method is called after the time is done.
     */
    public default void finish() {
    }
}
