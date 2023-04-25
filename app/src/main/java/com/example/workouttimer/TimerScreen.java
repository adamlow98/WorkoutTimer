package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;

public class TimerScreen extends AppCompatActivity {
    long setTimeSeconds = SharedData.getSetLength(); // pulls the times from the SharedData class
    long restTimeSeconds = SharedData.getRestLength();
    private boolean timerRunning;
    private boolean rest;
    long restTimeInMillis = 1000 * restTimeSeconds;
    long timeLeftInMillis = 1000 * setTimeSeconds; // converts the times to milliseconds
    long timeLeftStored = timeLeftInMillis;
    long countdownInterval = 1000; // 1 second in milliseconds

//    declares the ui elements
    CountDownTimer timer;
    TextView timerText;
    TextView setText;
    TextView restText;
    ImageButton startButton;
    Button finishButton;

    int currentSet = 1;
    int setsInt = SharedData.getSetNumber();
    String setsText = Integer.toString(setsInt);
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_screen);

        timerText = (TextView) findViewById(R.id.textView);
        setText = (TextView) findViewById(R.id.textView2);
        startButton = findViewById(R.id.imageButton);
        finishButton = findViewById(R.id.button4);
        restText = findViewById(R.id.textView7);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setMax((int) timeLeftInMillis); // sets the max for the progress bar

        setText.setText("Set " + Integer.toString(currentSet) + " of " + setsText);

        rest = false; // declares whether the timer is in a rest phase or not

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                starts the timer if its not running or pauses if it is
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sends user to the finish screen when clicked
                startActivity(new Intent(TimerScreen.this, FinishScreen.class));
                TimerScreen.this.finish();
            }
        });
    }
    private void startTimer() {
        timerRunning = true;
        startButton.setImageResource(android.R.drawable.ic_media_pause);
        startButton.setBackgroundColor(Color.YELLOW);


        final MediaPlayer mp = MediaPlayer.create(this, R.raw.bell2);

        timer = new CountDownTimer(timeLeftInMillis, countdownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText(); // updates the time text every tick
                progressBar.setProgress((int) timeLeftInMillis); // changes time to red if less than 6 seconds
                if (timeLeftInMillis < 5999){
                    timerText.setTextColor(Color.RED);
                }else{
                    timerText.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onFinish() {
                timerRunning = false;

                mp.start(); // plays a bell every time timer finishes
                if (currentSet < setsInt){
                    if(rest){
                        timeLeftInMillis = timeLeftStored; // sets the time to the timer length if rest is true
                        progressBar.setMax((int) timeLeftInMillis);
                        rest = false; // toggles the rest off
                        currentSet++;
                        restText.setVisibility(View.INVISIBLE); // hides the rest text
                        setText.setText("Set " + Integer.toString(currentSet) + " of " + setsText);
                        startTimer();
                    } else{
                        timeLeftInMillis = restTimeInMillis; // sets the countdown to rest length
                        progressBar.setMax((int) timeLeftInMillis);
                        rest = true;
                        restText.setVisibility(View.VISIBLE); // shows the rest text
                        startTimer();
                    }
                }else{
//                    sends the user to the finish screen when all sets are done
                    startActivity(new Intent(TimerScreen.this, FinishScreen.class));
                    TimerScreen.this.finish();
                }
            }
        }.start();
    }

    private void pauseTimer() {
        timer.cancel();
        timerRunning = false;

        startButton.setImageResource(android.R.drawable.ic_media_play);
        startButton.setBackgroundColor(Color.GREEN);
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(timeLeftFormatted);
    }
}

