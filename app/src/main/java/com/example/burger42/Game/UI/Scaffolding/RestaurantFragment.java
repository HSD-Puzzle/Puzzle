package com.example.burger42.Game.UI.Scaffolding;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Fragments.ParentFragment;
import com.example.burger42.Game.UI.ItemViews.PlateView;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

import java.util.LinkedList;
import java.util.List;

public abstract class RestaurantFragment extends ParentFragment {

    private List<ItemView> items = new LinkedList<>();
    private View rootView;
    FrameLayout itemRoot;
    private LinearLayout bottomCounterContainer;
    private LinearLayout topCounterContainer;
    int referenceHeight = 100;

    public RestaurantFragment(MainActivity mainActivity) {
        super(mainActivity);
        ItemView itemView = new PlateView(mainActivity);
        itemView.setTranslationY(300);
        addItem(itemView);
    }

    public void addItem(ItemView item) {
        if (!items.contains(item)) {
            items.add(item);
            item.setRestaurantFragment(this);
            if (itemRoot != null) {
                addItemViewToRoot(item);
            }
        }
    }

    public void removeItem(ItemView item) {
        if (items.remove(item) && itemRoot != null) {
            removeItemViewToRoot(item);
        }
    }

    private void addItemViewToRoot(ItemView item) {
        item.setLayoutParams(new FrameLayout.LayoutParams(referenceHeight, referenceHeight));
        itemRoot.addView(item);
    }

    private void removeItemViewToRoot(ItemView item) {
        itemRoot.removeView(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_restaurant, container, false);
        bottomCounterContainer = ((LinearLayout) rootView.findViewById(R.id.restaurant_bottomCounterContainer));
        topCounterContainer = ((LinearLayout) rootView.findViewById(R.id.restaurant_topCounterContainer));
        itemRoot = ((FrameLayout) rootView.findViewById(R.id.restaurant_root));
        referenceHeight = container.getHeight() / 4;

        itemRoot.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                ItemView itemView = (ItemView) dragEvent.getLocalState();
                if (dragEvent.getAction() == DragEvent.ACTION_DRAG_STARTED) {
                    itemView.setVisibility(View.INVISIBLE);
                    return true;
                } else if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    itemView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        for (ItemView x : items) {
            addItemViewToRoot(x);
        }
        for (CounterView x : bottomCounter()) {
            addBottomCounter(x);
        }
        for (CounterView x : topCounter()) {
            addTopCounter(x);
        }
        return rootView;
    }

    protected abstract CounterView[] bottomCounter();

    protected abstract CounterView[] topCounter();


    private void addBottomCounter(CounterView bottomCounter) {
        bottomCounter.setRestaurantFragment(this);
        bottomCounterContainer.addView(bottomCounter);
        bottomCounter.setLayoutParams(new LinearLayout.LayoutParams(-2, referenceHeight));
    }

    private void addTopCounter(CounterView topCounter) {
        topCounter.setRestaurantFragment(this);
        topCounterContainer.addView(topCounter);
        topCounter.setLayoutParams(new LinearLayout.LayoutParams(-2, referenceHeight));
    }
}
