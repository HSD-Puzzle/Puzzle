package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.ItemViews.BottomBreadView;
import com.example.burger42.Game.UI.ItemViews.TopBreadView;
import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.Game.UI.Scaffolding.ItemView;
import com.example.burger42.Game.UI.Scaffolding.OnTouchAreaListener;
import com.example.burger42.R;

public class TopRecepieCounter extends CounterView {

    public TopRecepieCounter(Context context) {
        super(context);
    }

    public TopRecepieCounter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {
    }

    @Override
    protected int drawableId() {
        return R.drawable.topcorner;
    }
}
