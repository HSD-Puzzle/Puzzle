package com.example.burger42.Game.Generator;

import com.example.burger42.Game.GamePuppeteer;
import com.example.burger42.Game.Recipe;
import com.example.burger42.Game.Time;
import com.example.burger42.Ingredients.BottomBurgerBun;
import com.example.burger42.Ingredients.BurgerPatty;
import com.example.burger42.Ingredients.BurgerSalad;
import com.example.burger42.Ingredients.Chesse;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.Ingredients.TopBurgerBun;

import java.util.LinkedList;

/**
 * A class that generates Random Recipes, via a Seedgenerator.
 */
public class RecipeGenerator {
    private Recipe recipe;
    LinkedList<Ingredient> list;
    private int difficultyLevel;
    private Time currentTime;

    public RecipeGenerator(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * A Method that is called from the outside to provide a new, random Recipe.
     * @param currentTime the current ingame Time to create a Recipe which suggests the ideal
     *                    time.
     * @return a new, random Recipe.
     */
    public Recipe createRecipe(Time currentTime) {
        recipe = new Recipe(currentTime);
        for (int i = 0; i < difficultyLevel + SeedGenerator.zeroOrOne(); i++) {
            list = new LinkedList<>();
            list.add(new BottomBurgerBun());
            for (int j = 0; j < difficultyLevel + 1; j++) {
                if (SeedGenerator.isEven()) {
                    if (SeedGenerator.integerGenerator() < 501)
                        list.add(new BurgerPatty());
                    else
                        list.add(new Chesse());
                } else
                    list.add(new BurgerSalad());
            }
            list.add(new BurgerPatty());
            list.add(new TopBurgerBun());
            recipe.list().add(list);
        }
        return recipe;
    }
}
