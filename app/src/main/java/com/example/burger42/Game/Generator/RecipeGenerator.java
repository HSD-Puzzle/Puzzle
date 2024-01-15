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
    public Recipe createRecipe(Time currentTime, float streak) {
        Recipe recipe = new Recipe(currentTime);
        for (int i = 0; i < recipeLengthController(); i++) {
            LinkedList<Ingredient> list = new LinkedList<>();
            list.add(new BottomBurgerBun());
            for (int j = 0; j < recipeIngredientController(streak); j++) {
                if(difficultyLevel > 1){
                    if (SeedGenerator.isEven()) {
                        if (SeedGenerator.isEven())
                            list.add(addPatty());
                        else
                            list.add(new Chesse());
                    } else
                        list.add(new BurgerSalad());
                }else{
                    if(SeedGenerator.isEven())
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
    private int recipeLengthController(){
        if(difficultyLevel == 2)
            return 1 + SeedGenerator.zeroOrOne();
        if(difficultyLevel >= 3) {
            return (2 + SeedGenerator.zeroOrOne() + SeedGenerator.zeroOrOne());
        }
        return 1 + SeedGenerator.zeroOrOne();
    }

    /**
     * A method which regulates how many ingredients a Burger can have, based on the difficulty.
     * @param streak the streak, that controlls the length of the ingredient amount.
     * @return a number that shows the appropiate amount of Ingredients for that difficulty.
     */
    private int recipeIngredientController(float streak){
        if(difficultyLevel == 2)
            return (int) (SeedGenerator.zeroOrOne() + Math.round(streak+0.2));
        if(difficultyLevel > 2)
            return (int) (1 + SeedGenerator.zeroOrOne() + SeedGenerator.zeroOrOne() + Math.round(streak+0.2));
        return (SeedGenerator.zeroOrOne() + SeedGenerator.zeroOrOne());
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

    /**
     * Method to ask for the current difficulty Level.
     * @return a number that contains the difficulty Level.
     */
    public int difficultyLevel(){
        return difficultyLevel;
    }
}
