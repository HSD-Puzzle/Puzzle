package com.example.burger42.Game.Timer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.core.content.res.ResourcesCompat;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class ClockTimer extends CountDownTimer {
    private Context context;
    private View view;
    private ImageView clockview;
    protected RecipeGenerator generator;
    protected int i = 0;
    private int[] images = {
            R.drawable.clock_0_sec,
            R.drawable.clock_1_sec,
            R.drawable.clock_2_sec,
            R.drawable.clock_3_sec,
            R.drawable.clock_4_sec,
            R.drawable.clock_5_sec,
            R.drawable.clock_6_sec,
            R.drawable.clock_7_sec,
            R.drawable.clock_8_sec,
            R.drawable.clock_9_sec,
            R.drawable.clock_10_sec,
            R.drawable.clock_11_sec,
    };
    public ClockTimer(long millisInFuture, long countDownIntervall, Context context){
        super(millisInFuture,countDownIntervall);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.ingredient_recipe,null);
        clockview = view.findViewById(R.id.timer_item);
        this.generator = new RecipeGenerator(context);
        ListView listView = view.findViewById(R.id.ingredient_recipe_list);
        listView.setAdapter(generator.giveIngredientadapter());
        generator.createRecipe(1);
    }

    @Override
    public void onTick(long l) {
        clockview.setImageResource(images[i++]);
    }


    public void onFinish(){
        i = 0;
        generator.createRecipe(1);
        this.start();
    }
    public View view(){
        return view;
    }
}
