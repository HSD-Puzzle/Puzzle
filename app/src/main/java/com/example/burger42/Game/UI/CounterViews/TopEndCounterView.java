package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.R;

public class TopEndCounterView extends CounterView {

    public TopEndCounterView(Context context) {
        super(context);
    }

    public TopEndCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {

    }

    @Override
    protected int drawableId() {
        return R.drawable.cornertopr;
    }
}
