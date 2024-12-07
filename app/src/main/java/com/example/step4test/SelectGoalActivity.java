package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.annotation.Target;

public class SelectGoalActivity extends AppCompatActivity {

    private TextView txtGoalName, txtGoalDescription;
    private ProgressBar progressGoal;
    private Button btnBack, btnIncrementProgress, btnResetProgress;

    private String goalName, goalDescription, goalCategory;
    private int goalProgress,start,target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goal);

        // Initialize views
        txtGoalName = findViewById(R.id.txtGoalName);
        txtGoalDescription = findViewById(R.id.txtGoalDescription);
        progressGoal = findViewById(R.id.progressGoal);
        btnBack = findViewById(R.id.btnBack);
        btnIncrementProgress = findViewById(R.id.btnIncrementProgress);
        btnResetProgress = findViewById(R.id.btnResetProgress);

        // Get data from intent
        Intent intent = getIntent();
        goalName = intent.getStringExtra("goalName");
        goalDescription = intent.getStringExtra("goalDescription");
        goalCategory = intent.getStringExtra("goalCategory");
        goalProgress = intent.getIntExtra("goalProgress", 0);
        start = intent.getIntExtra("startValue",0);
        target = intent.getIntExtra("targetValue",0);

        // Set goal details
        txtGoalName.setText(goalName);
        txtGoalDescription.setText(goalDescription);
        progressGoal.setProgress((int) (((float)goalProgress-start)/((float)target-start)*100));

        // Set up Back button
        btnBack.setOnClickListener(v -> finish()); // Ends this activity and returns to the previous one

        // Set up Increment and Reset Progress Buttons
        btnIncrementProgress.setOnClickListener(v -> {
            if (((float) (goalProgress - start) / (float) (target - start)) < 1) {
                goalProgress += 1+((target - start) / 10);
                progressGoal.setProgress((int) (((float) (goalProgress - start) / (float) (target - start)) * 100));
                updateGoalProgress(goalProgress);
            }
        });


        btnResetProgress.setOnClickListener(v -> {
            goalProgress = start;
            progressGoal.setProgress(0);
            updateGoalProgress(goalProgress);
        });
    }

    private void updateGoalProgress(int newProgress) {
        // Update the progress of the goal in the file
        try {
            File goalsFile = new File(getFilesDir(), "goals.txt");
            File tempFile = new File(getFilesDir(), "temp_goals.txt");

            BufferedReader reader = new BufferedReader(new FileReader(goalsFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] goalData = line.split(";");
                if (goalData[0].equals(goalName)) {
                    // Update the progress value for this goal
                    goalData[4] = String.valueOf(newProgress);
                    line = String.join(";", goalData);
                }
                writer.write(line + "\n");
            }

            reader.close();
            writer.close();

            // Replace the original file with the updated file
            if (goalsFile.delete()) {
                tempFile.renameTo(goalsFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
