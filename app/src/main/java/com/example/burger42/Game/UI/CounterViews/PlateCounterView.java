package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.PlateView;
import com.example.burger42.Game.UI.ItemViews.SaladView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

public class PlateCounterView extends CounterView {

    private int plateCounter = 0;

    private PlateView newPlate = null;

    public PlateCounterView(Context context) {
        super(context);
    }

    public PlateCounterView(Context context, int plateCounter) {
        super(context);
        this.plateCounter = plateCounter;
        loadTexture();
    }

    public PlateCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

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
                    if (plateView.hasNoItemAbove() && plateView.getCurrentState() == PlateView.state.CLEAN) {
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
