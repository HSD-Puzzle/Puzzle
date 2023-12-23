package com.example.burger42.Game.UI.Scaffolding;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Fragments.ParentFragment;
import com.example.burger42.Fragments.PauseFragment;
import com.example.burger42.Game.GamePuppeteer;
import com.example.burger42.Game.UI.ItemViews.Order;
import com.example.burger42.Game.UI.ItemViews.PlateView;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

/**
 * RestaurantFragment is the fragment of the game itself.
 */
public abstract class RestaurantFragment extends ParentFragment {

    private GamePuppeteer gamePuppeteer;
    /**
     * The view that blocks all vies above to be touched on.
     * This view contains the start countdown as well.
     */
    private RelativeLayout touchBlocker;
    /**
     * This TextView is the view with the big countdown at the beginning.
     * It will be disabled at start with the touchBlocker
     */
    private TextView startCountdownView;
    /**
     * The displayed amount of money.
     */
    private TextView moneyText;
    private boolean hadFirstSetup = false;

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
        ItemView itemView = new Order(mainActivity);
        itemView.setTranslationY(500);
        addItem(itemView);
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
        touchBLockerSetup();
        counterSetup();
        itemSetup();
        videoSetup(container);

        gamePuppeteer = new GamePuppeteer(this);
    }

    private void topNavigationSetup() {
        Button pauseButton = (Button) rootView.findViewById(R.id.restaurant_pause);
        pauseButton.setOnClickListener(view -> pause());
        moneyText = rootView.findViewById(R.id.restaurant_money);
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
    }

    private void videoSetup(ViewGroup container) {
        videoView = rootView.findViewById(R.id.restaurant_background);
        videoView.setLayoutParams(new RelativeLayout.LayoutParams(container.getHeight() * 6, container.getHeight()));
        String videoPath = "android.resource://" + mainActivity.getPackageName() + "/" + R.raw.background;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void touchBLockerSetup() {
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

    public void setMoneyText(int money) {
        moneyText.setText(money);
    }

    protected abstract CounterView[] bottomCounter();

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

    private int videoStoppedAt;

    public void resume() {
        videoView.seekTo(videoStoppedAt);
        videoView.start();
    }

    public void pause() {
        videoStoppedAt = videoView.getCurrentPosition();
        videoView.pause();
        mainActivity.showFragment(new PauseFragment(mainActivity, this), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    public void start() {
        videoView.start();
        touchBlocker.setEnabled(false);
        touchBlocker.setVisibility(View.INVISIBLE);
    }

    public void setCountdown(String text) {
        videoView.start();
        startCountdownView.setText(text);
    }

    @Override
    public void onBackPressed() {
        pause();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }
}
