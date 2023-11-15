package com.example.burger42.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.burger42.Fragments.ParentFragment;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class LevelSelectionFragment extends ParentFragment {

    View view;

    public LevelSelectionFragment(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_level_selection, container, false);
        //createAndDisplayLevelItems();
        Button backButton = (Button) view.findViewById(R.id.levelSelection_BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(new StartFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });
        //git Test


        return view;
    }
}