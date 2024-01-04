package com.example.burger42.Game;

import android.os.CountDownTimer;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.Item.BillIngredientItem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GamePuppeteer {
    private RestaurantFragment restaurantFragment;
    private CountDownTimer startCountdown;
    private RecipeGenerator generator;
    private List<BillIngredientItem> list;
    private int money;
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
        //System.out.println(item);
        Iterator<List<Ingredient>> orderIterator = order.list().iterator();
        Iterator<List<Ingredient>> itemIterator = item.list().iterator();
        while (orderIterator.hasNext() && itemIterator.hasNext()) {
            List<Ingredient> orderList = orderIterator.next();
            List<Ingredient> itemList = itemIterator.next();
            for (int i = 0; i < orderList.size() - 1; i++) {
                if (orderList.get(i).equals(itemList.get(i))) {
                    money += 10;
                    list.add(new BillIngredientItem(orderList.get(i), true, 10));
                } else {
                    list.add(new BillIngredientItem(orderList.get(i), false, 0));
                }
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
