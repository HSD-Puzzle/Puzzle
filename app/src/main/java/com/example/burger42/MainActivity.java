package com.example.burger42;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.burger42.Fragments.BillFragment;
import com.example.burger42.Fragments.LevelSelectionFragment;
import com.example.burger42.Fragments.ParentFragment;
import com.example.burger42.Fragments.SettingsFragment;
import com.example.burger42.Fragments.StartFragment;
import com.example.burger42.Audio.AudioController;

public class MainActivity extends AppCompatActivity {

    AudioController audioController;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        showFragment(new StartFragment(this), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        audioController = AudioController.getInstance(this);
        audioController.startMusic();
    }

    public void showFragment(Fragment fragment, int requestedOrientation) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        setRequestedOrientation(requestedOrientation);
    }

    public void closeApp() {
        MainActivity.this.finish();
        System.exit(0);
    }


}