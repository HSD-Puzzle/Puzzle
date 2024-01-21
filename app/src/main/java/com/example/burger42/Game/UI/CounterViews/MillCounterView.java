package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.PlateView;
import com.example.burger42.Game.UI.ItemViews.SpongeView;
import com.example.burger42.Game.UI.Scaffolding.BottomCounterItemSpawnCounterView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.R;

/**
 * The implementation of the mill counter view
 */
public class MillCounterView extends BottomCounterItemSpawnCounterView {

    /**
     * the states the mill can have
     */
    private enum state {
        CLEAN, DIRTY, EMPTY
    }

    /**
     * the last State the view had
     */
    private state lastState;
    /**
     * The new created plate after the last touch
     */
    private PlateView newPlate;
    /**
     * the state this currently have
     */
    private state currentState;

    public MillCounterView(Context context) {
        super(context);
    }

    public MillCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * sets the current state to dirty to start with a dirty plate in the mill
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void beforeInit(Context context, @Nullable AttributeSet attrs) {
        this.currentState = state.DIRTY;
    }

    /**
     * adds the listeners to drag plates in and out the mill
     * and listeners to place items on the drip tray
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        spongeView = new SpongeView(getContext());
        addOnTouchAreaListener(new OnTouchAreaListener(0.51f, 0.29f, 0.027f, 0.55f) {

            @Override
            protected boolean onTouch(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (currentState == state.CLEAN) {
                        newPlate = new PlateView(getContext());
                        setCurrentState(state.EMPTY);
                    } else if (currentState == state.DIRTY) {
                        newPlate = new PlateView(getContext());
                        newPlate.setCurrentState(PlateView.state.DIRTY);
                        setCurrentState(state.EMPTY);
                    } else if (currentState == state.EMPTY) return false;
                    if (!drag(newPlate)) {
                        setCurrentState(lastState);
                        newPlate.removeFromParent();
                        newPlate = null;
                    }
                    return true;
                }
                return false;
            }
        });

        //Reset state after dropping new Plate
        addOnDragAreaListener(new OnDragAreaListener(1, 0, 0, 1) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (event.getAction() == DragEvent.ACTION_DRAG_ENDED && event.getLocalState() == newPlate) {
                    if (!event.getResult()) {
                        setCurrentState(lastState);
                        newPlate.removeFromParent();
                    }
                    newPlate = null;
                }
                return false;
            }
        }.setUseFilter(true).addFilterTag("Plate"));

        //Put plate in mill and clean Table with sponge
        addOnDragAreaListener(new OnDragAreaListener(0.51f, 0.29f, 0.027f, 0.55f) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (event.getLocalState() instanceof PlateView) {
                    if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                        PlateView plateView = (PlateView) event.getLocalState();
                        if (currentState == state.EMPTY && plateView.hasNoItemAbove()) {
                            plateView.removeFromParent();
                            switch (plateView.currentState()) {
                                case DIRTY:
                                    setCurrentState(state.DIRTY);
                                    break;
                                case CLEAN:
                                    setCurrentState(state.CLEAN);
                                    break;
                            }
                            return true;
                        }
                        return false;
                    }
                }
                if (event.getLocalState() instanceof SpongeView && inArea) {
                    if (currentState == state.DIRTY) {
                        setCurrentState(state.CLEAN);
                    }
                }
                return false;
            }
        }.setUseFilter(true).addFilterTag("Plate").addFilterTag("Sponge"));

        //Put plate an sides
        addOnDragAreaListener(new OnDragAreaListener(0.5f, 0.32f, 0.59f, 0.95f) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    PlateView itemView = (PlateView) event.getLocalState();
                    if (itemView.onlyPlatesAbove()) {
                        itemView.removeFromParent();
                        restaurantFragment.addItem(itemView);
                        itemView.setTranslationY(getCustomHeight() * 1.40f);
                        itemView.setTranslationX(getX() + getCustomWidth() * 0.52f);
                        return true;
                    }
                }
                return false;
            }
        }.setUseFilter(true).addFilterTag("Plate"));
    }

    /**
     * sets the current state and redraws this view to show it graphically
     *
     * @param currentState the new state
     */
    public void setCurrentState(state currentState) {
        lastState = this.currentState;
        this.currentState = currentState;
        loadTexture();
        invalidate();
    }

    @Override
    protected int drawableId() {
        switch (currentState) {
            case CLEAN:
                return R.drawable.millcleancorner;
            case DIRTY:
                return R.drawable.milldirtycorner;
            case EMPTY:
                return R.drawable.millemptycorner;
            default:
                return R.drawable.millcleancorner;

        }
    }

    /**
     * The sponge that can be used to clean plates
     */
    private SpongeView spongeView;

    /**
     * after this is drawn the position of the sponge can be calculated and set.
     */
    @Override
    protected void afterDraw() {
        super.afterDraw();
        spongeView.setTranslationX(getX() + getCustomWidth() * 0.4f);
        spongeView.setTranslationY(getCustomHeight() * 1.26f);
    }

    /**
     * the sponge can be created and placed in the restaurant
     *
     * @param restaurantFragment the bound restaurant fragment.
     */
    @Override
    protected void onRestaurantBound(RestaurantFragment restaurantFragment) {
        super.onRestaurantBound(restaurantFragment);
        restaurantFragment.addItem(spongeView);
    }

}
