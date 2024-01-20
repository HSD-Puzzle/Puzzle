package com.example.burger42.Game.UI.Scaffolding;


import android.content.ClipData;

/**
 * every counter that allows to spawn an ItemView on it.
 * This interface has to be registered at the {@link RestaurantFragment}
 */
public interface BottomCounterItemSpawn {
    /**
     * spawns an ItemView on this counter.
     */
    void placeItemOnCounter(ItemView itemView);
}
