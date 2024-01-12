package com.example.burger42.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Audio.SoundController;
import com.example.burger42.MainActivity;
import com.example.burger42.R;
import com.example.burger42.Audio.AudioController;

import android.widget.SeekBar;

public class SettingsFragment extends ParentFragment {

    AudioController audioController;
    private SoundController soundController;

    private SeekBar audioSeekBar;

    /**
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button backButton = (Button) view.findViewById(R.id.settings_backButton);
        audioSeekBar = (SeekBar) view.findViewById(R.id.audioSeekBar);
        audioSeekBar.setProgress(audioController.volume());

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

        return view;
    }
}
