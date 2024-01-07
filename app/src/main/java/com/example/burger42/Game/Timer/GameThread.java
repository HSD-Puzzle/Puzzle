package com.example.burger42.Game.Timer;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * GameThread is a thread, where operations happen that appear after a fix time interval.
 * the GameThread can be paused and resumed.
 * The action that should be happen will be coded inside a {@link GameTimer} interface and has to be registered here.
 */
public class GameThread implements Runnable {
    /**
     * milliseconds are the total milliseconds this GameThread is running.
     */
    private long milliseconds = 0;
    /**
     * the Thread that is currently used.
     */
    private Thread thread;
    /**
     * the activity the ui is inside.
     */
    private final Activity activity;
    /**
     * The Map contains all action to handle with the time in which it will happen.
     */
    private final TreeMap<Long, List<GameTimerContainer>> gameTimerContainers = new TreeMap<>();

    /**
     * true if started was called ones
     */
    private boolean started;

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
        if (started) {
            throw new AlreadyStartedException();
        } else {
            thread = new Thread(this);
            thread.start();
            started = true;
        }
    }

    /**
     * pauses the GameThread
     */
    public void pause() {
        if (started)
            thread.interrupt();
    }

    /**
     * resume the GameThread
     */
    public void resume() {
        if (started && !thread.isAlive()) {
            start();
        }
    }

    /**
     * The loop, that finds and triggers the GameTimer
     */
    @Override
    public void run() {
        long lastTimeMillis = System.currentTimeMillis();
        while (!Thread.interrupted()) {
            synchronized (this) {
                milliseconds += (System.currentTimeMillis() - lastTimeMillis);
                lastTimeMillis = System.currentTimeMillis();
                Queue<Map.Entry<Long, List<GameTimerContainer>>> queue = new LinkedList<>();
                //Find all GameTimer that has to trigger in this round
                for (Map.Entry<Long, List<GameTimerContainer>> entry : gameTimerContainers.entrySet()) {
                    if (entry.getKey() < milliseconds) {
                        queue.add(entry);
                    } else {
                        break;
                    }
                }
                //Tick and recalculate all GameTimer
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

    /**
     * adds a game timer to the trigger list.
     * This {@link GameTimer} will be triggered every loop for ever.
     *
     * @param gameTimer the timer to add, that will be triggered
     */
    public void addGameTimer(GameTimer gameTimer) {
        new GameTimerContainer(gameTimer, milliseconds, -1, 0).register();
    }

    /**
     * adds a game timer to the trigger list.
     * This {@link GameTimer} will be triggered in a constant interval for ever.
     *
     * @param gameTimer the timer to add, that will be triggered
     * @param interval  the interval in ms the GameTimer will be triggered
     */
    public void addGameTimer(GameTimer gameTimer, long interval) {
        new GameTimerContainer(gameTimer, milliseconds, 0, interval).register();
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
        new GameTimerContainer(gameTimer, milliseconds, duration, interval).register();
    }

    /**
     * The Container for the GameTimer managed the timer.
     */
    private class GameTimerContainer {
        /**
         * the startTime is the time in milli seconds (based on GameThread.milliseconds), where this GameTimer is started.
         */
        private final long startTime;
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
        /**
         * if the gameTimer is finished this is set to true.
         */
        private boolean finished = false;

        /**
         * @param gameTimer that will be triggered
         * @param startTime the time that has passed since the start of the GameThread
         * @param duration  the duration this GameTimer exists
         * @param interval  the length that an interval lasts.
         */
        private GameTimerContainer(GameTimer gameTimer, long startTime, long duration, long interval) {
            this.startTime = startTime;
            this.gameTimer = gameTimer;
            this.duration = duration;
            if (interval <= 0)
                interval = 0;
            this.interval = interval;
        }

        /**
         * triggers the tick or finish methode from the {@link GameTimer}
         *
         * @param gameThreadTime the time that has passed since the start of the GameThread
         */
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

        /**
         * calculates the next time when this method must be triggered and adds it to the trigger list.
         */
        private void register() {
            if (!finished) {
                if (duration >= 0 && duration < timeDone + interval) {
                    //finish time
                    timeDone = duration;
                } else {
                    //next tick time
                    timeDone += interval;
                }
                long timeToRegister = timeDone + startTime;
                if (!gameTimerContainers.containsKey(timeToRegister)) {
                    gameTimerContainers.put(timeToRegister, new LinkedList<>());
                }
                gameTimerContainers.get(timeToRegister).add(this);
            }
        }
    }
}
