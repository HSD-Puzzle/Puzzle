package com.example.burger42.Game.UI.Scaffolding;


import android.content.ClipData;

/**
 * //TODO aktualisieren
 * every counter that allows to spawn {@link com.example.burger42.Game.UI.ItemViews.PlateView}.
 * This interface has to be registered at the {@link RestaurantFragment}
 */
public interface BottomCounterItemSpawn {
    /**
     * //TODO aktualisieren
     * spawns an {@link com.example.burger42.Game.UI.ItemViews.PlateView} on this Counter.
     */
    void placeItemOnCounter(ItemView itemView);
}
