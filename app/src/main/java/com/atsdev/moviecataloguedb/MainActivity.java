package com.atsdev.moviecataloguedb;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.atsdev.moviecataloguedb.fragments.FavoriteFragment;
import com.atsdev.moviecataloguedb.fragments.MovieFragment;
import com.atsdev.moviecataloguedb.fragments.TvShowFragment;
import com.atsdev.moviecataloguedb.notification.DailyReminderReceiver;
import com.atsdev.moviecataloguedb.notification.ReleaseTodayReminderReceiver;
import com.atsdev.moviecataloguedb.utils.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Objects;

import static com.atsdev.moviecataloguedb.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.atsdev.moviecataloguedb.notification.DailyReminderReceiver.DAILY_REQUEST_CODE;
import static com.atsdev.moviecataloguedb.notification.DailyReminderReceiver.EXTRA_TYPE;
import static com.atsdev.moviecataloguedb.notification.ReleaseTodayReminderReceiver.EXTRA_REMINDER;
import static com.atsdev.moviecataloguedb.notification.ReleaseTodayReminderReceiver.LATEST_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment fragment;

        switch (item.getItemId()) {
            case R.id.navigation_movies:

                fragment = new MovieFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                return true;
            case R.id.navigation_tvshows:

                fragment = new TvShowFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                return true;
            case R.id.navigation_favorite:

                fragment = new FavoriteFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null){
            navigation.setSelectedItemId(R.id.navigation_movies);
        }

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver dataObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, dataObserver);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        int i = preferences.getInt("launching", 1);

        if (i < 2) {
            setAlarmRelease(this);
            setAlarmDaily(this);
            i++;
            editor.putInt("launching", i);
            editor.apply();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.language_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else {
            Intent intent2 = new Intent(this, SettingActivity.class);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    static class DataObserver extends ContentObserver {
        final Context context;
        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }

    public static void setAlarmDaily(Context context){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(context, DailyReminderReceiver.class);
        intent.putExtra(EXTRA_TYPE, DAILY_REQUEST_CODE);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,intent, 0);

        long dateTime = calendar.getTimeInMillis();
        if(dateTime <= System.currentTimeMillis()){
            dateTime = dateTime + 24 * 3600 *1000;
        }

        Objects.requireNonNull(alarmManager).setWindow(AlarmManager.RTC_WAKEUP, dateTime, AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public static void setAlarmRelease(Context context){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, 8);
        calendar1.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(context, ReleaseTodayReminderReceiver.class);
        intent.putExtra(EXTRA_REMINDER, LATEST_REQUEST_CODE);
        AlarmManager alarmManager1 = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context,101,intent, 0);

        long dateTime = calendar1.getTimeInMillis();
        if(dateTime <= System.currentTimeMillis()){
            dateTime = dateTime + 24 * 3600 *1000;
        }

        Objects.requireNonNull(alarmManager1).setWindow(AlarmManager.RTC_WAKEUP, dateTime, AlarmManager.INTERVAL_DAY, pendingIntent1);

    }

    public static void cancelAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = null;
        int request = 0;

        if(type.equalsIgnoreCase("daily")){
            request = 100;
            intent = new Intent(context, DailyReminderReceiver.class);
        }
        if(type.equalsIgnoreCase("latest")){
            request = 101;
            intent = new Intent(context, ReleaseTodayReminderReceiver.class);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request, intent, 0);

        if(alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }
}
