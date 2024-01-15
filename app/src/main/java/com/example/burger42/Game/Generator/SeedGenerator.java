package com.example.burger42.Game.Generator;

/**
 * A class with various random functions, used primarily to generate Recipes.
 */
public class SeedGenerator {
    /**
     * A method which generates a random Integer between 1 and 1000.
     * @return a random Integer between 1 and 1000.
     */
    public static int integerGenerator(){
        return 1 + (int)(Math.random() * ((1000 - 1) + 1));
    }

    /**
     * A "coin flip" method, it has a 50/50 chance of either becoming true or false.
     * @return a random boolen - true or false.
     */
    public static boolean isEven(){
        return (integerGenerator() % 2) == 0;
    }

    /**
     * Another "coin flip" method but with numbers, instead of booleans.
     * @return 0 or 1, either true or false.
     */
    public static int zeroOrOne(){
        if(isEven())
            return 1;
        else
            return 0;
    }
}
