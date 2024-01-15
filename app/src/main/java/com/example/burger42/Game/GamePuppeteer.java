package com.example.burger42.Game;

import com.example.burger42.Game.Generator.RecipeGenerator;
import com.example.burger42.Game.Timer.GameThread;
import com.example.burger42.Game.Timer.GameTimer;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.Ingredients.Ingredient;
import com.example.burger42.Item.BillItem;
import com.example.burger42.Item.BillDetailItem;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

/**
 * A Class which manages the Gameflow (Timers, Points) and generation of Orders.
 */
public class GamePuppeteer {

    private final GameThread gameThread;
    private RestaurantFragment restaurantFragment;
    private RecipeGenerator generator;
    private int amountOfRecipesServed = 0;
    private int money = 0;
    private float currentStreak = 1.0f;
    public Time currentime;
    private final List<BillItem> billItemList;

    /**
     * These GameResultStatistics contain various statistics from this match for the evaluation of stars.
     */
    public static class GameResultStatistics {
        /**
         * income is the money made during the whole match.
         */
        private int income;
        /**
         * amountOfSuccessfulSoldBurger is the total amount of all successful sold burgers.
         */
        private int amountOfSuccessfulSoldBurger;
        /**
         * largestStreakAchieved is the value that the streak had at the highest point in time.
         */
        private float largestStreakAchieved;
        /**
         * The number of orders for which tips were received.
         */
        private int numberOfTipsReceived;
        /**
         * The number of orders for which no tip was received
         */
        private int numberOfTipsLost;

        public int income() {
            return income;
        }

        public float largestStreakAchieved() {
            return largestStreakAchieved;
        }

        public int amountOfSoldBurger() {
            return amountOfSuccessfulSoldBurger;
        }
    }

    public GamePuppeteer(RestaurantFragment restaurantFragment, RecipeGenerator recipeGenerator) {
        this.restaurantFragment = restaurantFragment;
        billItemList = new LinkedList<>();
        currentime = new Time(8, 0);
        generator = recipeGenerator;
        gameThread = new GameThread(restaurantFragment.getActivity());
    }

    /**
     * A Method to start the GamePuppeteer, it initializes and starts all the Timers the Game needs.
     */
    public void start() {
        gameThread.start();
        startBeforeStartCountdown();
    }

    /**
     * Starts the countdown for the round and calls startDayTimer after the finish.
     */
    private void startBeforeStartCountdown() {
        restaurantFragment.setCountdown("3");
        gameThread.addGameTimer(new GameTimer() {
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

            public void finish() {
                startShift();
            }
        }, 3300, 1000);
    }

    /**
     *
     */
    private void startShift() {
        restaurantFragment.start();
        startCreateRecipeTimer();
        gameThread.addGameTimer(new GameTimer() {
            @Override
            public void tick(long timeSinceRegistration) {
                currentime.setTimeInMilliSeconds(timeSinceRegistration * 60 + 28800000);
                restaurantFragment.setTimeText(currentime);
            }

            @Override
            public void finish() {
                timeIsDone();
            }
        }, 240000, 500);
    }

    /**
     * Once started, creates recipes at intervals throughout the game.
     */
    private void startCreateRecipeTimer() {
        gameThread.addGameTimer(new GameTimer() {
            @Override
            public void tick(long timeSinceRegistration) {
                //TODO generate fewer recipes when there are many recipes in circulation.
                // Generate more recipes with a larger streak.
                restaurantFragment.addRecipe(generator.createRecipe(currentime, currentStreak));
            }

            @Override
            public void finish() {
            }
        }, 1000);
    }

    /**
     * timeIsDone is called when the time has expired.
     * The final statistics are created and the level is completed.
     */
    //TODO Fill statistics with data.
    private void timeIsDone() {
        gameThread.pause();
        restaurantFragment.timesUp(billItemList, money);
    }

    /**
     * Method that will be called, when an finished Burger is dragged on to the order or
     * the other way round. It calculates the Points per correct Burger
     * and adds or corrects the Streak.
     *
     * @param order Recipe of the order
     * @param item  Recipe of the item or finished Burger
     */
    //TODO Fill statistics with data.
    public void serve(Recipe order, Recipe item) {
        List<BillDetailItem> list = new LinkedList<>();
        int newMoney = 0;
        int restOfTheTime = order.restOfTheTime(currentime);
        int tip = Math.max(restOfTheTime * (order.list().size() * 2 + 2), 0);
        for (List<Ingredient> orderList : order.list()) {
            if (item.list().contains(orderList)) {
                item.list().remove(orderList);
                int singleBurgerMoney = orderList.size() * 10;
                list.add(new BillDetailItem(restaurantFragment.getString(R.string.burger), singleBurgerMoney, currentStreak, true));
                newMoney += singleBurgerMoney * currentStreak;
                if (restOfTheTime > 0) {
                    currentStreak = currentStreak * 1.06f;
                } else {
                    currentStreak = currentStreak * 1.02f;
                }
            } else {
                list.add(new BillDetailItem(restaurantFragment.getString(R.string.burger), 0, currentStreak, false));
                currentStreak = 1.0f;
                tip = 0;
            }
        }
        list.add(new BillDetailItem(restaurantFragment.getString(R.string.tip), tip, currentStreak, true));
        tip = (int) (tip * currentStreak);
        money += tip;
        money += newMoney;
        billItemList.add(new BillItem(++amountOfRecipesServed, (newMoney + tip), list));
        restaurantFragment.setMoneyText(money, newMoney, tip, currentStreak);
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
