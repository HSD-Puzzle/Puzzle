package com.example.burger42;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.burger42.Alarm.AlarmReceiver;
import com.example.burger42.Fragments.SuperFragment;
import com.example.burger42.Fragments.StartFragment;
import com.example.burger42.Audio.AudioController;
import com.example.burger42.Fragments.SuperFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    /**
     *
     */
    private AudioController audioController;
    /**
     * The fragment that is currently displayed.
     */
    private SuperFragment currentlyShownFragment;

    /**
     * Method called when the activity is first created. Performs initialization operations for the activity,
     * such as setting the layout, displaying the StartFragment, initializing the AudioController,
     * and starting the background music. This method is automatically called when the activity is created.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        showFragment(new StartFragment(this), ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        audioController = AudioController.getInstance(this, 0);
        audioController.startMusic();
        dailyAlarm();
    }

    /**
     * this methode changes the fragment to show.
     *
     * @param fragment             the fragment to show
     * @param requestedOrientation the orientation of the device to show this fragment.
     */
    public void showFragment(SuperFragment fragment, int requestedOrientation) {
        currentlyShownFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        setRequestedOrientation(requestedOrientation);
    }

    /**
     * close the App and release all resources
     */
    public void closeApp() {
        if (audioController.musicIsPlaying()) {
            audioController.stopMusic();
        }
        MainActivity.this.finish();
        System.exit(0);
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        if (currentlyShownFragment != null)
            currentlyShownFragment.onBackPressed();
        else
            super.onBackPressed();
    }

    /**
     * pauses the music when tabbing out of the app
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (audioController != null) {
            audioController.onPause();
        }
    }

    /**
     * continue to play the music when opening the app again
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (audioController != null) {
            audioController.startMusic();
        }
    }

    /**
     * sets a daily alarm at a spezific time. Works when the app is running or sleeping
     */
    public void dailyAlarm() {
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int alarmHour = 14;
        int alarmMinute = 45;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    /**
     * shows a Dialag to valuate the App or not
     *
     * @param v View
     */
    public void showAlertDialog(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.app_name));
        alert.setMessage(getString(R.string.app_valuation));
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            /**
             *
             * @param dialogInterface
             * @param i
             */
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://play.google.com/store/games?device=windows&gl=DE&utm_source=emea_Med&utm_medium=hasem&utm_content=Sep2020&utm_campaign=Evergreen&pcampaignid=MKT-EDR-emea-de-1707522-Med-hasem-py-Evergreen-Sep2020-Text_Search_BKWS%7CONSEM_kwid_43700008794262693&gclid=CjwKCAiA75itBhA6EiwAkho9e6eE5S8_e9jRdwGN6PqOq9y9o-pEAxNwGTyy16VyIq2WrYAipfh2OBoCjRQQAvD_BwE&gclsrc=aw.ds"));
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            /**
             *
             * @param dialogInterface
             * @param i
             */
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Thank you for your Feedback!", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

}