package com.atsdev.moviecataloguedb.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.atsdev.moviecataloguedb.BuildConfig;
import com.atsdev.moviecataloguedb.R;
import com.atsdev.moviecataloguedb.details.DetailMovieActivity;
import com.atsdev.moviecataloguedb.models.MovieItem;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class ReleaseTodayReminderReceiver extends BroadcastReceiver {

    public static final int LATEST_REQUEST_CODE = 101;
    public static final String EXTRA_REMINDER = "extra_reminder";
    private static final CharSequence CHANNEL_NAME = "daily release";
    private ArrayList<MovieItem> listMovie = new ArrayList<>();
    private final String API_KEY = BuildConfig.TheMovieDBApi;
    private final String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US&page=1";

    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult result = goAsync();
        Thread thread = new Thread() {
            public void run() {
                SyncHttpClient client = new SyncHttpClient();
                client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            ArrayList<MovieItem> listMv = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject movie = jsonArray.getJSONObject(i);
                                MovieItem movieItem = new MovieItem(movie);
                                listMv.add(movieItem);
                            }
                            listMovie = listMv;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("Failure", Objects.requireNonNull(throwable.getMessage()));
                    }
                });

                ArrayList<MovieItem> items = listMovie;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                String todayDate = dateFormat.format(date);

                for (MovieItem movieItem : items) {
                    if (movieItem.getRelease().equals(todayDate)) {
                        showReleaseTodayReminderNotification(context, movieItem.getTitle());
                    }
                }
                result.finish();
            }
        };
        thread.start();
    }

    private void showReleaseTodayReminderNotification(Context context, String title) {
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        MovieItem movie = new MovieItem();
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pIntent = PendingIntent.getActivity(context, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification2 = new NotificationCompat.Builder(context,"101")
                .setContentIntent(pIntent)
                .setContentTitle(context.getString(R.string.release_today_title))
                .setContentText(title + " " + context.getString(R.string.has_release_string))
                .setSmallIcon(R.drawable.ic_live_tv_black_24dp)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setLights(Color.CYAN, 1000, 1000)
                .setVibrate(new long[]{500, 500, 500, 500, 500})
                .setSound(sound)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("100", CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.CYAN);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{500, 500, 500, 500, 500});

            notification2.setChannelId("100");

            notificationManagerCompat.createNotificationChannel(channel);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        int notificationNumber = preferences.getInt("notificationNumber", 101);

        notificationManagerCompat.notify(notificationNumber, notification2.build());
        notificationNumber++;
        editor.putInt("notificationNumber", notificationNumber);
        editor.apply();
    }
}
