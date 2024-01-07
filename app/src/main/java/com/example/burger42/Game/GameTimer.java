package com.example.burger42.Game;

public interface GameTimer {
    public void tick(long timeSinceRegistration);

    public default void finish() {
    }
}
