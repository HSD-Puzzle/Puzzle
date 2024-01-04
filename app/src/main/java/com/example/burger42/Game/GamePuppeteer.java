package com.example.burger42.Game;

import android.os.CountDownTimer;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;

public class GamePuppeteer {
    private RestaurantFragment restaurantFragment;
    private CountDownTimer startCountdown;
    private RecipeGenerator generator;

    public GamePuppeteer(RestaurantFragment restaurantFragment) {
        this.restaurantFragment = restaurantFragment;
        generator = new RecipeGenerator(2);
        startCountdown = new CountDownTimer(3300, 1000) {
            @Override
            public void onTick(long l) {
                if (l < 300)
                    restaurantFragment.setCountdown("Start");
                else if (l < 1300)
                    restaurantFragment.setCountdown("1");
                else if (l < 2300)
                    restaurantFragment.setCountdown("2");
                else if (l < 3300)
                    restaurantFragment.setCountdown("3");
            }

            @Override
            public void onFinish() {
                restaurantFragment.start();
                restaurantFragment.addRecipe(generator.createRecipe());

                //TEST Recepie add
                new CountDownTimer(20000, 5000) {
                    @Override
                    public void onTick(long l) {
                        restaurantFragment.addRecipe(generator.createRecipe());
                    }

                    @Override
                    public void onFinish() {
                    }
                }.start();

            }
        }.start();
    }

    public void serve(Recipe order, Recipe item) {
        System.out.println(item);
    }

    /**
     * onPause will be called, when the RestaurantFragment is paused.
     */
    public void onPause() {

    }

    /**
     * onResume will be called,
     */
    public void onResume() {

    }
}
