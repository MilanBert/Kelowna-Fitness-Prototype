package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GoalsActivity extends AppCompatActivity {

    private Button btnBack, btnAddNewGoal;
    private LinearLayout layoutGoalsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        // Initialize views
        btnBack = findViewById(R.id.btnBack);
        btnAddNewGoal = findViewById(R.id.btnAddNewGoal);
        layoutGoalsContainer = findViewById(R.id.layoutGoalsContainer);

        // Load goals from file and display them
        loadGoalsFromFile();

        // Set up Back button to go to the Home Activity
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(GoalsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Set up Add New button to go to CreateGoalActivity
        btnAddNewGoal.setOnClickListener(v -> {
            Intent intent = new Intent(GoalsActivity.this, CreateGoalsActivity.class);
            startActivity(intent);
        });
    }

    private void loadGoalsFromFile() {
        File goalsFile = new File(getFilesDir(), "goals.txt");
        if (!goalsFile.exists()) {
            return; // No file exists, nothing to load
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(goalsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] goalData = line.split(";");
                if (goalData.length < 4) {
                    continue; // Skip invalid entries
                }

                String goalName = goalData[0];
                String goalDescription = goalData[1];
                String goalCategory = goalData[2];
                int progress;

                // Validate progress
                try {
                    progress = Integer.parseInt(goalData[3]);
                    progress = Math.max(0, Math.min(progress, 100)); // Clamp to 0-100
                } catch (NumberFormatException e) {
                    progress = 0; // Default to 0 if invalid
                }

                addGoalToLayout(goalName, goalDescription, goalCategory, progress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addGoalToLayout(String goalName, String goalDescription, String goalCategory, int progress) {
        View goalView = getLayoutInflater().inflate(R.layout.goal_item, null);

        ProgressBar progressBar = goalView.findViewById(R.id.progressGoal);
        TextView txtGoalName = goalView.findViewById(R.id.txtGoalName);
        TextView txtGoalDescription = goalView.findViewById(R.id.txtGoalDescription);

        progressBar.setProgress(progress);
        txtGoalName.setText(goalName);
        txtGoalDescription.setText(goalDescription);

        // Set OnClickListener to open GoalSelectActivity
        goalView.setOnClickListener(v -> {
            Intent intent = new Intent(GoalsActivity.this, SelectGoalActivity.class);
            intent.putExtra("goalName", goalName);
            intent.putExtra("goalDescription", goalDescription);
            intent.putExtra("goalCategory", goalCategory);
            intent.putExtra("goalProgress", progress);
            startActivity(intent);
        });

        layoutGoalsContainer.addView(goalView);
    }
}
