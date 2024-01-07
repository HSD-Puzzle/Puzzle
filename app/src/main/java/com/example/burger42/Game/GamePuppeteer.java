package com.example.burger42.Game;

import android.os.CountDownTimer;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.Timer.Timer;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.Item.BillItem;
import com.example.burger42.Item.BillOrderItem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GamePuppeteer {

    private GameThread gameThread;

    private RestaurantFragment restaurantFragment;
    private CountDownTimer startCountdown;
    private CountDownTimer recipeCountdown;
    private CountDownTimer gameCountdown;
    private List<CountDownTimer> burgerCountdown;
    private CountDownTimer plateCountdown;
    private List<CountDownTimer> allCountdowns;
    //private long[] timers = {3300,20000,240000,10000};
    //private long[] intervalls = {1000,5000,500,1000};
    private RecipeGenerator generator;
    private int index = 0;
    private int money = 0;
    private int newMoney;
    private int tip;
    private float streak = 1.0f;
    public static Time currentime;
    private static Time pausetime;
    private static boolean isPaused = false;
    private List<BillItem> billItemList;


    public GamePuppeteer(RestaurantFragment restaurantFragment) {
        billItemList = new LinkedList<>();
        this.restaurantFragment = restaurantFragment;
        currentime = new Time(8, 0);
        generator = new RecipeGenerator(2);

        gameThread = new GameThread(restaurantFragment.getActivity());
        gameThread.start();

        gameThread.addGameTimer(new GameTimer() {
            {
                restaurantFragment.setCountdown("3");
            }

            @Override
            public void tick(long timeSinceRegistration) {
                if (timeSinceRegistration < 1000)
                    restaurantFragment.setCountdown("3");
                else if (timeSinceRegistration < 2000)
                    restaurantFragment.setCountdown("2");
                else if (timeSinceRegistration < 3000)
                    restaurantFragment.setCountdown("1");
                else
                    restaurantFragment.setCountdown("Start");
            }

            @Override
            public void finish() {
                restaurantFragment.start();
                restaurantFragment.addRecipe(generator.createRecipe());

                gameThread.addGameTimer(new GameTimer() {

                    @Override
                    public void tick(long timeSinceRegistration) {
                        currentime.setTimeInMilliSeconds(timeSinceRegistration * 120 + 28800000);
                        restaurantFragment.setTimeText(currentime);
                    }

                    @Override
                    public void finish() {
                        restaurantFragment.timesUp(billItemList);
                    }
                }, 240000, 500);


            }
        }, 3300, 1000);
    }


    public void serve(Recipe order, Recipe item) {
        List<BillOrderItem> list = new LinkedList<>();
        newMoney = 0;
        Iterator<List<Ingredient>> orderIterator = order.list().iterator();
        while (orderIterator.hasNext()) {
            List<Ingredient> orderList = orderIterator.next();
            if (item.list().contains(orderList)) {
                item.list().remove(orderList);
                money += orderList.size() * 10 * streak;
                newMoney += orderList.size() * 10 * streak;
                list.add(new BillOrderItem("Burger", newMoney, streak, true));
                streak += 0.1;
                tip = order.calculateTip();
            } else {
                list.add(new BillOrderItem("Burger", 0, streak, false));
                streak = 1.0f;
                tip = 0;
            }
        }
        billItemList.add(new BillItem(index++, (newMoney + tip) * streak, list));
        restaurantFragment.setMoneyText(money, newMoney, tip);
    }

    /**
     * onPause will be called, when the RestaurantFragment is paused.
     */
    public void onPause() {
        gameThread.pause();
        pausetime = new Time(currentime);
        isPaused = true;
    }

    /**
     * onResume will be called,
     */
    public void onResume() {
        gameThread.resume();
        currentime = pausetime;
        isPaused = false;
    }
}
