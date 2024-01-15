package com.example.burger42.Item;

import android.content.Context;

import com.example.burger42.Game.GamePuppeteer;

/**
 * The StarItem is the container for all the information required to display a star.
 */
public interface StarItem {
    /**
     * done indicates whether the task associated with the star has been completed.
     *
     * @return true if the task id done
     */
    boolean done();

    /**
     * sets the Star always done.
     *
     * @param value the value to set.
     */
    void setIsDone(boolean value);

    /**
     * degreeOfSuccess returns the degree of completion as String. E.g. 4/5.
     *
     * @param context the context to create the correct language String
     * @return the result could be 4/5 as well as 223/100 if the task is more than completed.
     */
    String degreeOfSuccess(Context context);

    /**
     * The title is the title of the task.
     * It must briefly describe the task.
     * Direct (Earn 300$) or Indirect (Now you are a capitalist)s
     *
     * @param context the context to create the correct language String
     * @return the title of the task.
     */
    String title(Context context);

    /**
     * Calculates the degree of completion and if it is done.
     *
     * @param statistics the statistics to read the game data.
     */
    void calculate(GamePuppeteer.GameResultStatistics statistics);
}
