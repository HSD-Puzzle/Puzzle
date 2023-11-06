package com.example.burger42;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.burger42.Burger;
import com.example.burger42.GameObject;
import com.example.burger42.GameView;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);

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

        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
