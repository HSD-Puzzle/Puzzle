package com.example.burger42.Alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.burger42.MainActivity;
import com.example.burger42.R;

/**
 * This class receives a Broadcastmesssage and sends a notification
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Receive the Broadcastmessage and calls the Method to send a Notification
     * @param context of the Activity
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotifications(context);
    }

    /**
     * create a Channel and sends a notification with a message
     * @param context of the Activity
     */
    public void sendNotifications(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Alarm";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            //Create a notification channel
            NotificationChannel channel = new NotificationChannel("android", name, importance);
            channel.setDescription(description);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "android")
                .setSmallIcon(R.drawable.fastfood_asset)
                .setContentTitle("Neue Nachricht")
                .setContentText("Komm zurück Burger braten")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat mNotification = NotificationManagerCompat.from(context);
        mNotification.notify(1, mBuilder.build());

    }
}
