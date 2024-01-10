package com.example.burger42.Fragments;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.burger42.Alarm.AlarmReceiver;
import com.example.burger42.Audio.SoundController;
import com.example.burger42.MainActivity;
import com.example.burger42.R;
import com.example.burger42.Audio.AudioController;
import android.widget.SeekBar;
import android.widget.Toast;

public class SettingsFragment extends ParentFragment{

    AudioController audioController;
    private SoundController soundController;

    private SeekBar audioSeekBar;

    /**
     *
     * @param mainActivity MainActivity
     */
    public SettingsFragment(MainActivity mainActivity) {
        super(mainActivity);
        audioController = AudioController.getInstance(mainActivity, 0);
        audioController.startMusic();
        soundController = new SoundController(mainActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        Button backButton = (Button) view.findViewById(R.id.backToMain);
        audioSeekBar = (SeekBar) view.findViewById(R.id.audioSeekBar);
        audioSeekBar.setProgress(audioController.volume());
        Button playSound_0 = (Button) view.findViewById(R.id.playSound_0);
        Button playSound_1 = (Button) view.findViewById(R.id.playSound_1);
        Button sendNotification = (Button) view.findViewById(R.id.notification);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(new StartFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });

        audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             *
             * @param seekBar seekbar
             * @param i value from 0-100
             * @param b ...
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println("onProgressChanged");
                audioController.leftAndRightVolume(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playSound_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundController.playSound_0();
            }
        });

        playSound_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //soundController.loadSound_0(mainActivity);
                soundController.playSound_1();
            }
        });

        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.alarmInTenSeconds();
            }
        });

        return view;
    }
}
