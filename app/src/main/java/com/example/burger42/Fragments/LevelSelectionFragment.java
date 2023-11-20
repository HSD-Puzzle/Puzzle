package com.example.burger42.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.burger42.ArrayAdapter.IngredientAdapter;
import com.example.burger42.ArrayAdapter.LevelDisplayItemAdapter;
import com.example.burger42.Fragments.ParentFragment;
import com.example.burger42.Game.RecipeGenerator;
import com.example.burger42.Ingredients.BottomBurgerBun;
import com.example.burger42.Ingredients.BurgerPatty;
import com.example.burger42.Ingredients.TopBurgerBun;
import com.example.burger42.Item.LevelDisplayItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.List;

public class LevelSelectionFragment extends ParentFragment {

    private View view;
    private ListView listView;
    private LevelDisplayItemAdapter levelDisplayItemAdapter;
    public LevelSelectionFragment(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_level_selection, container, false);
        //view = inflater.inflate(R.layout.ingredient_recipe, container, false);
        //createAndDisplayLevelItems();
        listView = view.findViewById(R.id.levelSelection_List);
        //listView = view.findViewById(R.id.ingredient_recipe_list);

        Button backButton = (Button) view.findViewById(R.id.levelSelection_BackButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               mainActivity.showFragment(new StartFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });
        levelDisplayItemAdapter = new LevelDisplayItemAdapter(mainActivity);

        listView.setAdapter(levelDisplayItemAdapter);
        levelDisplayItemAdapter.add(new LevelDisplayItem(1,200,3));


       //RecipeGenerator generator = new RecipeGenerator(mainActivity);
       //listView.setAdapter(generator.giveIngredientadapter());
       //generator.createRecipe(1);


        return view;
    }


    }