package com.atsdev.moviecataloguedb.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.atsdev.moviecataloguedb.MainActivity;
import com.atsdev.moviecataloguedb.R;

public class DailyReminderReceiver extends BroadcastReceiver {

    public static final int DAILY_REQUEST_CODE = 100;
    public static final String EXTRA_TYPE = "extra_type";
    private static final CharSequence CHANNEL_NAME = "daily alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        setDailyReminderAlarm(context);
    }

    private void setDailyReminderAlarm(Context context){
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context, 100, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "100")
                .setContentIntent(pIntent)
                .setContentTitle(context.getString(R.string.daily_reminder_notif_title))
                .setContentText(context.getString(R.string.daily_reminder_notif_text))
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setLights(Color.GREEN, 500, 500)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(sound)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("100", CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.CYAN);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{500, 500, 500, 500, 500});

            notification.setChannelId("100");

            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(100, notification.build());
    }
}
