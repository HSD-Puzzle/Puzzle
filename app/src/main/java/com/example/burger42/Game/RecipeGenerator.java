package com.example.burger42.Game;

import com.example.burger42.ArrayAdapter.IngredientAdapter;
import com.example.burger42.Ingredients.BottomBurgerBun;
import com.example.burger42.Ingredients.BurgerPatty;
import com.example.burger42.Ingredients.BurgerSalad;
import com.example.burger42.Ingredients.TopBurgerBun;
import com.example.burger42.MainActivity;

public class RecipeGenerator {
    private MainActivity mainActivity;
    private IngredientAdapter adapter;
    public RecipeGenerator(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public IngredientAdapter giveIngredientadapter(){
        if(adapter == null)
            adapter = new IngredientAdapter(mainActivity);
        return adapter;
    }
    public void createRecipe(int difficultyLevel){
        adapter.clear();
        int seedGenerator = 1 + (int)(Math.random() * ((1000 - 1) + 1));

        adapter.add(new BottomBurgerBun(3));

        for (int i = 0;i<difficultyLevel;i++){
            if((seedGenerator % 2)==0){
                adapter.add(new BurgerPatty(2));
            }
            else
                adapter.add(new BurgerSalad(2));
            seedGenerator = 1 + (int)(Math.random() * ((1000 - 1) + 1));
        }
        adapter.add(new BurgerPatty(2));
        adapter.add(new TopBurgerBun(1));
    }
}
