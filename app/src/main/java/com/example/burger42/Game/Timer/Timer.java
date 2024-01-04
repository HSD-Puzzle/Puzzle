package com.example.burger42.Game.Timer;

import android.os.CountDownTimer;

public abstract class Timer extends CountDownTimer {
    private long lastL;
    private long interval;
    public Timer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        lastL = millisInFuture; interval = countDownInterval;
    }

    @Override
    public abstract void onTick(long l);
    public void onPause() throws InterruptedException {
        if(!Thread.currentThread().isInterrupted())
            Thread.currentThread().wait();
    }
    public void onResume(){
        if(Thread.currentThread().isInterrupted()){
            Thread.currentThread().notify();
        }
    }

    @Override
    public abstract void onFinish();
}
