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

/**
 * this fragment, will be shown while the level is paused.
 */
public class PauseFragment extends SuperFragment {
    /**
     * The restaurantFragment, of the Level that is paused and can be resumed by clicking on the back to restaurant button.
     */
    private final RestaurantFragment restaurantFragment;

    /**
     * @param mainActivity       the mainActivity, that shows this fragment
     * @param restaurantFragment The restaurantFragment, of the Level that is paused and can be resumed by clicking on the back to restaurant button.
     */
    public PauseFragment(MainActivity mainActivity, RestaurantFragment restaurantFragment) {
        super(mainActivity);
        this.restaurantFragment = restaurantFragment;

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
