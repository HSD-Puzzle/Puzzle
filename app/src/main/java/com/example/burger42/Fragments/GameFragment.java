package com.example.burger42.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Game.Restaurant.Restaurant;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class GameFragment extends ParentFragment {
    private FrameLayout root;

    public GameFragment(MainActivity mainActivity) {
        super(mainActivity);
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        root = view.findViewById(R.id.game_root);
        Restaurant restaurant = new Restaurant(mainActivity);
        root.addView(restaurant.view());

        /*
        View view1 = view.findViewById(R.id.game_move);
        View view12 = view.findViewById(R.id.game_move2);
        View view2 = view.findViewById(R.id.game_aim);
        View view22 = view.findViewById(R.id.game_aim2);
        OnTouchListenerDragable onTouchListenerDragable = new OnTouchListenerDragable(view1);
        onTouchListenerDragable.dragFilter().addFilterTag("1");
        OnTouchListenerDragable onTouchListenerDragable2 = new OnTouchListenerDragable(view12);
        onTouchListenerDragable2.dragFilter().addFilterTag("2");
        view1.setOnTouchListener(onTouchListenerDragable);
        view12.setOnTouchListener(onTouchListenerDragable2);

        OnDragListenerAim onDragListenerAim = new OnDragListenerAim(0.5f, 0.5f, true);
        onDragListenerAim.dragFilter().addFilterTag("1");
        onDragListenerAim.dragFilter().addFilterTag("2");

        OnDragListenerAim onDragListenerAim1 = new OnDragListenerAim();
        onDragListenerAim1.dragFilter().addFilterTag("1");

        OnDragListenerAim onDragListenerAim2 = new OnDragListenerAim();
        onDragListenerAim2.dragFilter().addFilterTag("2");

        view2.setOnDragListener(onDragListenerAim);
        root.setOnDragListener(onDragListenerAim1);
        view22.setOnDragListener(onDragListenerAim2);*/

        return view;
    }
}
