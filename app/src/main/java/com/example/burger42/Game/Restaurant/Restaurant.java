package com.example.burger42.Game.Restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.burger42.R;

public class Restaurant {
    private final View view;

    public Restaurant(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_restaurant, null);
        ((LinearLayout) view.findViewById(R.id.restaurant_cornerContainer)).addView(new BreadCounterPart(context));
        ((LinearLayout) view.findViewById(R.id.restaurant_cornerContainer)).addView(new MillCounterPart(context));

    }

    public View view() {
        return view;
    }

}
