package com.example.burger42.Game.Generator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.burger42.ArrayAdapter.IngredientAdapter;
import com.example.burger42.Game.Timer.ClockTimer;
import com.example.burger42.Ingredients.BottomBurgerBun;
import com.example.burger42.Ingredients.BurgerPatty;
import com.example.burger42.Ingredients.BurgerSalad;
import com.example.burger42.Ingredients.Chesse;
import com.example.burger42.Ingredients.TopBurgerBun;
import com.example.burger42.R;

public class RecipeGenerator {
    private Context context;
    private IngredientAdapter adapter;
    private ClockTimer timer;
    private View view;
    private ListView listView;
    private int difficultyLevel;
    public RecipeGenerator(Context context, int difficultyLevel){
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.ingredient_recipe,null);
        listView = view.findViewById(R.id.ingredient_recipe_list);
        listView.setAdapter(giveIngredientadapter());
        timer = new ClockTimer(12000,1000,this, view);
        this.difficultyLevel = difficultyLevel;
    }
    public IngredientAdapter giveIngredientadapter(){
        if(adapter == null)
            adapter = new IngredientAdapter(context);
        return adapter;
    }
    public void createRecipe(){
        giveIngredientadapter().clear();
        adapter.add(new BottomBurgerBun());
        for (int i = 0;i<difficultyLevel+1;i++){
            if(SeedGenerator.isEven()){
                if(SeedGenerator.integerGenerator() < 501)
                    adapter.add(new BurgerPatty());
                else
                    adapter.add(new Chesse());
            }
            else
                adapter.add(new BurgerSalad());
        }
        adapter.add(new BurgerPatty());
        adapter.add(new TopBurgerBun());
    }
    public void start(){
        createRecipe();
        timer.start();
    }
    public View view(){
        return view;
    }
}
