package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button logCaloriesButton, goalsButton, progressReportButton, findGymButton;
    private ImageButton logCaloriesHelpIcon, goalsHelpIcon, progressReportHelpIcon, findGymHelpIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        logCaloriesButton = findViewById(R.id.logCaloriesButton);
        goalsButton = findViewById(R.id.goalsButton);
        progressReportButton = findViewById(R.id.progressReportButton);
        findGymButton = findViewById(R.id.findGymButton);

        // Initialize help icons
        logCaloriesHelpIcon = findViewById(R.id.imageButton_log);
        goalsHelpIcon = findViewById(R.id.imageButton_goals);
        progressReportHelpIcon = findViewById(R.id.imageButton_pr);
        findGymHelpIcon = findViewById(R.id.imageButton_gym);

        // Set onClickListeners for buttons
        logCaloriesButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LogCalories.class)));
        goalsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GoalsActivity.class)));
        progressReportButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProgressReport.class)));
        findGymButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, mapEntrySelectionActivity.class)));

        // Set onClickListeners for help icons
        logCaloriesHelpIcon.setOnClickListener(v -> showHelpDialog("Log Calories", "Use this button to log your daily calorie intake."));
        goalsHelpIcon.setOnClickListener(v -> showHelpDialog("Goals", "Set and monitor your fitness goals using this button."));
        progressReportHelpIcon.setOnClickListener(v -> showHelpDialog("Progress Report", "View your fitness progress and trends."));
        findGymHelpIcon.setOnClickListener(v -> showHelpDialog("Find A Gym", "Locate nearby gyms with this feature."));
    }

    private void showHelpDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
