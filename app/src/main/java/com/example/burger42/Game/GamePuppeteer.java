package com.example.burger42.Game;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.Timer.GameThread;
import com.example.burger42.Game.Timer.GameTimer;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.Item.BillItem;
import com.example.burger42.Item.BillOrderItem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GamePuppeteer {

    private final GameThread gameThread;

    private RestaurantFragment restaurantFragment;
    private RecipeGenerator generator;
    private int amountOfRecipesServed = 0;
    private int money = 0;
    private float streak = 1.0f;
    public Time currentime;
    private final List<BillItem> billItemList;

    public GamePuppeteer(RestaurantFragment restaurantFragment, RecipeGenerator recipeGenerator) {
        billItemList = new LinkedList<>();
        this.restaurantFragment = restaurantFragment;
        currentime = new Time(8, 0);
        generator = recipeGenerator;
        gameThread = new GameThread(restaurantFragment.getActivity());
    }

    public void start() {
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
                restaurantFragment.addRecipe(generator.createRecipe(currentime));

                gameThread.addGameTimer(new GameTimer() {

                    @Override
                    public void tick(long timeSinceRegistration) {
                        System.out.println(timeSinceRegistration);
                        currentime.setTimeInMilliSeconds(timeSinceRegistration * 120 + 28800000);
                        restaurantFragment.setTimeText(currentime);
                    }

                    @Override
                    public void finish() {
                        restaurantFragment.timesUp(billItemList, money);
                    }
                }, 240000, 500);
            }
        }, 3300, 1000);
    }

    public void serve(Recipe order, Recipe item) {
        List<BillOrderItem> list = new LinkedList<>();
        int newMoney = 0;
        int restOfTheTime = order.restOfTheTime(currentime);
        int tip = Math.max(restOfTheTime * (order.list().size() * 2 + 2), 0);
        for (List<Ingredient> orderList : order.list()) {
            if (item.list().contains(orderList)) {
                item.list().remove(orderList);
                int singleBurgerMoney = orderList.size() * 10;
                list.add(new BillOrderItem("Burger", singleBurgerMoney, streak, true));
                newMoney += singleBurgerMoney * streak;
                if (restOfTheTime > 0) {
                    streak = streak * 1.06f;
                } else {
                    streak = streak * 1.02f;
                }
            } else {
                list.add(new BillOrderItem("Burger", 0, streak, false));
                streak = 1.0f;
                tip = 0;
            }
        }
        list.add(new BillOrderItem("Tip", 0, streak, true));
        money += tip * streak;
        money += newMoney;
        billItemList.add(new BillItem(++amountOfRecipesServed, (newMoney + tip) * streak, list));
        restaurantFragment.setMoneyText(money, newMoney, tip, streak);
    }

    /**
     * onPause will be called, when the RestaurantFragment is paused.
     */
    public void onPause() {
        gameThread.pause();
    }

    /**
     * onResume will be called,
     */
    public void onResume() {
        gameThread.start();
    }

    /**
     * @return the gameThread from the GamePuppeteer
     */
    public GameThread currentlyUsedGameThread() {
        return gameThread;
    }
}
