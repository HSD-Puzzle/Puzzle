package com.example.burger42.Game.Timer;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.R;

public class ClockTimer extends CountDownTimer {
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
    public ClockTimer(long millisInFuture, long countDownIntervall, RecipeGenerator generator, View view){
        super(millisInFuture,countDownIntervall);
        this.view = view;
        clockview = view.findViewById(R.id.timer_item);
        this.generator = generator;
    }

    @Override
    public void onTick(long l) {
        clockview.setImageResource(images[i++]);
    }


    public void onFinish(){
        i = 0;
        generator.createRecipe();
        this.start();
    }
    public View view(){
        return view;
    }
}
