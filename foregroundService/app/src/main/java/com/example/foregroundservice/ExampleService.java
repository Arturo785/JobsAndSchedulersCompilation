package com.example.foregroundservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.foregroundservice.App.CHANNEL_ID;

public class ExampleService extends Service {
    public static final String TAG = "ExampleService";

    // gets called the first time the service is created
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: called");
        return null;
    }

    // every time we call start service
    // this kind of service does all on the UI thread
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: called");
        String input = intent.getStringExtra("inputExtra");
        final Integer CANCEL_CODE = 0;

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                CANCEL_CODE,
                notificationIntent,
                0
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.clock)
                .setContentIntent(pendingIntent)
                .build();

        // the service starts the notification for us
        // the code passed has to be bigger than 0
        // is the id for the notification if we want to change it

       // stopSelf(); when we want to stop it from code
        // always do heavy work on backGround thread
        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }
}
