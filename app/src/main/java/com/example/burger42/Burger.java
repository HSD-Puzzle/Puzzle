package com.example.burger42;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ResourceBundle;

public class Burger implements GameObject {
    Resources resources;
    GameView gameView;

    public Burger(Resources resources, GameView gameView) {
        this.x = x;
        this.y = y;
        this.gameView = gameView;
        this.resources = resources;
        test = BitmapFactory.decodeResource(resources, R.drawable.burger);
    }

    private Bitmap test;
    private float x, y;
    private float speed = (float) Math.random() * 100;
    private float xDirection = 1, yDirection = 1;

    @Override
    public void start() {
        test = Bitmap.createScaledBitmap(test, test.getWidth() / 10, test.getHeight() / 10, false);
    }

    @Override
    public void update(double deltaSeconds) {
        x += deltaSeconds * speed * xDirection;
        y += deltaSeconds * speed * yDirection;
        speed += deltaSeconds * 10;
        if (x > 1920 - test.getWidth()) {
            xDirection = -1;
            gameView.instantiate(new Burger(resources, gameView));
        }
        if (x < 0) {
            xDirection = 1;
        }
        if (y > 1200 - test.getHeight()) {
            yDirection = -1;
        }
        if (y < 0) {
            yDirection = 1;
        }
    }

    @Override
    public int xPosition() {
        return (int) x;
    }

    @Override
    public int yPosition() {
        return (int) y;
    }

    @Override
    public Bitmap texture() {
        return test;
    }
}