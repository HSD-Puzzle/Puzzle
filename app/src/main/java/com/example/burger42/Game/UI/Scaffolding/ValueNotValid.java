package com.example.burger42.Game.UI.Scaffolding;

/**
 * used in {@link OnDragAreaListener} and {@link OnTouchAreaListener} if the values are not possible.
 */
public class ValueNotValid extends RuntimeException {
    public ValueNotValid(String message) {
        super(message);
    }
}
