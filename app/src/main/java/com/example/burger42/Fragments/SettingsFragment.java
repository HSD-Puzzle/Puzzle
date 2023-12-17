package com.example.burger42.Fragments;

import static com.example.burger42.R.raw.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.MainActivity;
import com.example.burger42.R;
import com.example.burger42.Audio.AudioController;
import com.example.burger42.SettingsActivity;

import android.media.MediaPlayer;
import android.widget.SeekBar;
import android.widget.Toast;


public class SettingsFragment extends ParentFragment {

    AudioController audioController;

    private SeekBar audioSeekBar;

    public SettingsFragment(MainActivity mainActivity) {
        super(mainActivity);
        audioController = AudioController.getInstance(mainActivity);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        Button backButton = (Button) view.findViewById(R.id.backToMain);
        Button stopMusicButton = (Button) view.findViewById(R.id.stopMusic);
        Button playMusicButton = (Button) view.findViewById(R.id.startMusic);
        audioSeekBar = (SeekBar) view.findViewById(R.id.audioSeekBar);

        audioSeekBar.setProgress(audioController.volume());


        playMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioController.musicIsPlaying()) {
                    Toast.makeText(getActivity(), "Music is already playing", Toast.LENGTH_SHORT).show();
                } else {
                    audioController.startMusic();
                }

            }
        });

        stopMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioController.musicIsPlaying() == true) {
                    audioController.stopMusic();

                } else {
                    Toast.makeText(getActivity(), "Music is not playing", Toast.LENGTH_SHORT).show();
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(new StartFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });

        audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
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

    @Override
    public void onBackPressed() {
        mainActivity.showFragment(new StartFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }
}
