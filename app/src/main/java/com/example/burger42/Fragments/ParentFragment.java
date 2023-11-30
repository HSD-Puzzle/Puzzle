package com.example.burger42.Fragments;

import androidx.fragment.app.Fragment;

import com.example.burger42.MainActivity;

import java.util.Set;

public class ParentFragment extends Fragment {

    protected MainActivity mainActivity;

    public ParentFragment (){}
    public ParentFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
