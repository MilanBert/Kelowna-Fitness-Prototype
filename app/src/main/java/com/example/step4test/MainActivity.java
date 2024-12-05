package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button logCaloriesButton, goalsButton, progressReportButton, findGymButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        logCaloriesButton = findViewById(R.id.logCaloriesButton);
        goalsButton = findViewById(R.id.goalsButton);
        progressReportButton = findViewById(R.id.progressReportButton);
        findGymButton = findViewById(R.id.findGymButton);

        // Set onClickListeners for each button

        // Log Calories Button
        logCaloriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogCalories.class);
                startActivity(intent);
            }
        });

        // Goals Button
        goalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoalsActivity.class);
                startActivity(intent);
            }
        });

        // Progress Report Button
        progressReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the ProgressReport activity using an Intent
                Intent intent = new Intent(MainActivity.this, ProgressReport.class);
                startActivity(intent); // This starts ProgressReport as a new Activity
            }
        });



        // Find A Gym Button
        findGymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add functionality here later
            }
        });
    }
}
