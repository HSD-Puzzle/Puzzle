package com.example.burger42;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private Paint paint;

    private List<GameObject> activeGameObjects = new LinkedList<>();
    private Queue<GameObject> newGameObjects = new LinkedList<>();

    public GameView(Context context) {
        super(context);

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);
    }

    public void instantiate(GameObject gameObject) {
        newGameObjects.add(gameObject);
    }

    private long lastTimeInNanos;

    public void resume() {

        isPlaying = true;
        lastTimeInNanos = System.nanoTime();
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (isPlaying) {
            start();
            update();
            draw();
            Thread.yield();
        }
    }

    private void start() {
        while (!newGameObjects.isEmpty()) {
            GameObject x = newGameObjects.poll();
            x.start();
            activeGameObjects.add(x);
        }
    }

    private void update() {
        double deltaTime = (System.nanoTime() - lastTimeInNanos) / 1000000000.0;
        lastTimeInNanos = System.nanoTime();
        for (GameObject x : activeGameObjects) {
            x.update(deltaTime);
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            for (GameObject x : activeGameObjects) {
                canvas.drawBitmap(x.texture(), x.xPosition(), x.yPosition(), paint);
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }
}
