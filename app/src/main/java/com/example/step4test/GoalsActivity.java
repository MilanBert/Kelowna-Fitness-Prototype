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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

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
        Button btnViewCompletedGoals = findViewById(R.id.btnViewCompletedGoals);

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

        // Set up View Completed Goals button to go to GoalCompletionActivity
        btnViewCompletedGoals.setOnClickListener(v -> {
            Intent intent = new Intent(GoalsActivity.this, GoalCompletionActivity.class);
            startActivity(intent);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        layoutGoalsContainer.removeAllViews(); // Clear the existing views
        loadGoalsFromFile(); // Reload the updated list
    }



    private void loadGoalsFromFile() {
        File goalsFile = new File(getFilesDir(), "goals.txt");
        File completedGoalsFile = new File(getFilesDir(), "compgoals.txt");

        if (!goalsFile.exists()) {
            return; // No file exists, nothing to load
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(goalsFile));
             BufferedWriter completedWriter = new BufferedWriter(new FileWriter(completedGoalsFile, true))) {

            String line;
            StringBuilder updatedGoals = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                String[] goalData = line.split(";");
                if (goalData.length < 7) {
                    continue; // Skip invalid entries
                }

                String goalName = goalData[0];
                String goalDescription = goalData[1];
                String goalCategory = goalData[2];
                String goalFrequency = goalData[3];

                int progress;

                // Validate progress
                try {
                    progress = Integer.parseInt(goalData[4]);

                } catch (NumberFormatException e) {
                    progress = 0; // Default to 0 if invalid
                }
                int startValue;
                try {
                    startValue = Integer.parseInt(goalData[6]);

                } catch (NumberFormatException e) {
                   startValue = 0; // Default to 0 if invalid
                }
                int targetValue;
                try {
                    targetValue = Integer.parseInt(goalData[5]);

                } catch (NumberFormatException e) {
                    targetValue = 0; // Default to 0 if invalid
                }

                if (progress >= 100 && goalFrequency.equals("once")) {
                    // Write the completed goal to the completed goals file
                    completedWriter.write(line + "\n");
                } else {
                    // Keep non-completed goals in the main goals list
                    updatedGoals.append(line).append("\n");
                    addGoalToLayout(goalName, goalDescription, goalCategory, goalFrequency, progress, targetValue,startValue);
                }

            }

            // Overwrite the original goals file with updated goals
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(goalsFile))) {
                writer.write(updatedGoals.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void addGoalToLayout(String goalName, String goalDescription, String goalCategory, String frequency, int progress, int start, int target) {
        View goalView = getLayoutInflater().inflate(R.layout.goal_item, null);

        ProgressBar progressBar = goalView.findViewById(R.id.progressGoal);
        TextView txtGoalName = goalView.findViewById(R.id.txtGoalName);
        TextView txtGoalDescription = goalView.findViewById(R.id.txtGoalDescription);

        progressBar.setProgress((int) (((float)progress-start)/((float)target-start)*100));
        txtGoalName.setText(goalName);
        txtGoalDescription.setText(goalDescription);

        // Set OnClickListener to open GoalSelectActivity
        goalView.setOnClickListener(v -> {
            Intent intent = new Intent(GoalsActivity.this, SelectGoalActivity.class);
            intent.putExtra("goalName", goalName);
            intent.putExtra("goalDescription", goalDescription);
            intent.putExtra("goalCategory", goalCategory);
            intent.putExtra("frequency", frequency);
            intent.putExtra("goalProgress", progress);
            intent.putExtra("startValue", start);
            intent.putExtra("targetValue", target);
            startActivity(intent);
        });

        layoutGoalsContainer.addView(goalView);
    }
}
