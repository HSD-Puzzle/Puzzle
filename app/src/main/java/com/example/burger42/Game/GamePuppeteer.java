package com.example.burger42.Game;

import android.os.CountDownTimer;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.Item.BillOrderItem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GamePuppeteer {
    private RestaurantFragment restaurantFragment;
    private CountDownTimer startCountdown;
    private RecipeGenerator generator;
    private List<BillOrderItem> list;
    private int money;
    private float streak = 1.0f;
    public static Time currentime;


    public GamePuppeteer(RestaurantFragment restaurantFragment) {
        list = new LinkedList<>();
        this.restaurantFragment = restaurantFragment;
        currentime = new Time(8, 0);
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
                        restaurantFragment.setMoneyText(150, 100, 50);
                    }

                    @Override
                    public void onFinish() {
                    }
                }.start();

                //TEST GameTime add
                new CountDownTimer(240000, 500) {


                    long lastL = 240000;

                    @Override
                    public void onTick(long l) {
                        currentime.addTimeInMilliSeconds((lastL - l) * 120);
                        lastL = l;
                        restaurantFragment.setTimeText(currentime);
                    }

                    @Override
                    public void onFinish() {
                        restaurantFragment.timesUp(list);
                    }
                }.start();

            }
        }.start();
    }

    public void serve(Recipe order, Recipe item) {
        Iterator<List<Ingredient>> orderIterator = order.list().iterator();
        while (orderIterator.hasNext()) {
            List<Ingredient> orderList = orderIterator.next();
            if(item.list().contains(orderList)) {
                item.list().remove(orderList);
                money+=orderList.size()*10*streak;
                list.add(new BillOrderItem("Burger",orderList.size()*10,streak,true));
                streak+=0.1;
            }
            else{
                list.add(new BillOrderItem("Burger",0,streak,false));
                streak=1.0f;
            }
        }
        /*
         * TODO
         *  jeder order mit jedem item vergleichen,
         *  da die Reihenfolge der Burger in der Bestellung nicht zu den auf den Tellern passen muss.
         * TODO
         *  die größt mögliche Summe als Gewinn ermitteln wichtig kein Item darf doppelt berechnet werden.
         * TODO
         *  Geld abziehen für zu viel gelieferte burger
         *
         * Einfacher
         * wir akzeptieren nur in gänze richtige Burger, dann brauchen wir nur order prüfen ob sie in item sind.
         * Und den Wert der Zutaten aufaddieren.
         */
        restaurantFragment.setMoneyText(money, money, 0);
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
