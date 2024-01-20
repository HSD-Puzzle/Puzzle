package com.example.burger42.Fragments;

import androidx.fragment.app.Fragment;

import com.example.burger42.MainActivity;

/**
 * The super fragment is the super class of all question elements used.
 * A fragment is a single page/view in the game.
 */
public class SuperFragment extends Fragment {

    /**
     * The MainActivity in which the fragment is displayed.
     */
    protected MainActivity mainActivity;

    /**
     * @param mainActivity The MainActivity in which the fragment is displayed.
     */
    public SuperFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     * onBackPressed is called by the MainActivity if this fragment is the currently displayed fragment and the back button of the device is pressed.
     */
    public void onBackPressed() {

    }
}