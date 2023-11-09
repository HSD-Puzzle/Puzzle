package com.example.burger42;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.burger42.Fragments.StartFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(new StartFragment(this), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    public void showFragment(Fragment fragment, int requestedOrientation) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        setRequestedOrientation(requestedOrientation);
    }

}