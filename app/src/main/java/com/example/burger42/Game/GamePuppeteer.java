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
    private int money = 150;
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
        startCountdown = new CountDownTimer(3300, 1000) {
            long lastL = 3300;
            @Override
            public void onTick(long l) {
                if(isPaused)
                    l = lastL;
                else{
                    lastL = l;
                    if (l < 300)
                        restaurantFragment.setCountdown("Start");
                    else if (l < 1300)
                        restaurantFragment.setCountdown("1");
                    else if (l < 2300)
                        restaurantFragment.setCountdown("2");
                    else if (l < 3300)
                        restaurantFragment.setCountdown("3");
                }
            }

            @Override
            public void onFinish() {
                restaurantFragment.start();
                restaurantFragment.addRecipe(generator.createRecipe());

                //TEST Recepie add
                recipeCountdown = new CountDownTimer(20000, 5000) {
                    public long lastL = 20000;
                    @Override
                    public void onTick(long l) {
                        if(isPaused)
                            l = lastL;
                        else{
                            lastL = l;
                            restaurantFragment.addRecipe(generator.createRecipe());
                        }
                    }

                    @Override
                    public void onFinish() {
                    }
                }.start();
                //TEST GameTime add
                gameCountdown = new CountDownTimer(240000, 500) {
                    long lastL = 240000;
                    @Override
                    public void onTick(long l) {
                        currentime.addTimeInMilliSeconds((lastL - l) * 120);
                        lastL = l;
                        restaurantFragment.setTimeText(currentime);
                    }

                    @Override
                    public void onFinish() {
                        restaurantFragment.timesUp(billItemList);
                    }
                }.start();

            }
        }.start();
    }

    public void serve(Recipe order, Recipe item) {
        List<BillOrderItem> list = new LinkedList<>();
        newMoney = 0;
        Iterator<List<Ingredient>> orderIterator = order.list().iterator();
        while (orderIterator.hasNext()) {
            List<Ingredient> orderList = orderIterator.next();
            if(item.list().contains(orderList)) {
                item.list().remove(orderList);
                money+=orderList.size()*10*streak;
                newMoney+=orderList.size()*10*streak;
                list.add(new BillOrderItem("Burger",newMoney,streak,true));
                streak+=0.1;
                tip = order.calculateTip();
            }
            else{
                list.add(new BillOrderItem("Burger",0,streak,false));
                streak=1.0f;
                tip = 0;
            }
        }
        billItemList.add(new BillItem(index++,(newMoney+tip)*streak,list));
        restaurantFragment.setMoneyText(money, newMoney, tip);
    }

    /**
     * onPause will be called, when the RestaurantFragment is paused.
     */
    public void onPause() {
        pausetime = new Time(currentime);
        isPaused = true;
        }
    /**
     * onResume will be called,
     */
    public void onResume() {
        currentime = pausetime;
        isPaused = false;
    }
}
