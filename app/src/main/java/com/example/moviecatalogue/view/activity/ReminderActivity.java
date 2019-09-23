package com.example.moviecatalogue.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.alarm.AlarmReceiver;

public class ReminderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Switch daily, today;
    private String TAG_DAILY = "DAILY";
    private String TAG_TODAY = "TODAY";
    private int ON_CODE = 1, OFF_CODE = 0;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        daily = findViewById(R.id.daily);
        today = findViewById(R.id.realese);
        alarmReceiver = new AlarmReceiver();


        if (getKey(TAG_DAILY)) {
            daily.setChecked(true);
        }


        if (getKey(TAG_TODAY)) {
            today.setChecked(true);
        }
        today.setOnCheckedChangeListener(this);
        daily.setOnCheckedChangeListener(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.reminder));
    }

    private void saveKey(String key, int code) {
        SharedPreferences preferences = ((Context) ReminderActivity.this).getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, code);
        editor.commit();
    }

    private boolean getKey(String key) {
        SharedPreferences preferences = ((Context) ReminderActivity.this).getSharedPreferences(key, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0) == 1;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.daily) {
            if (b) {
                alarmReceiver.setAlarm(ReminderActivity.this, AlarmReceiver.TYPE_DAILY, "07:00", getString(R.string.daily_msg));
                saveKey(TAG_DAILY, ON_CODE);
            } else {
                saveKey(TAG_DAILY, OFF_CODE);
                alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_DAILY);
            }
        } else if (compoundButton.getId() == R.id.realese) {
            if (b) {
                saveKey(TAG_TODAY, ON_CODE);
                alarmReceiver.setAlarm(ReminderActivity.this, AlarmReceiver.TYPE_TODAY, "08:00", getString(R.string.today_msg));

            } else {
                saveKey(TAG_TODAY, OFF_CODE);
                alarmReceiver.cancelAlarm(ReminderActivity.this, AlarmReceiver.TYPE_TODAY);
            }
        }
    }
}
