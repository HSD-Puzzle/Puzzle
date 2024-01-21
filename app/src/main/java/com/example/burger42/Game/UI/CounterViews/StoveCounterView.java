package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Timer.GameTimer;
import com.example.burger42.Game.UI.ItemViews.BurgerPattyView;
import com.example.burger42.Game.UI.Scaffolding.BottomCounterItemSpawnCounterView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

/**
 * The implementation of the stove counter view, that allows roasting the patties
 */
public class StoveCounterView extends BottomCounterItemSpawnCounterView {
    /**
     * a List of all BurgerPatties, that are currently on the left head plate
     */
    private List<BurgerPattyView> leftHeadPlate;
    /**
     * a List of all BurgerPatties, that are currently on the right head plate
     */
    private List<BurgerPattyView> rightHeadPlate;
    /**
     * true if the right head plate is active else false
     */
    private boolean rightOn = false;
    /**
     * true if the left head plate is active else false
     */
    private boolean leftOn = false;

    public StoveCounterView(Context context) {
        super(context);
    }

    public StoveCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * adds listener to place patties on the plates.
     * adds listener to turn the head plates on and off.
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        leftHeadPlate = new LinkedList<>();
        rightHeadPlate = new LinkedList<>();
        // turn the head plates on or off
        addOnTouchAreaListener(new OnTouchAreaListener(0.25f, 0.15f, 0.2f, 0.33f) {
            @Override
            protected boolean onTouch(MotionEvent event) {
                setLeftOn(!leftOn);
                return true;
            }
        });
        addOnTouchAreaListener(new OnTouchAreaListener(0.25f, 0.15f, 0.67f, 0.8f) {
            @Override
            protected boolean onTouch(MotionEvent event) {
                setRightOn(!rightOn);
                return true;
            }
        });

        // place patties on the plates
        addOnDragAreaListener(new StoveTopDragListener(leftHeadPlate).setRelativeRight(0.5f).setRelativeLeft(0.05f));
        addOnDragAreaListener(new StoveTopDragListener(rightHeadPlate).setRelativeLeft(0.51f).setRelativeRight(0.95f));
    }

    /**
     * adds a timer, that roasts the patties every 250ms
     *
     * @param restaurantFragment the bound restaurant fragment.
     */
    @Override
    protected void onRestaurantBound(RestaurantFragment restaurantFragment) {
        restaurantFragment.currentlyUsedGameThread().addGameTimer(new GameTimer() {
            @Override
            public void tick(long timeSinceRegistration) {
                if (rightOn) {
                    for (BurgerPattyView x : rightHeadPlate) {
                        x.roast();
                    }
                }
                if (leftOn) {
                    for (BurgerPattyView x : leftHeadPlate) {
                        x.roast();
                    }
                }
            }
        }, 250);
    }

    /**
     * The Listener used to place patties on the head plates
     */
    private class StoveTopDragListener extends OnDragAreaListener {
        private final List<BurgerPattyView> burgerOnHeadPlate;

        private StoveTopDragListener(List<BurgerPattyView> burgerOnHeadPlate) {
            setRelativeBottom(0.31f);
            setRelativeTop(0.56f);
            setUseFilter(true);
            addFilterTag("BurgerPatty");
            this.burgerOnHeadPlate = burgerOnHeadPlate;
        }

        @Override
        protected boolean onDrag(DragEvent event, boolean inArea) {
            if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                BurgerPattyView itemView = (BurgerPattyView) event.getLocalState();
                if (itemView.hasNoItemAbove()) {
                    itemView.removeFromParent();
                    restaurantFragment.addItem(itemView);
                    itemView.setTranslationY(getCustomHeight() * 0.8f + Math.min(Math.max(event.getY(), getCustomHeight() * 0.5f), getCustomHeight() * 0.69f - itemView.getCustomHeight()));
                    itemView.setTranslationX(getX() + Math.min(Math.max(event.getX() - itemView.getCustomWidth() / 2f, getCustomWidth() * relativeLeft()), getCustomWidth() * relativeRight() - itemView.getCustomWidth()));
                    itemView.putOnHeadPlate(burgerOnHeadPlate);
                    burgerOnHeadPlate.add(itemView);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * sets the right head plate on or off
     *
     * @param on the value the right head plate should have after this
     */
    public void setRightOn(boolean on) {
        setAndReloadTexture(leftOn, on);
    }

    /**
     * sets the left head plate on or off
     *
     * @param on the value the left head plate should have after this
     */
    public void setLeftOn(boolean on) {
        setAndReloadTexture(on, rightOn);
    }

    /**
     * redraws this view after changing the state
     *
     * @param leftOn  the status the left head plate should have
     * @param rightOn the status the right head plate should have
     */
    public void setAndReloadTexture(boolean leftOn, boolean rightOn) {
        this.leftOn = leftOn;
        this.rightOn = rightOn;
        loadTexture();
        invalidate();
    }

    @Override
    protected int drawableId() {
        if (rightOn) {
            if (leftOn) {
                return R.drawable.stoveon;
            } else {
                return R.drawable.stover;
            }
        } else {
            if (leftOn) {
                return R.drawable.stovel;
            } else {
                return R.drawable.stoveoff;
            }
        }
    }
}
