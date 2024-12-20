package com.example.burger42.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.burger42.Audio.AudioValueText;
import com.example.burger42.Audio.SoundController;
import com.example.burger42.MainActivity;
import com.example.burger42.R;
import com.example.burger42.Audio.AudioController;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * manages alle the settings for the app
 */
public class SettingsFragment extends SuperFragment {

    /**
     * controller for the audio
     */
    AudioController audioController;
    /**
     * value from 0-100 for the audio volume
     */
    AudioValueText audioValueText;
    /**
     * seekbar for the audio
     */
    private SeekBar audioSeekBar;
    /**
     * context of the activity
     */
    Context context;

    /**
     * @param mainActivity MainActivity
     */
    public SettingsFragment(MainActivity mainActivity) {
        super(mainActivity);
        audioController = AudioController.getInstance(mainActivity, 0);
        audioController.startMusic();
        audioValueText = new AudioValueText(mainActivity);
    }

    /**
     * Create View
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mainActivity.showAlertDialog(view);

        Button backButton = (Button) view.findViewById(R.id.settings_backButton);
        audioSeekBar = (SeekBar) view.findViewById(R.id.audioSeekBar);
        audioSeekBar.setProgress(audioController.volume());
        TextView textView = (TextView) view.findViewById(R.id.volumeValue);
        textView.setText(""+ audioValueText.getAudioValue() + "%");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(new StartFragment(mainActivity), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });

        audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * changes the seekbar based on the progress the user set
             * @param seekBar seekbar
             * @param i value from 0-100
             * @param b ...
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioController.leftAndRightVolume(i);
                textView.setText("" + i + "%");
                audioValueText.saveData(i);
            }

            /**
             * not in use, override methode
             * @param seekBar
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            /**
             *
             * @param seekBar
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }


}
