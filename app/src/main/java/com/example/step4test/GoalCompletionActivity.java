package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GoalCompletionActivity extends AppCompatActivity {

    private LinearLayout layoutCompletedGoalsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_completion);

        // Initialize the layout container
        LinearLayout layoutCompletedGoalsContainer = findViewById(R.id.layoutCompletedGoalsContainer);

        // Load completed goals and display them in the container
        loadCompletedGoalsFromFile(layoutCompletedGoalsContainer);

        // Set up Back button functionality
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void loadCompletedGoalsFromFile(LinearLayout layoutContainer) {
        File completedGoalsFile = new File(getFilesDir(), "compgoals.txt");

        if (!completedGoalsFile.exists()) {
            return; // No completed goals
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(completedGoalsFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] goalData = line.split(";");
                if (goalData.length < 7) continue; // Skip invalid entries

                String goalName = goalData[0];
                String goalDescription = goalData[1];
                String goalCategory = goalData[2];
                int progress = Integer.parseInt(goalData[4]);
                int target = Integer.parseInt(goalData[5]);

                addCompletedGoalToLayout(goalName, goalDescription, progress, target, layoutContainer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCompletedGoalToLayout(String goalName, String goalDescription, int progress, int target, LinearLayout layoutContainer) {
        View goalView = getLayoutInflater().inflate(R.layout.goal_item, null);

        TextView txtGoalName = goalView.findViewById(R.id.txtGoalName);
        TextView txtGoalDescription = goalView.findViewById(R.id.txtGoalDescription);
        ProgressBar progressBar = goalView.findViewById(R.id.progressGoal);

        // Calculate percentage progress
        int percentageProgress = (int) (((float) progress / (float) target) * 100);
        txtGoalName.setText(goalName);
        txtGoalDescription.setText(goalDescription);
        progressBar.setProgress(percentageProgress);

        // Set OnClickListener to open SelectGoalActivity
        goalView.setOnClickListener(v -> {
            Intent intent = new Intent(GoalCompletionActivity.this, SelectGoalActivity.class);
            intent.putExtra("goalName", goalName);
            intent.putExtra("goalDescription", goalDescription);
            intent.putExtra("goalProgress", progress);
            startActivity(intent);
        });

        layoutContainer.addView(goalView);
    }


}
