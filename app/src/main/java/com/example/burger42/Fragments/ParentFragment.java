package com.example.burger42.Fragments;

import androidx.fragment.app.Fragment;

import com.example.burger42.MainActivity;

public class ParentFragment extends Fragment {

    protected MainActivity mainActivity;

    public ParentFragment (){}
    public ParentFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
