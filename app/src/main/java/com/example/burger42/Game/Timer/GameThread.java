package com.example.burger42.Game.Timer;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * GameThread is a thread, where operations happen that appear after a fix time interval.
 * the GameThread can be paused and resumed.
 * The action that should be happen will be coded inside a {@link GameTimer} interface and has to be registered here.
 */
public class GameThread {
    private Activity activity;
    /**
     * The List GameTimerContainer.
     */
    private final List<GameTimerContainer> gameTimerContainers = new LinkedList<>();

    /**
     * true if started was called ones
     */
    private boolean running;

    /**
     * @param activity the activity the ui is inside.
     */
    public GameThread(Activity activity) {
        this.activity = activity;
    }

    /**
     * start starts the GameThread.
     * if the GameThread was already started a AlreadyStartedException will be thrown
     */
    public void start() {
        if (!running)
            for (GameTimerContainer x : gameTimerContainers) {
                x.start();
            }
        running = true;
    }

    /**
     * pauses the GameThread
     */
    public void pause() {
        if (running)
            for (GameTimerContainer x : gameTimerContainers) {
                x.pause();
            }
        running = false;
    }

    /**
     * adds a game timer to the trigger list.
     * This {@link GameTimer} will be triggered every loop for ever.
     *
     * @param gameTimer the timer to add, that will be triggered
     */
    public void addGameTimer(GameTimer gameTimer) {
        GameTimerContainer gameTimerContainer = new GameTimerContainer(gameTimer, -1, -1);
        gameTimerContainers.add(gameTimerContainer);
        if (running)
            gameTimerContainer.start();
    }

    /**
     * adds a game timer to the trigger list.
     * This {@link GameTimer} will be triggered in a constant interval for ever.
     *
     * @param gameTimer the timer to add, that will be triggered
     * @param interval  the interval in ms the GameTimer will be triggered
     */
    public void addGameTimer(GameTimer gameTimer, long interval) {
        GameTimerContainer gameTimerContainer = new GameTimerContainer(gameTimer, -1, interval);
        gameTimerContainers.add(gameTimerContainer);
        if (running)
            gameTimerContainer.start();
    }

    /**
     * adds a game timer to the trigger list.
     *
     * @param gameTimer the timer to add, that will be triggered
     * @param duration  the duration in ms the GameTimer is active. At the end the finish methode will be triggered.
     * @param interval  the interval in ms the GameTimer will be triggered.
     *                  If the interval is bigger than the duration the tick methode will never called.
     */
    public void addGameTimer(GameTimer gameTimer, long duration, long interval) {
        GameTimerContainer gameTimerContainer = new GameTimerContainer(gameTimer, duration, interval);
        gameTimerContainers.add(gameTimerContainer);
        if (running)
            gameTimerContainer.start();
    }

    /**
     * The Container for the GameTimer managed the timer.
     */
    private class GameTimerContainer implements Runnable {
        /**
         * the startTime is the time in milli seconds (based on GameThread.milliseconds), where this GameTimer is started.
         */
        private long startTime;
        /**
         * the amount of time this GameTimer has done
         */
        private long timeDone = 0;
        /**
         * the duration this GameTimer exists
         */
        private final long duration;
        /**
         * the length that an interval lasts.
         */
        private final long interval;
        /**
         * the {@link GameTimer}, that will be triggered
         */
        private final GameTimer gameTimer;

        private Thread thread;

        private long pausedAt;

        /**
         * @param gameTimer that will be triggered
         * @param duration  the duration this GameTimer exists
         * @param interval  the length that an interval lasts.
         */
        private GameTimerContainer(GameTimer gameTimer, long duration, long interval) {
            this.startTime = System.currentTimeMillis();
            this.pausedAt = startTime;
            this.gameTimer = gameTimer;
            this.duration = duration;
            this.interval = interval;
        }

        private void start() {
            startTime += System.currentTimeMillis() - pausedAt;
            thread = new Thread(this);
            thread.start();
        }

        private void pause() {
            pausedAt = System.currentTimeMillis();
            thread.interrupt();
        }

        @Override
        public void run() {
            boolean finished = false;
            while (!Thread.interrupted() && !finished) {
                if (interval < 0) {
                    timeDone = duration;
                } else {
                    timeDone += interval;
                    if (!(duration < 0) && timeDone > duration) {
                        timeDone = duration;
                    }
                }
                long nextTimeInterval = startTime + timeDone - System.currentTimeMillis();
                try {
                    if (nextTimeInterval > 0)
                        Thread.sleep(nextTimeInterval);
                } catch (InterruptedException e) {
                    break;
                }
                if (duration > 0 && timeDone >= duration) {
                    finished = true;
                    gameTimerContainers.remove(this);
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
                            gameTimer.tick(System.currentTimeMillis() - startTime);
                        }
                    });
                }
            }
        }
    }
}
