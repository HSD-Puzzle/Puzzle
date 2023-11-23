package com.example.burger42.Game;

public class SeedGenerator {
    private static int integerGenerator(){
        return 1 + (int)(Math.random() * ((1000 - 1) + 1));
    }
    public static boolean isEven(){
        return (integerGenerator() % 2) == 0;
    }
}
