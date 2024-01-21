package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.PlateView;
import com.example.burger42.Game.UI.Scaffolding.BottomCounterItemSpawnCounterView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

/**
 * The implementation of the plate stack counter view
 */
public class PlateCounterView extends BottomCounterItemSpawnCounterView {

    /**
     * the amount of plates currently on the stack
     */
    private int plateCounter = 0;

    /**
     * the PlateView of the new created plate
     */
    private PlateView newPlate = null;

    public PlateCounterView(Context context) {
        super(context);
    }

    /**
     * @param plateCount the amount of plates that are here at the beginning
     */
    public PlateCounterView(Context context, int plateCount) {
        super(context);
        this.plateCounter = plateCount;
        loadTexture();
    }

    public PlateCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * adds listeners to drag and drop pates on this and from this
     *
     * @param context the context of this app, can be used to load the correct language
     * @param attrs   the AttributeSet that is used in xml
     */
    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
        super.onInit(context, attrs);
        addOnTouchAreaListener(new OnTouchAreaListener(0.7f, 0.5f, 0.03f, 0.97f) {
            @Override
            protected boolean onTouch(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && plateCounter > 0) {
                    newPlate = new PlateView(getContext());
                    newPlate.setReferenceHeight(referenceHeight);
                    drag(newPlate);
                    plateCounter--;
                    loadTexture();
                    invalidate();
                    return true;
                }
                return false;
            }
        });

        addOnDragAreaListener(new OnDragAreaListener(0.7f, 0.5f, 0.03f, 0.97f) {
            @Override
            protected boolean onDrag(DragEvent event, boolean inArea) {
                if (inArea && event.getAction() == DragEvent.ACTION_DROP) {
                    PlateView plateView = (PlateView) event.getLocalState();
                    if (plateView.hasNoItemAbove() && plateView.currentState() == PlateView.state.CLEAN) {
                        plateView.removeFromParent();
                        plateCounter++;
                        loadTexture();
                        invalidate();
                        return true;
                    }
                }
                if (event.getAction() == DragEvent.ACTION_DRAG_ENDED && event.getLocalState() == newPlate) {
                    if (!event.getResult()) {
                        newPlate.removeFromParent();
                        plateCounter++;
                        loadTexture();
                        invalidate();
                    }
                    newPlate = null;
                }
                return false;
            }
        }.addFilterTag("Plate").setUseFilter(true));
    }

    @Override
    protected int drawableId() {
        switch (plateCounter) {
            case 0:
                return R.drawable.plateconrner0;
            case 1:
                return R.drawable.platecorner1;
            case 2:
                return R.drawable.platecorner2;
            case 3:
                return R.drawable.platecorner3;
            default:
                return R.drawable.platecorner4;
        }
    }
}
