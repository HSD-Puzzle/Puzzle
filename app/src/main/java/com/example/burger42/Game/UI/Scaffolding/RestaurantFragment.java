package com.example.burger42.Game.UI.Scaffolding;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Fragments.BillFragment;
import com.example.burger42.Fragments.ParentFragment;
import com.example.burger42.Fragments.PauseFragment;
import com.example.burger42.Game.GamePuppeteer;
import com.example.burger42.Game.Recipe;
import com.example.burger42.Game.Time;
import com.example.burger42.Game.UI.ItemViews.OrderView;

import com.example.burger42.Item.BillItem;
import com.example.burger42.Item.BillOrderItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * RestaurantFragment is the fragment of the game itself.
 */
public abstract class RestaurantFragment extends ParentFragment {
    private TextView tipTextView;

    private boolean timeIsUp = false;

    private TextView moneyEarnedTextView;

    private final List<OrderSpawn> listOfOrderSpawns = new LinkedList<>();
    /**
     * The view that blocks all vies above to be touched on.
     * This view contains the start countdown as well.
     */
    private RelativeLayout touchBlocker;

    /**
     * videoStoppedAt stores the second where the background video is stopped.
     */
    private int videoStoppedAt;

    private final Queue<Recipe> notSpawnedRecipes = new LinkedList<>();

    private GamePuppeteer gamePuppeteer;

    /**
     * This TextView is the view with the big countdown at the beginning.
     * It will be disabled at start with the touchBlocker
     */
    private TextView startCountdownView;
    /**
     * The displayed amount of money.
     */
    private TextView moneyText;
    private TextView clockText;
    /**
     * True after the View is created and first setup.
     */
    private boolean hadFirstSetup = false;
    /**
     * all shown items with RestaurantFragment as root.
     */
    private final List<ItemView> items = new LinkedList<>();
    /**
     * the rootView is the view from the fragment itself.
     */
    private View rootView;
    /**
     * The video view is the video in the background.
     */
    private VideoView videoView;
    /**
     * The ItemView contains all displayed items.
     */
    private FrameLayout itemRoot;
    private LinearLayout bottomCounterContainer;
    private LinearLayout topCounterContainer;

    /**
     * The referenceHeight is a value set by the first setup and will be used to scale every View inside the restaurant.
     */
    private int referenceHeight = 0;

    /**
     * Constructor
     *
     * @param mainActivity the mainActivity where this fragment runs in and the pause fragment will be starded.
     */
    public RestaurantFragment(MainActivity mainActivity) {
        super(mainActivity);
    }

    /**
     * addItem adds an ItemView to the display. Only if the Item is not part of an other Item.
     *
     * @param item the item to add.
     */
    public void addItem(ItemView item) {
        if (!items.contains(item)) {
            items.add(item);
            item.setRestaurantFragment(this);
            if (itemRoot != null) {
                addItemViewToRoot(item);
            }
        }
    }

    /**
     * removeItem removes an Item from the display.
     * Only if it is child of the RestaurantFragment not child of a child.
     *
     * @param item that will be removed
     */
    public void removeItem(ItemView item) {
        if (items.remove(item) && itemRoot != null) {
            removeItemViewToRoot(item);
        }
    }

    private void addItemViewToRoot(ItemView item) {
        item.setLayoutParams(new FrameLayout.LayoutParams(referenceHeight, referenceHeight));
        itemRoot.addView(item);
    }

    private void removeItemViewToRoot(ItemView item) {
        itemRoot.removeView(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!hadFirstSetup) {
            hadFirstSetup = true;
            firstSetup(inflater, container);
        } else {
            resume();
        }
        return rootView;
    }


    //region setup methods

    /**
     * firstSetup will be called ones by the first call of onCreate and setups the Fragment.
     *
     * @param inflater  the inflater from onCreate
     * @param container the container from onCreate
     */
    private void firstSetup(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_restaurant, container, false);
        referenceHeight = container.getHeight() / 4;

        topNavigationSetup();
        touchBlockerSetup();
        counterSetup();
        itemSetup();
        videoSetup(container);

        gamePuppeteer = new GamePuppeteer(this);
    }

    private void topNavigationSetup() {
        Button pauseButton = (Button) rootView.findViewById(R.id.restaurant_pause);
        pauseButton.setOnClickListener(view -> pause());
        moneyText = rootView.findViewById(R.id.restaurant_money);
        clockText = rootView.findViewById(R.id.restaurant_clock);
        tipTextView = rootView.findViewById(R.id.restaurant_earnedtip);
        moneyEarnedTextView = rootView.findViewById(R.id.restaurant_earnedmoney);
    }

    private void counterSetup() {
        bottomCounterContainer = ((LinearLayout) rootView.findViewById(R.id.restaurant_bottomCounterContainer));
        topCounterContainer = ((LinearLayout) rootView.findViewById(R.id.restaurant_topCounterContainer));
        for (CounterView bottomCounter : bottomCounter()) {
            addBottomCounter(bottomCounter);
        }
        for (CounterView topCounter : topCounter()) {
            addTopCounter(topCounter);
        }
    }

    private void itemSetup() {
        itemRoot = ((FrameLayout) rootView.findViewById(R.id.restaurant_root));
        itemRoot.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                ItemView itemView = (ItemView) dragEvent.getLocalState();
                if (dragEvent.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    itemView.setVisibility(View.INVISIBLE);
                    return true;
                } else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    itemView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        for (ItemView x : items) {
            addItemViewToRoot(x);
        }
        while (!notSpawnedRecipes.isEmpty()) {
            addRecipe(notSpawnedRecipes.poll());
        }
    }

    private void videoSetup(ViewGroup container) {
        videoView = rootView.findViewById(R.id.restaurant_background);
        videoView.setLayoutParams(new RelativeLayout.LayoutParams(container.getHeight() * 6, container.getHeight()));
        String videoPath = "android.resource://" + mainActivity.getPackageName() + "/" + R.raw.background;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
    }

    @SuppressLint("ClickableViewAccessibility")
    /**
     * the touch blocker blocks all touch events during the countdown
     */
    private void touchBlockerSetup() {
        touchBlocker = rootView.findViewById(R.id.restaurant_touchBlocker);
        touchBlocker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        startCountdownView = rootView.findViewById(R.id.restaurant_countdown);
    }
    //endregion

    /**
     * setMoneyText sets the text of earned money and should be called after serving.
     *
     * @param money        the money in total, that will be displayed in constantly in the right top corner.
     * @param newMainMoney the money earned with the last serving. Displayed a short time as effect in yellow.
     * @param newTip       the tip earned with the last serving.Displayed a short time as effect in blue.
     */
    public void setMoneyText(int money, int newMainMoney, int newTip) {
        moneyText.setText(money + "");

        tipTextView.setText("+" + newTip + "$");
        Animation tipDownAnim = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.tipdown_animation);
        tipTextView.startAnimation(tipDownAnim);
        tipTextView.setVisibility(View.VISIBLE);

        moneyEarnedTextView.setText("+" + newMainMoney + "$");
        Animation moneyDownAnim = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.moneydown_animation);
        moneyEarnedTextView.startAnimation(moneyDownAnim);
        moneyEarnedTextView.setVisibility(View.VISIBLE);
    }

    /**
     * bottomCounter returns all counter elements, that will bie displayed at the bottom of the restaurant.
     *
     * @return a array of bottom counters
     */
    protected abstract CounterView[] bottomCounter();

    /**
     * topCounter returns all counter elements, that will bie displayed at the top of the restaurant.
     *
     * @return a array of top counters
     */
    protected abstract CounterView[] topCounter();

    private void addBottomCounter(CounterView bottomCounter) {
        bottomCounter.setRestaurantFragment(this);
        bottomCounterContainer.addView(bottomCounter);
        bottomCounter.setLayoutParams(new LinearLayout.LayoutParams(-2, referenceHeight));
    }

    private void addTopCounter(CounterView topCounter) {
        topCounter.setRestaurantFragment(this);
        topCounterContainer.addView(topCounter);
        topCounter.setLayoutParams(new LinearLayout.LayoutParams(-2, referenceHeight));
    }

    /**
     * resume resumes the game in total. The gamePuppeteer as controller and the background video.
     */
    public void resume() {
        gamePuppeteer.onResume();
        videoView.seekTo(videoStoppedAt);
        videoView.start();
    }

    /**
     * pause pauses the game in total
     */
    public void pause() {
        gamePuppeteer.onPause();
        videoStoppedAt = videoView.getCurrentPosition();
        videoView.pause();
        mainActivity.showFragment(new PauseFragment(mainActivity, this), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    /**
     * starts the game by disabling the countdown and allow touches.
     */
    public void start() {
        videoView.start();
        touchBlocker.setEnabled(false);
        touchBlocker.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets the Countdown text. The Countdown is shown before start.
     *
     * @param text the countdown number as String.
     */
    public void setCountdown(String text) {
        startCountdownView.setText(text);
    }

    /**
     * onBackPressed will be called when the hardware back button is pressed
     */
    @Override
    public void onBackPressed() {
        pause();
    }

    /**
     * onPause will be called from the system when the app is paused.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (!timeIsUp)
            pause();
    }

    /**
     * addOrderSpawn adds a potential spawn for orders to the list of all spawns. The methode addRecipe will spawn at one of these spawns.
     *
     * @param orderSpawn the spawn to add to the list.
     */
    public void addOrderSpawn(OrderSpawn orderSpawn) {
        listOfOrderSpawns.add(orderSpawn);
    }

    /**
     * adds a recipe as order on a random spawn
     *
     * @param recipe to spawn as order
     */
    public void addRecipe(Recipe recipe) {

        if (listOfOrderSpawns.isEmpty()) {
            notSpawnedRecipes.add(recipe);
        } else {
            listOfOrderSpawns.get((int) (Math.random() * listOfOrderSpawns.size())).spawnAndPlaceRecipe(recipe);
        }
    }

    /**
     * puts a new dirty plate on the counter
     */
    public void dirtyPlateBack() {
        //TODO add dirty-plate
    }

    /**
     * shows the given time in the fragment
     *
     * @param time the time to show
     */
    public void setTimeText(Time time) {
        clockText.setText(time.timeAsText());
    }


    //serve aufrufen
    public void timesUp(List<BillItem> list, int totalValue) {
        timeIsUp = true;
        mainActivity.showFragment(new BillFragment(mainActivity, list, totalValue), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    /**
     * tells the gamePuppeteer to serve an Item
     *
     * @param order the order that was ordered.
     * @param item  the item that will be delivered.
     */
    public void serve(Recipe order, Recipe item) {
        gamePuppeteer.serve(order, item);
    }
}
