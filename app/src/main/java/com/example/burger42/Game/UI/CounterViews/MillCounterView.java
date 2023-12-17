package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.BottomBreadView;
import com.example.burger42.Game.UI.ItemViews.PlateView;
import com.example.burger42.Game.UI.ItemViews.SpongeView;
import com.example.burger42.Game.UI.ItemViews.TopBreadView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.DragAreaSetItemAbove;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

public class MillCounterView extends CounterView {

    private enum state {
        CLEAN, DIRTY, EMPTY
    }

    private state lastCurrentState;
    private PlateView newPlate;
    private state currentState;

    public MillCounterView(Context context) {
        super(context);
    }

    public MillCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void beforeInit(Context context, @Nullable AttributeSet attrs) {
        this.currentState = state.DIRTY;
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        spongeView = new SpongeView(getContext());
        addOnTouchAreaListener(new OnTouchAreaListener(0.3f, 0.16f, 0.027f, 0.55f) {

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
                        setCurrentState(lastCurrentState);
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
                        setCurrentState(lastCurrentState);
                        newPlate.removeFromParent();
                    }
                    newPlate = null;
                }
                return false;
            }
        }.setUseFilter(true).addFilterTagR("Plate"));

        //Put plate in mill and clean Table with sponge
        addOnDragAreaListener(new OnDragAreaListener(0.3f, 0.16f, 0.027f, 0.55f) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (event.getLocalState() instanceof PlateView) {
                    if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                        PlateView plateView = (PlateView) event.getLocalState();
                        if (currentState == state.EMPTY && plateView.hasNoItemAbove()) {
                            plateView.removeFromParent();
                            switch (plateView.getCurrentState()) {
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
        }.setUseFilter(true).addFilterTagR("Plate").addFilterTagR("Sponge"));

        //Put plate an sides
        addOnDragAreaListener(new OnDragAreaListener(0.26f, 0.19f, 0.59f, 0.95f) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    PlateView itemView = (PlateView) event.getLocalState();
                    if (itemView.onlyPlateAbove()) {
                        itemView.removeFromParent();
                        restaurantFragment.addItem(itemView);
                        itemView.setTranslationY(getCustomHeight() * 0.81f);
                        itemView.setTranslationX(getX() + getCustomWidth() * 0.52f);
                        return true;
                    }
                }
                return false;
            }
        }.setUseFilter(true).addFilterTagR("Plate"));
    }

    public void setCurrentState(state currentState) {
        lastCurrentState = this.currentState;
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

    SpongeView spongeView;

    @Override
    protected void afterDraw() {
        spongeView.setTranslationX(getX() + getCustomWidth() * 0.4f);
        spongeView.setTranslationY(getCustomHeight() * 0.73f);
    }

    @Override
    public void bindOnRestaurant() {
        restaurantFragment.addItem(spongeView);
    }
}
