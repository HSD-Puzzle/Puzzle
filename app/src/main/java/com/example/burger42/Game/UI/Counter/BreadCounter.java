package com.example.burger42.Game.UI.Counter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.BottomBreadView;
import com.example.burger42.Game.UI.ItemViews.ItemView;
import com.example.burger42.Game.UI.ItemViews.TopBreadView;
import com.example.burger42.Game.UI.RestaurantLevel.RestaurantFragment;
import com.example.burger42.R;

import java.util.List;

public class BreadCounter extends CounterView {
    private List<TouchArea> touchAreas;


    public BreadCounter(Context context, RestaurantFragment restaurantFragment) {
        super(context, restaurantFragment);
    }

    public BreadCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int drawableId() {
        return R.drawable.burgerbreadcorner;
    }

    @Override
    public List<TouchArea> touchAreas() {
        if (touchAreas == null) {
            touchAreas = super.touchAreas();
            touchAreas.add(new TouchArea(0.63f, 0.93f, 0.03f, 0.49f) {
                @Override
                protected boolean onTouchEvent(MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ItemView itemView = new BottomBreadView(getContext(), restaurantFragment);
                        drag(itemView);
                        return true;
                    }
                    return false;
                }
            });

            touchAreas.add(new TouchArea(0.63f, 0.93f, 0.51f, 0.96f) {
                @Override
                protected boolean onTouchEvent(MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ItemView itemView = new TopBreadView(getContext(), restaurantFragment);
                        drag(itemView);
                        return true;
                    }
                    return false;
                }
            });

        }
        return touchAreas;
    }
}
