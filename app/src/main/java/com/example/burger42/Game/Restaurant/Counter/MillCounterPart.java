package com.example.burger42.Game.Restaurant.Counter;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.burger42.Game.Restaurant.Restaurant;
import com.example.burger42.R;

public class MillCounterPart extends CounterPartView {

    public MillCounterPart(Context context, Restaurant restaurant) {
        super(context, restaurant);
    }

    @Override
    protected int drawableId() {
        return R.drawable.millcleancorner;
    }
}
