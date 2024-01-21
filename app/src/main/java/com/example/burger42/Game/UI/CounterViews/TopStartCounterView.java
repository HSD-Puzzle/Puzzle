package com.example.burger42.Game.UI.CounterViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.CounterView;
import com.example.burger42.R;

/**
 * The implementation of the start Counter View at the top
 */
public class TopStartCounterView extends CounterView {

    public TopStartCounterView(Context context) {
        super(context);
    }

    public TopStartCounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInit(Context context, @Nullable AttributeSet attrs) {

    }

    @Override
    protected float scaling() {
        if (isInEditMode()) return 1;
        return 4;
    }

    @Override
    protected int drawableId() {
        return R.drawable.cornertopl;
    }
}
