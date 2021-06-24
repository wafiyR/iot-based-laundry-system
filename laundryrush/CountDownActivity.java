package com.example.laundryrush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Locale;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class CountDownActivity extends AppCompatActivity {

    public int counter = 1;
    private TextView lblcountTime;
    //CountDownTimer cTimer = null;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 15000;  // 5000 equal to 5 seconds // 1800000 equals to 30 mins
    private boolean timerRunning;

    //@TargetApi(Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        createNotificationChannel();

        Intent snoozeIntent = new Intent(CountDownActivity.this, ReminderBroadcast.class);
        //snoozeIntent.setAction(ACTION_SNOOZE);
        //snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(CountDownActivity.this, 0, snoozeIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeStart = System.currentTimeMillis();

        long timeCalculate = 1000 * 5; // 1000ms equals to 1 seconds

        // need to add snoozing and vibrating

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeStart + timeCalculate, snoozePendingIntent);

        lblcountTime = findViewById(R.id.lblcounterTime);

        startStopCountDown();
    }

    // countdown timer need to continue counting even when user exit the CountDownActivity, and not restart the countdown whenever user exit CountDownActivity

    public void startStopCountDown(){

        if(timerRunning){

            stopTimer();

        }else{

            startTimer();

        }

    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

            //millisInFuture 600000 is equal to 10 mins

            @Override
            public void onTick(long millisUntilFinished) {

                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerRunning = true;
    }

    private void stopTimer() {

        countDownTimer.cancel();
        timerRunning = false;

    }

    public void updateTimer(){

        int minutes = (int) (timeLeftInMilliseconds / 1000) / 60;
        int seconds = (int) (timeLeftInMilliseconds / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        lblcountTime.setText(timeLeftFormatted);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LaundryRushReminderChannel"; //getString(R.string.channel_name);
            String description = "Channel for Laundry Rush Reminder"; //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("counterTimer", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //public void startCountDown(){

        /*new CountDownTimer(5000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {

                //lblcountTime.setText(String.valueOf(counter));
                //counter++;

                lblcountTime.setText("Time Remaining: " + millisUntilFinished / 1000);

                //animation

            }

            @Override
            public void onFinish() {

                lblcountTime.setText("Laundry Finished!");

            }
        }.start();*/

       /* cTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                //millidInFutute 600000 is equal to 10 mins

                //lblcountTime.setText("Time Remaining: " + millisUntilFinished / 1000);


               // lblcountTime.setText(String.valueOf(counter));
               // counter++;

                String txtcounter = String.valueOf(counter);

                lblcountTime.setText(txtcounter);

                counter++;


                //lblcountTime.setText(String.valueOf(millisUntilFinished));

            }

            @Override
            public void onFinish() {

                lblcountTime.setText("Laundry Finished!");

            }
        };

        cTimer.start();*/



    //}
}