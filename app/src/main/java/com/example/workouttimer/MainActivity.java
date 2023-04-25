package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText numSetsText = (EditText) findViewById(R.id.editTextNumber);
        EditText lenSetsText = (EditText) findViewById(R.id.editTextTime2);
        EditText lenRestText = (EditText) findViewById(R.id.editTextNumber2);
        Button startButton = (Button) findViewById(R.id.button2);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sends the workout data to the SharedData class and opens the next activity upon click
                String numSets = numSetsText.getText().toString();
                SharedData.setSetNumber(Integer.parseInt(numSets));
                String lenSets = lenSetsText.getText().toString();
                SharedData.setSetLength(Integer.parseInt(lenSets));
                String lenRest = lenRestText.getText().toString();
                SharedData.setRestLength(Integer.parseInt(lenRest));

                startActivity(new Intent(MainActivity.this, TimerScreen.class));
                MainActivity.this.finish();
            }

        });

    }
}