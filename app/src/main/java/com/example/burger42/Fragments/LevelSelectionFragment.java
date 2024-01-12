package com.example.burger42.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.burger42.ArrayAdapter.LevelDisplayItemAdapter;
//import com.example.burger42.Game.UI.RestaurantLevel.RestaurantLevel1;
import com.example.burger42.Game.UI.RestaurantFragmentLevel1;
import com.example.burger42.Item.LevelDisplayItem;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

/**
 * A Fragment to represent the Level Selection Menu.
 */

public class LevelSelectionFragment extends ParentFragment {

    private View view;
    private ListView listView;
    private LevelDisplayItemAdapter levelDisplayItemAdapter;

    public LevelSelectionFragment(MainActivity mainActivity) {
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
     * @return created View of the Level Selection Menu.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_level_selection, container, false);
        listView = view.findViewById(R.id.levelSelection_List);

        Button backButton = (Button) view.findViewById(R.id.levelSelection_BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(new StartFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });
        levelDisplayItemAdapter = new LevelDisplayItemAdapter(mainActivity);

        listView.setAdapter(levelDisplayItemAdapter);
        levelDisplayItemAdapter.add(new LevelDisplayItem(1, "Easy", 200, 3));
        levelDisplayItemAdapter.add(new LevelDisplayItem(2, "Medium", 300, 3));
        levelDisplayItemAdapter.add(new LevelDisplayItem(3, "Hard", 100, 3));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mainActivity.showFragment(new RestaurantFragmentLevel1(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });
        return view;
    }


}