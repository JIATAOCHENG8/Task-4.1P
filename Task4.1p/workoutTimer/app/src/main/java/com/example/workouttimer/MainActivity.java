package com.example.workouttimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    TextView timeTextView;
    Chronometer timer;
    ImageButton startImageButton;
    ImageButton pauseImageButton;
    ImageButton stopImageButton;
    String TIME;
    SharedPreferences sharedPreferences;
    String T;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.timer);
        timeTextView = findViewById(R.id.timeTextView);
        startImageButton = findViewById(R.id.startImageButton);
        pauseImageButton = findViewById(R.id.pauseImageButton);
        stopImageButton = findViewById(R.id.stopImageButton);
        sharedPreferences = getSharedPreferences("com.example.workouttimer", MODE_PRIVATE);
        if(savedInstanceState != null)
        {
            timer.setBase(savedInstanceState.getLong(T));
            timer.start();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putLong(T,timer.getBase());
    }

    public void timerClick(View view)
    {
        switch (view.getId())
        {
            case R.id.startImageButton:
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
                break;

            case R.id.pauseImageButton:
                timer.stop();
                break;

            case R.id.stopImageButton:
                timer.stop();
                int temp0 = Integer.parseInt(timer.getText().toString().split(":")[0]);
                int temp1 =Integer.parseInt(timer.getText().toString().split(":")[1]);
                int temp=temp0*60+temp1;
                editor = sharedPreferences.edit();
                editor.putInt(TIME,temp);
                editor.apply();
                int time = sharedPreferences.getInt(TIME,0);
                timeTextView.setText("You spent: "+ time +"s " + "on push ups last time");
                break;
            default:
                throw new IllegalStateException("Unexcepted value: " + view.getId());
        }
    }

}

