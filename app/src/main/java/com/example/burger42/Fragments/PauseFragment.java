package com.example.burger42.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.Scaffolding.RestaurantFragment;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class PauseFragment extends ParentFragment {
    private RestaurantFragment restaurantFragment;

    public PauseFragment(MainActivity mainActivity, RestaurantFragment restaurantFragment) {
        super(mainActivity);
        this.restaurantFragment = restaurantFragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pause, container, false);
        Button backToGame = (Button) view.findViewById(R.id.pause_BackToGame);
        Button backToMenu = (Button) view.findViewById(R.id.pause_BackToMainMenu);

        backToGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(restaurantFragment, ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                restaurantFragment.resume();
            }
        });
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(new StartFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

            }
        });
        return view;
    }
}
