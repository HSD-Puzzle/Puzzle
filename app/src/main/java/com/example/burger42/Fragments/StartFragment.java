package com.example.burger42.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.MainActivity;
import com.example.burger42.R;

/**
 * This fragment is the start screen of the game.
 */
public class StartFragment extends SuperFragment {

    /**
     * @param mainActivity the mainActivity, that shows this fragment
     */
    public StartFragment(MainActivity mainActivity) {
        super(mainActivity);
    }

    /**
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return the View to display this Fragment.
     */
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
                mainActivity.showFragment(new LevelSelectionFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

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

    /**
     * onBackPressed is called by the MainActivity if this fragment is the currently displayed fragment and the back button of the device is pressed.
     * The methode closes the App.
     */
    @Override
    public void onBackPressed() {
        mainActivity.closeApp();
    }
}
