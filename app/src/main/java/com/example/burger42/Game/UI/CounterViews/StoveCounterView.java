package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.PlateView;
import com.example.burger42.Game.UI.ItemViews.SpongeView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnDragAreaListener;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

public class StoveCounterView extends CounterView {

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
