package com.example.burger42;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTestSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_select);

        Button mainButton = (Button) findViewById(R.id.testToMain);
        Button settingButton = (Button) findViewById(R.id.testToSettings);
        Button levelSelectionButton = (Button) findViewById(R.id.testToLevelSelection);
        Button gameButton = (Button) findViewById(R.id.testToGame);
        Button billButton = (Button) findViewById(R.id.testToBill);
        Button pauseButton = (Button) findViewById(R.id.testToPause);
        mainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(ActivityTestSelectActivity.this, MainActivity.class));
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(ActivityTestSelectActivity.this, MainActivity.class));
            }
        });
        levelSelectionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(ActivityTestSelectActivity.this, LevelSelectionActivity.class));
            }
        });
        gameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(ActivityTestSelectActivity.this, GameActivity.class));
            }
        });
        billButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(ActivityTestSelectActivity.this, BillActivity.class));
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(ActivityTestSelectActivity.this, PauseActivity.class));
            }
        });
    }


}