package com.example.jobscheduler1;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class ExampleJobService extends JobService {

    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: JobStared");
        doBackgroundWork(jobParameters);

        return true; // if job is short and can be executed in this scope we return false which means
        // that the work is over
    }

    private void doBackgroundWork(final JobParameters jobParameters) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++){
                    if(jobCancelled){
                        return;
                    }
                    Log.d(TAG, "run: doBackgroundWork " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "run: doBackgroundWork JobDone");
                jobFinished(jobParameters, false); // to reschedule if necessary
            }
        }).start();
    }

    @Override // when our job gets cancelled
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: JobCancelled before completion");
        jobCancelled = true;
        return true; // false if not reschedule otherwise true
    }
}
