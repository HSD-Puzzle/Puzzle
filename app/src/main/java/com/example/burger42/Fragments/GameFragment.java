package com.example.burger42.Fragments;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Burger;
import com.example.burger42.GameObject;
import com.example.burger42.GameView;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class GameFragment extends ParentFragment {

    private GameView gameView;

    public GameFragment(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        gameView = new GameView(mainActivity);
        gameView.instantiate(new GameObject() {
            private Bitmap test = Bitmap.createBitmap(1920, 1200, Bitmap.Config.RGB_565);
            private float x;

            @Override
            public void start() {
            }

            @Override
            public void update(double deltaSeconds) {
            }

            @Override
            public int xPosition() {
                return 0;
            }

            @Override
            public int yPosition() {
                return 0;
            }

            @Override
            public Bitmap texture() {
                return test;
            }
        });
        gameView.instantiate(new Burger(getResources(), gameView));

        return gameView;
    }

    @Override
    public void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        gameView.resume();
    }
}