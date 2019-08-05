package com.atsdev.moviecataloguedb.utils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.atsdev.moviecataloguedb.R;

import static com.atsdev.moviecataloguedb.MainActivity.cancelAlarm;
import static com.atsdev.moviecataloguedb.MainActivity.setAlarmDaily;
import static com.atsdev.moviecataloguedb.MainActivity.setAlarmRelease;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Switch dailySwitch = findViewById(R.id.switch_daily);
        Switch latestSwitch = findViewById(R.id.switch_latest);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean dailyCondition = preferences.getBoolean("dailyCondition", true);
        boolean latestCondition = preferences.getBoolean("latestCondition", true);

        dailySwitch.setOnCheckedChangeListener(null);
        if(dailyCondition){
            dailySwitch.setChecked(true);
        }else{
            dailySwitch.setChecked(false);
        }

        latestSwitch.setOnCheckedChangeListener(null);
        if(latestCondition){
            latestSwitch.setChecked(true);
        }else{
            latestSwitch.setChecked(false);
        }

        dailySwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences1.edit();
                setAlarmDaily(getApplicationContext());
                editor.putBoolean("dailyCondition", true);
                editor.apply();

            }else{
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences1.edit();
                cancelAlarm(getApplicationContext(), "daily");
                editor.putBoolean("dailyCondition", false);
                editor.apply();
            }
        });

        latestSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                SharedPreferences preferences12 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences12.edit();
                setAlarmRelease(getApplicationContext());
                editor.putBoolean("latestCondition", true);
                editor.apply();
            }else{
                SharedPreferences preferences12 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences12.edit();
                cancelAlarm(getApplicationContext(), "latest");
                editor.putBoolean("latestCondition", false);
                editor.apply();
            }
        });
    }
}
