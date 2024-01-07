package com.example.burger42.Game;

import android.app.Activity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class GameThread implements Runnable {
    private long milliseconds = 0;
    private long lastTimeMillis = 0;
    private Thread thread;

    private Activity activity;

    public TreeMap<Long, List<GameTimerContainer>> gameTimerContainers = new TreeMap<>();

    public GameThread(Activity activity) {
        this.activity = activity;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        thread.interrupt();
    }

    public void resume() {
        if (!thread.isAlive()) {
            start();
        }
    }

    @Override
    public void run() {
        lastTimeMillis = System.currentTimeMillis();
        while (!Thread.interrupted()) {
            synchronized (this) {
                milliseconds += System.currentTimeMillis() - lastTimeMillis;
                lastTimeMillis = System.currentTimeMillis();

                Queue<Map.Entry<Long, List<GameTimerContainer>>> queue = new LinkedList<>();
                for (Map.Entry<Long, List<GameTimerContainer>> entry : gameTimerContainers.entrySet()) {
                    if (entry.getKey() < milliseconds) {
                        queue.add(entry);
                    } else {
                        break;
                    }
                }
                //Remove all ticked from map and register
                while (!queue.isEmpty()) {
                    Map.Entry<Long, List<GameTimerContainer>> entry = queue.poll();
                    gameTimerContainers.remove(entry.getKey());
                    for (GameTimerContainer x : entry.getValue()) {
                        x.tick(milliseconds);
                        x.register();
                    }
                }
            }
            Thread.yield();
        }
    }

    public void addGameTimer(GameTimer gameTimer) {
        new GameTimerContainer(gameTimer, milliseconds, -1, 0).register();
    }

    public void addGameTimer(GameTimer gameTimer, long duration) {
        new GameTimerContainer(gameTimer, milliseconds, duration, 0).register();
    }

    public void addGameTimer(GameTimer gameTimer, long duration, long countDownInterval) {
        new GameTimerContainer(gameTimer, milliseconds, duration, countDownInterval).register();
    }

    private static int idCounter = 0;

    private class GameTimerContainer {

        private int id;

        private final long startTime;

        private long timeDone = 0;

        private final long duration;
        private final long interval;
        private final GameTimer gameTimer;

        private boolean finished = false;

        private GameTimerContainer(GameTimer gameTimer, long startTime, long duration, long interval) {
            this.startTime = startTime;
            this.gameTimer = gameTimer;
            this.duration = duration;
            if (interval <= 0)
                interval = 0;
            this.interval = interval;
            id = idCounter++;
        }

        private void tick(long gameThreadTime) {
            if (duration >= 0 && milliseconds >= startTime + duration) {
                finished = true;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameTimer.finish();
                    }
                });
            } else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameTimer.tick(gameThreadTime - startTime);
                    }
                });
            }
        }

        private void register() {
            if (!finished) {
                long timeToRegister;
                if (duration >= 0 && duration < timeDone + interval) {
                    //finish
                    timeToRegister = duration + startTime;
                    timeDone = duration;
                } else {
                    timeDone += interval;
                    timeToRegister = timeDone + startTime;
                }
                if (!gameTimerContainers.containsKey(timeToRegister)) {
                    gameTimerContainers.put(timeToRegister, new LinkedList<>());
                }
                gameTimerContainers.get(timeToRegister).add(this);
            }
        }

    }
}
