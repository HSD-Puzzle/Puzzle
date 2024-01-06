package com.example.burger42.Game.UI.Scaffolding;

import com.example.burger42.Game.Recipe;


/**
 * every counter that allows to spawn {@link com.example.burger42.Game.UI.ItemViews.OrderView} based on a given Recipe.
 * This interface has to be registered at the {@link RestaurantFragment}
 */
public interface OrderSpawn {
    /**
     * spawns an {@link com.example.burger42.Game.UI.ItemViews.OrderView} on this Counter.
     *
     * @param recipe the {@link Recipe} the {@link com.example.burger42.Game.UI.ItemViews.OrderView} should base on.
     */
    public void spawnAndPlaceRecipe(Recipe recipe);
}
