package com.example.burger42.Item;

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
     * degreeOfSuccess returns the degree of completion as String. E.g. 4/5.
     *
     * @return the result could be 4/5 as well as 223/100 if the task is more than completed.
     */
    String degreeOfSuccess();

    /**
     * The title is the title of the task.
     * It must briefly describe the task.
     * Direct (Earn 300$) or Indirect (Now you are a capitalist)s
     *
     * @return the title of the task.
     */
    String title();
}
