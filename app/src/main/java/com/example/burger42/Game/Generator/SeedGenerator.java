package com.example.burger42.Game.Generator;

public class SeedGenerator {
    public static int integerGenerator(){
        return 1 + (int)(Math.random() * ((1000 - 1) + 1));
    }
    public static boolean isEven(){
        return (integerGenerator() % 2) == 0;
    }
    public static int zeroOrOne(){
        if(isEven())
            return 0;
        else
            return 1;
    }
}
