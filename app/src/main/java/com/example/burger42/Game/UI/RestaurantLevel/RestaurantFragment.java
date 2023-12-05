package com.example.burger42.Game.UI.RestaurantLevel;

import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Fragments.ParentFragment;
import com.example.burger42.Game.UI.Counter.CounterView;
import com.example.burger42.Game.UI.ItemViews.ItemView;
import com.example.burger42.Game.UI.ItemViews.TabletView;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

public abstract class RestaurantFragment extends ParentFragment {

    private List<ItemView> items = new LinkedList<>();

    private int itemSize;

    private View view;

    private FrameLayout itemRoot;
    private LinearLayout bottomCounterContainer;
    private LinearLayout topCounterContainer;

    public RestaurantFragment(MainActivity mainActivity) {
        super(mainActivity);
    }

    public void addItem(ItemView item) {
        if (!items.contains(item)) {
            items.add(item);
            if (itemRoot != null) {
                addItemViewToRoot(item);
            }
        }
    }

    public void removeItem(ItemView item) {
        items.remove(item);
        if (itemRoot != null) {
            removeItemViewToRoot(item);
        }
    }

    private void addItemViewToRoot(ItemView item) {
        item.setLayoutParams(new FrameLayout.LayoutParams(itemSize, itemSize));
        itemRoot.addView(item);
    }

    private void removeItemViewToRoot(ItemView item) {
        itemRoot.removeView(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemSize = container.getHeight() / 5;
        view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        bottomCounterContainer = ((LinearLayout) view.findViewById(R.id.restaurant_bottomCounterContainer));
        topCounterContainer = ((LinearLayout) view.findViewById(R.id.restaurant_topCounterContainer));
        itemRoot = ((FrameLayout) view.findViewById(R.id.restaurant_root));
        itemRoot.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                ItemView itemView = (ItemView) dragEvent.getLocalState();
                if (dragEvent.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    itemView.setVisibility(View.INVISIBLE);
                } else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    itemView.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        for (ItemView x : items) {
            addItemViewToRoot(x);
        }

        for (CounterView x : bottomCounterParts(view.getContext())) {
            bottomCounterContainer.addView(x);
        }

        for (CounterView x : topCounterParts(view.getContext())) {
            topCounterContainer.addView(x);
        }
        return view;
    }

    public int itemSize() {
        return itemSize;
    }

    public abstract CounterView[] bottomCounterParts(Context context);

    public abstract CounterView[] topCounterParts(Context context);
}
