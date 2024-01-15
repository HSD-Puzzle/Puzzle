package com.example.burger42.Game.StarChalanges;

import com.example.burger42.Item.StarItem;

public abstract class BasicStarItem implements StarItem {
    private boolean done = false;

    @Override
    public final boolean done() {
        return done;
    }

    @Override
    public final void setIsDone(boolean value) {
        done = value;
    }
}
