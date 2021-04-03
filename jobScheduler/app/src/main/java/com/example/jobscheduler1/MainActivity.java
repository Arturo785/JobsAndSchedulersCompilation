package com.example.jobscheduler1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static int jobId = 123;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // the on clicks are attached on the xml
    }

    public void scheduleJob(View view){
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(jobId, componentName)
                .setRequiresCharging(true) // only if charging
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // only wifi
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000) // how often to execute (cant't be lower than 15 minutes) value in ms
                .build();

        // we get the scheduler
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);

        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "scheduleJob: RESULT_SUCCESS");
        }
        else{
            Log.d(TAG, "scheduleJob: RESULT_FAILURE");
        }
    }

    public void cancelJob(View view){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(jobId);
        Log.d(TAG, "cancelJob: JobCancelled");
    }
}