package com.example.burger42.Game.Timer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class ClockTimer extends CountDownTimer {
    private Context context;
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
    public ClockTimer(long millisInFuture, long countDownIntervall, Context context){
        super(millisInFuture,countDownIntervall);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.clock_timer_item,null);
        clockview = view.findViewById(R.id.timer_image_view);
    }

    @Override
    public void onTick(long l) {
        if(i>11)
            i = 0;
        clockview.setImageResource(images[i++]);
        System.out.println("lol");
    }

    @Override
    public void onFinish() {
        System.out.println("Its joever.");
    }
    public View view(){
        return view;
    }
}
