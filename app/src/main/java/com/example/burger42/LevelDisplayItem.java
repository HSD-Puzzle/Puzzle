package com.example.burger42;

public class LevelDisplayItem {
    protected int levelId;
    protected int highscore;
    protected int stars;

    public LevelDisplayItem(int levelId){
        this.levelId = levelId;
        this.highscore = 0;
        this.stars = 0;
    }
}
