package com.example.burger42.Game.UI.Scaffolding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
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
import com.example.burger42.Game.Timer.GameThread;
import com.example.burger42.Item.BillItem;
import com.example.burger42.Item.StarItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * RestaurantFragment is the fragment of the game itself.
 */
public abstract class RestaurantFragment extends ParentFragment {

    /**
     * the starItems used for this level.
     */
    private StarItem[] starItems;

    /**
     * The ids of the 4 background videos
     */
    private final static int[] backgroundVideo = {R.raw.background1, R.raw.background2, R.raw.background3, R.raw.background4};

    /**
     * the video that is currently playing
     */
    private int currentlyPlayedVideo = 0;

    /**
     * tipTextView is the view that displays the last tip earned.
     */
    private TextView tipTextView;

    /**
     * streakTextView is the view that displays the current streak.
     */
    private TextView streakTextView;

    /**
     * timeIsUp is true when the time has expired, i.e. the game is over.
     */
    private boolean timeIsUp = false;
    /**
     * moneyEarnedTextView displays the new money earned from the last sale.
     */
    private TextView moneyEarnedTextView;

    /**
     * listOfOrderSpawns contains all locations where a recipe can be created graphically.
     */
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
    /**
     * notSpawnedRecipes contains all recipes that have not yet been created graphically.
     * This can happen if the graphic basis is not yet available.
     * When the graphic is created for the first time, all elements of the list are displayed.
     */
    private final Queue<Recipe> notSpawnedRecipes = new LinkedList<>();

    /**
     * the gamePuppeteer, which controls the current level.
     */
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
    /**
     * the display of the time
     */
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
    /**
     * The container, which contains all the lower counter components.
     */
    private LinearLayout bottomCounterContainer;
    /**
     * The container, which contains all the upper counter components.
     */
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
        starItems = createStarItems();
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
            removeItemViewFromRoot(item);
        }
    }

    /**
     * addItemViewToRoot adds the passed item to the ItemRoot object as a child in order to display the item graphically.
     *
     * @param item the item to be displayed
     */
    private void addItemViewToRoot(ItemView item) {
        item.setLayoutParams(new FrameLayout.LayoutParams(referenceHeight, referenceHeight));
        itemRoot.addView(item);
    }

    /**
     * removeItemViewFromRoot removes an item from the display as a separate item.
     *
     * @param item the item to be removed
     */
    private void removeItemViewFromRoot(ItemView item) {
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
        //The view of the restaurant fragment itself is generated
        rootView = inflater.inflate(R.layout.fragment_restaurant, container, false);
        //A reference size is determined, which is used to determine the size of all other objects.
        referenceHeight = container.getHeight() / 4;
        //the gamePuppeteer is created, which acts as controller over the level after the start.
        gamePuppeteer = gamePuppeteer();

        //The top navigation bar is set. The pause button and the various displays.
        topNavigationSetup();
        //The touch blockade is discontinued. This includes the countdown and prevents entries before the actual game starts.
        touchBlockerSetup();
        //The counter is created and set.
        counterSetup();
        //the ItemRoot is set and all items from the queue are displayed.
        itemSetup();
        //the background video is set.
        videoSetup(container);
        //The game is started.
        gamePuppeteer.start();
    }

    /**
     * The top navigation bar is set.
     * The pause button and the various displays.
     */
    private void topNavigationSetup() {
        Button pauseButton = rootView.findViewById(R.id.restaurant_pause);
        pauseButton.setOnClickListener(view -> pause());
        moneyText = rootView.findViewById(R.id.restaurant_money);
        clockText = rootView.findViewById(R.id.restaurant_clock);
        tipTextView = rootView.findViewById(R.id.restaurant_earnedtip);
        streakTextView = rootView.findViewById(R.id.restaurant_streak);
        moneyEarnedTextView = rootView.findViewById(R.id.restaurant_earnedmoney);
    }

    /**
     * The counter is created and set.
     */
    private void counterSetup() {
        bottomCounterContainer = rootView.findViewById(R.id.restaurant_bottomCounterContainer);
        topCounterContainer = rootView.findViewById(R.id.restaurant_topCounterContainer);
        for (CounterView bottomCounter : bottomCounter()) {
            addBottomCounter(bottomCounter);
        }
        for (CounterView topCounter : topCounter()) {
            addTopCounter(topCounter);
        }
    }

    /**
     * the ItemRoot is set and all items from the queue are displayed.
     */
    private void itemSetup() {
        //itemRoot is bound
        itemRoot = rootView.findViewById(R.id.restaurant_root);
        //When an object is dragged, the actual object becomes invisible so that only the shadow is visible. And after dropping, the object becomes visible again.
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
        //Items are displayed
        for (ItemView x : items) {
            addItemViewToRoot(x);
        }
        while (!notSpawnedRecipes.isEmpty()) {
            addRecipe(notSpawnedRecipes.poll());
        }
    }

    /**
     * the background video is set.
     *
     * @param container the container in which the video is displayed. Its dimensions are used to determine the size.
     */
    private void videoSetup(ViewGroup container) {
        videoView = rootView.findViewById(R.id.restaurant_background);
        videoView.setLayoutParams(new RelativeLayout.LayoutParams(container.getHeight() * 6, container.getHeight()));
        setNextBackgroundVideoURI();
        //After a video has been played, the next video is started.
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                setNextBackgroundVideoURI();
                videoView.start();
            }
        });
    }

    /**
     * The Uri to the next background video is set if one is available.
     */
    private void setNextBackgroundVideoURI() {
        if (currentlyPlayedVideo < backgroundVideo.length) {
            String videoPath = "android.resource://" + mainActivity.getPackageName() + "/" + backgroundVideo[currentlyPlayedVideo++];
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
        }
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
     * @param streak       the streak value to display. If it is zero it will be invisible.
     */
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void setMoneyText(int money, int newMainMoney, int newTip, float streak) {
        moneyText.setText(money + "");

        tipTextView.setText("+" + newTip + "$");
        Animation tipDownAnim = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.tipdown_animation);
        tipTextView.startAnimation(tipDownAnim);

        moneyEarnedTextView.setText("+" + newMainMoney + "$");
        Animation moneyDownAnim = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.moneydown_animation);
        moneyEarnedTextView.startAnimation(moneyDownAnim);

        streakTextView.setVisibility(streak <= 1f ? View.INVISIBLE : View.VISIBLE);
        streakTextView.setText("x" + String.format("%.2f", streak));
        float size = Math.min(streak - 1, 1);
        streakTextView.setRotation(size * 15);
        streakTextView.setTextSize(15 + size * 7);
        streakTextView.setTextColor((255 << 24) + ((int) (Math.min(size * 2, 1) * 255) << 16) + ((int) (Math.min((2f - size * 2), 1) * 255) << 8));
    }

    /**
     * bottomCounter returns all counter elements, that will bie displayed at the bottom of the restaurant.
     * s
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

    /**
     * adds a lower counter section to the container of all lower counter sections and sets it to the correct size.
     *
     * @param bottomCounter the added lower counter section.
     */
    private void addBottomCounter(CounterView bottomCounter) {
        bottomCounter.setRestaurantFragment(this);
        bottomCounterContainer.addView(bottomCounter);
        bottomCounter.setLayoutParams(new LinearLayout.LayoutParams(-2, referenceHeight));
    }

    /**
     * adds an upper counter section to the container of all upper counter sections and sets it to the correct size.
     *
     * @param topCounter the upper counter section to be added.
     */
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

    public void timesUp(List<BillItem> list, int totalValue, GamePuppeteer.GameResultStatistics statistics) {
        timeIsUp = true;
        for (StarItem x : starItems) {
            x.calculate(statistics);
        }
        highScore = totalValue;
        saveData();
        mainActivity.showFragment(new BillFragment(mainActivity, list, totalValue, starItems), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
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

    /**
     * @return the gameThread from the GamePuppeteer
     */
    public GameThread currentlyUsedGameThread() {
        return gamePuppeteer.currentlyUsedGameThread();
    }

    /**
     * @return an instance of the used GamePuppeteer
     */
    public abstract GamePuppeteer gamePuppeteer();

    public void loadData() {
        SharedPreferences preferences = mainActivity.getSharedPreferences(levelId(), Context.MODE_PRIVATE);
        highScore = preferences.getInt("highscore", 0);
        for (int i = 0; i < starItems.length; i++) {
            starItems[i].setIsDone(preferences.getBoolean("star" + i, false));
        }
    }

    public void saveData() {
        SharedPreferences preferences = mainActivity.getSharedPreferences(levelId(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getInt("highscore", 0) < highScore)
            editor.putInt("highscore", highScore);
        for (int i = 0; i < starItems.length; i++) {
            if (!preferences.getBoolean("star" + i, false))
                editor.putBoolean("star" + i, starItems[i].done());
        }
        editor.apply();
    }

    protected abstract String levelId();

    private int highScore;

    public int highScore() {
        return highScore;
    }

    public abstract String title();

    public abstract int thumbnailId();

    public StarItem[] starItems() {
        return starItems;
    }

    /**
     * creates the Level specific StarItems
     *
     * @return the Level specific StarItems
     */
    protected abstract StarItem[] createStarItems();
}
