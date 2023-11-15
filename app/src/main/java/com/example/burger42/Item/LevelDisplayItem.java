package com.example.burger42.Item;

public class LevelDisplayItem {
    protected int levelId;
    protected int highscore;
    protected int stars;

    public LevelDisplayItem(int levelId, int highscore, int stars){
        this.levelId = levelId;
        this.highscore = highscore;
        this.stars = stars;
    }
    public int id(){
        return levelId;
    }
    public int highscore(){
        return highscore;
    }
    public int stars(){
        return stars;
    }
}
