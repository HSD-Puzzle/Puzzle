package com.example.burger42.Game.Generator;

import android.content.Context;

import com.example.burger42.ArrayAdapter.IngredientAdapter;
import com.example.burger42.Ingredients.BottomBurgerBun;
import com.example.burger42.Ingredients.BurgerPatty;
import com.example.burger42.Ingredients.BurgerSalad;
import com.example.burger42.Ingredients.Chesse;
import com.example.burger42.Ingredients.TopBurgerBun;
import com.example.burger42.MainActivity;

public class RecipeGenerator {
    private Context context;
    private IngredientAdapter adapter;
    public RecipeGenerator(Context context){
        this.context = context;
    }
    public IngredientAdapter giveIngredientadapter(){
        if(adapter == null)
            adapter = new IngredientAdapter(context);
        return adapter;
    }
    public void createRecipe(int difficultyLevel){
        adapter.clear();
        adapter.add(new BottomBurgerBun(3));
        //SeedGenerator generator = new SeedGenerator();

        for (int i = 0;i<difficultyLevel+1;i++){
            if(SeedGenerator.isEven()){
                if(SeedGenerator.integerGenerator() < 501)
                    adapter.add(new BurgerPatty(2));
                else
                    adapter.add(new Chesse(2));
            }
            else
                adapter.add(new BurgerSalad(2));
        }
        adapter.add(new BurgerPatty(2));
        adapter.add(new TopBurgerBun(1));
    }
}
