package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Timer.GameTimer;
import com.example.burger42.Game.UI.ItemViews.BurgerPattyView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

public class StoveCounterView extends CounterView {

    private List<BurgerPattyView> leftHeadPlate;
    private List<BurgerPattyView> rightHeadPlate;
    private boolean rightOn = false;
    private boolean leftOn = false;

    public StoveCounterView(Context context) {
        super(context);
    }

    public StoveCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        leftHeadPlate = new LinkedList<>();
        rightHeadPlate = new LinkedList<>();
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

        addOnDragAreaListener(new StoveTopDragListener(leftHeadPlate).setRelativeRight(0.5f).setRelativeLeft(0.05f));
        addOnDragAreaListener(new StoveTopDragListener(rightHeadPlate).setRelativeLeft(0.51f).setRelativeRight(0.95f));
    }

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

    public void setRightOn(boolean on) {
        setAndReloadTexture(leftOn, on);
    }

    public void setLeftOn(boolean on) {
        setAndReloadTexture(on, rightOn);
    }

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
