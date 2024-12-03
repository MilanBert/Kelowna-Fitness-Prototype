package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set up Add New button to go to CreateGoalActivity
        btnAddNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalsActivity.this, CreateGoalsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadGoalsFromFile() {
        File goalsFile = new File(getFilesDir(), "goals.txt");
        if (!goalsFile.exists()) {
            return; // No file exists, nothing to load
        }

        ArrayList<String[]> goalsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(goalsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] goalData = line.split(";"); // Assuming goals are stored as "name;description;progress"
                goalsList.add(goalData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Display each goal
        for (String[] goal : goalsList) {
            if (goal.length < 3) continue;

            String goalName = goal[0];
            String goalDescription = goal[1];
            int goalProgress = Integer.parseInt(goal[2]);

            addGoalToLayout(goalName, goalDescription, goalProgress);
        }
    }

    private void addGoalToLayout(String goalName, String goalDescription, int progress) {
        View goalView = getLayoutInflater().inflate(R.layout.goal_item, null);

        ProgressBar progressBar = goalView.findViewById(R.id.progressGoal);
        TextView txtGoalName = goalView.findViewById(R.id.txtGoalName);
        TextView txtGoalDescription = goalView.findViewById(R.id.txtGoalDescription);

        progressBar.setProgress(progress);
        txtGoalName.setText(goalName);
        txtGoalDescription.setText(goalDescription);

        layoutGoalsContainer.addView(goalView);
    }
}
