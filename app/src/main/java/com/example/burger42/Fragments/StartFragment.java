package com.example.burger42.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Game.UI.RestaurantLevel.RestaurantLevel1;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class StartFragment extends ParentFragment {

    public StartFragment(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        Button startButton = (Button) view.findViewById(R.id.button);
        Button settingsButton = (Button) view.findViewById(R.id.start_settings);
        Button quitButton = (Button) view.findViewById(R.id.start_quit);


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(new SettingsFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(new RestaurantLevel1(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.closeApp();
            }
        });

        return view;
    }
}
