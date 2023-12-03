package com.example.burger42.Game.Restaurant;

import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.burger42.Game.Restaurant.Counter.CounterPartView;
import com.example.burger42.Game.Restaurant.Item.ItemView;
import com.example.burger42.R;

public abstract class Restaurant {
    private final View root;
    private final LinearLayout bottomCounterContainer;
    private final LinearLayout topCounterContainer;


    public Restaurant(Context context) {
        root = LayoutInflater.from(context).inflate(R.layout.fragment_restaurant, null);
        bottomCounterContainer = ((LinearLayout) root.findViewById(R.id.restaurant_bottomCounterContainer));
        topCounterContainer = ((LinearLayout) root.findViewById(R.id.restaurant_topCounterContainer));

        root.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    View onTouchListenerDragable = (View) dragEvent.getLocalState();
                    onTouchListenerDragable.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });

        for (CounterPartView x : bottomCounterParts(root.getContext())) {
            bottomCounterContainer.addView(x);
        }

        for (CounterPartView x : topCounterParts(root.getContext())) {
            topCounterContainer.addView(x);
        }
    }

    public abstract CounterPartView[] bottomCounterParts(Context context);

    public abstract CounterPartView[] topCounterParts(Context context);

    public View view() {
        return root;
    }

    public float height() {
        return bottomCounterContainer.getHeight();
    }
}
