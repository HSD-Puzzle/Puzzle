package com.example.burger42;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.burger42.Alarm.AlarmReceiver;
import com.example.burger42.Fragments.BillFragment;
import com.example.burger42.Fragments.LevelSelectionFragment;
import com.example.burger42.Fragments.ParentFragment;
import com.example.burger42.Fragments.SettingsFragment;
import com.example.burger42.Fragments.StartFragment;
import com.example.burger42.Audio.AudioController;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AudioController audioController;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        showFragment(new StartFragment(this), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        audioController = AudioController.getInstance(this,0);
        audioController.startMusic();
    }

    public void showFragment(Fragment fragment, int requestedOrientation) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        setRequestedOrientation(requestedOrientation);
    }

    public void closeApp() {
        if(audioController.musicIsPlaying()){
            audioController.stopMusic();
        }
        MainActivity.this.finish();
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        if (currentlyShownFragment != null)
            currentlyShownFragment.onBackPressed();
        else
            super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (audioController != null) {
            audioController.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (audioController != null) {
            audioController.startMusic();
        }
    }

    public void alarmInTenSeconds(){
        System.out.println("alarm");
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
/**
        long time = System.currentTimeMillis();
        long triggerTime = System.currentTimeMillis() + (30 * 60 * 1000);
        System.out.println("Millisekunden:" + triggerTime);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
 */

// Setzen Sie die Alarmzeit auf 09:00 Uhr
        int alarmHour = 23;
        int alarmMinute = 8;

        // Kalenderobjekt erstellen und auf die gewünschte Uhrzeit setzen
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);

        // Wenn die gewünschte Zeit bereits vergangen ist, setzen Sie den Alarm auf den nächsten Tag
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Setzen Sie den Alarm mit RTC_WAKEUP, um das Gerät aufzuwecken, wenn es im Schlaf ist
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}