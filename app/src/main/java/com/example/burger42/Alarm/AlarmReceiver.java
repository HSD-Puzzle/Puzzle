package com.example.burger42.Alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.burger42.Fragments.ParentFragment;
import com.example.burger42.MainActivity;
import com.example.burger42.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("onReceive");
        sendNotifications(context);
    }

    public void sendNotifications(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Alarm";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("android", name, importance);
            channel.setDescription(description);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        System.out.println("Send Notification");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "android")
                .setSmallIcon(R.drawable.fastfood_asset)
                .setContentTitle("Neue Nachricht")
                .setContentText("Komm zurück Burger braten")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat mNotification = NotificationManagerCompat.from(context);
        mNotification.notify(1, mBuilder.build());
        System.out.println("Notification gesendet");

    }
}
