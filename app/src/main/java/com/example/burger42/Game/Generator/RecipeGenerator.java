package com.example.burger42.Game.Generator;

import com.example.burger42.Game.Recipe;
import com.example.burger42.Game.Time;
import com.example.burger42.Ingredients.BottomBurgerBun;
import com.example.burger42.Ingredients.BurgerPattyMedium;
import com.example.burger42.Ingredients.BurgerPattyRare;
import com.example.burger42.Ingredients.BurgerSalad;
import com.example.burger42.Ingredients.Chesse;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.Ingredients.TopBurgerBun;

import java.util.LinkedList;

/**
 * A class that generates Random Recipes, via a Seedgenerator.
 */
public class RecipeGenerator {
    private int difficultyLevel;

    public RecipeGenerator(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * A Method that is called from the outside to provide a new, random Recipe.
     *
     * @param currentTime the current ingame Time to create a Recipe which suggests the ideal
     *                    time.
     * @return a new, random Recipe.
     */
    //TODO Create more difficult recipes with a higher streak.
    // Create recipes that only contain certain ingredients.
    public Recipe createRecipe(Time currentTime, float streak) {
        Recipe recipe = new Recipe(currentTime);
        for (int i = 0; i < difficultyController() + SeedGenerator.zeroOrOne() + Math.round(streak + 0.3); i++) {
            LinkedList<Ingredient> list = new LinkedList<>();
            list.add(new BottomBurgerBun());
            for (int j = 0; j < difficultyLevel; j++) {
                if(difficultyLevel > 1){
                    if (SeedGenerator.isEven()) {
                        if (SeedGenerator.integerGenerator() < 501)
                            list.add(addPatty());
                        else
                            list.add(new Chesse());
                    } else
                        list.add(new BurgerSalad());
                }else{
                        list.add(addPatty());
                }

            }
            list.add(addPatty());
            list.add(new TopBurgerBun());
            recipe.list().add(list);
        }
        return recipe;
    }

    /**
     * A method which regulates the difficulty setting, by mapping the difficulty setting to a
     * different number.
     * @return a mapped number for generating recipes.
     */
    private int difficultyController(){
        if(difficultyLevel == 2)
            return 0;
        if(difficultyLevel == 3) {
            if (SeedGenerator.isEven())
                return 1;
            else
                return 2;
        }
        if(difficultyLevel > 3)
            return 3;
        return 0;
    }

    /**
     * A method that adds a type of Patty, the outcome is randomized.
     * @return A type of Purger Patty.
     */
    private Ingredient addPatty(){
        if(SeedGenerator.isEven())
            return new BurgerPattyRare();
        else
            return new BurgerPattyMedium();
    }
}
