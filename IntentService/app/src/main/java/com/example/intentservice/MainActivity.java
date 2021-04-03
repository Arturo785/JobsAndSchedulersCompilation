package com.example.intentservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    /*
    An IntentService runs in the background independently from an activity and handles all the
    incoming work on a HandlerThread, so we don't have to take care of creating our own background
    thread in order to not block the UI thread.

    Since IntentService is a subclass of Service and therefore affected by the Android Oreo background
    execution limits, the recommended approach is to use the JobIntentService instead, which uses
     JobScheduler to enqueue a job on API 26 and higher.
     */

    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.edit_text_input);
    }

    public void startService(View v) {
        String input = editTextInput.getText().toString();

        Intent serviceIntent = new Intent(this, ExampleIntentService.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);
    }
}