package com.example.burger42.Item;

/**
 * Class to generate and display Level Display Items.
 */
public class LevelDisplayItem {
    protected int levelId;
    protected String difficulty;
    protected int highscore;
    protected int stars;

    /**
     * Constructor to Create a Level Display Items.
     * @param levelId a numeric ID for an Level.
     * @param difficulty the Difficulty of an Level, regulates the Recipe Generation.
     * @param highscore the achieved Highscore.
     * @param stars the number of reached Objectives.
     */
    public LevelDisplayItem(int levelId, String difficulty, int highscore, int stars){
        this.levelId = levelId;
        this.difficulty = difficulty;
        this.highscore = highscore;
        this.stars = stars;
    }

    /**
     * Method to access the ID.
     * @return a numeric ID Number.
     */
    public int id(){
        return levelId;
    }

    /**
     * Method to access the Highscore.
     * @return a numeric Highscore Number.
     */
    public int highscore(){
        return highscore;
    }

    /**
     * Method to access the Difficulty.
     * @return a Difficulty String.
     */
    public String difficulty(){
        return difficulty;
    }
    /**
     * Method to access the Star Count.
     * @return a numeric Star Count Number.
     */
    public int stars(){
        return stars;
    }
}
