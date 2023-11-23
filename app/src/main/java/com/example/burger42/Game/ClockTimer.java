package com.example.burger42.Game;

import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import com.example.burger42.R;

public class ClockTimer extends CountDownTimer {
    private View view;
    private ImageView clockview;
    private int i = 0;
    private int[] images = {
            R.drawable.clock_0_sec,
            R.drawable.clock_1_sec,
            R.drawable.clock_2_sec,
            R.drawable.clock_3_sec,
            R.drawable.clock_4_sec,
            R.drawable.clock_5_sec,
            R.drawable.clock_6_sec,
            R.drawable.clock_7_sec,
            R.drawable.clock_8_sec,
            R.drawable.clock_9_sec,
            R.drawable.clock_10_sec,
            R.drawable.clock_11_sec,
    };
    public ClockTimer(long millisInFuture, long countDownIntervall){
        super(millisInFuture,countDownIntervall);
        view.findViewById(R.layout.clock_timer_item);
        clockview = view.findViewById(R.id.timer_image_view);
    }

    @Override
    public void onTick(long l) {
        if(i>11)
            i = 0;
        clockview.setImageResource(images[i++]);
    }

    @Override
    public void onFinish() {
        System.out.println("Its joever.");
    }
    public ImageView getClockview(){
        return clockview;
    }
}
