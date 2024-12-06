package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ProgressReport extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter myAdapter;
    private ArrayList<Goal> goalList; // Change to store Goal objects
    private Button backbutton;
    private ProgressBarView selectedGoalProgressBars;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_progress_report);

        Log.d("RecyclerView", "I just ran the onCreate method!");
        selectedGoalProgressBars = findViewById(R.id.selectedGoalProgressBars);
        // Initialize the goals and set up RecyclerView
        goalList = initializeDummyGoals(); // Call the method to initialize dummy data
        setUpRecyclerview(); // Set up RecyclerView after data is initialized
        backbutton = findViewById(R.id.BackBtn);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to GoalsActivity
                Intent intent = new Intent(ProgressReport.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        title = findViewById(R.id.titleText);
    }

    protected void setUpRecyclerview() {
        // Log the size of the goal list for debugging
        Log.d("RecyclerView", "Number of goals: " + goalList.size());

        // Initialize RecyclerView and set LayoutManager
        recyclerView = findViewById(R.id.Goals);
        if (recyclerView == null) {
            Log.e("RecyclerView", "RecyclerView with ID 'Goals' not found!");
            return;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the Adapter
        myAdapter = new RecycleAdapter(goalList); // Use the goalList to populate the RecyclerView
        recyclerView.setAdapter(myAdapter);
    }

    // Method to initialize dummy goal data
    protected ArrayList<Goal> initializeDummyGoals() {
        ArrayList<Goal> goals = new ArrayList<Goal>();
    try{
        goals.addAll(loadGoalsFromFile());
    }
        catch(Exception e) {
        //  Block of code to handle errors
    }


        // Create dummy goal objects with hardcoded values
        goals.add(new Goal("Daily Kcal", "daily", 100.0f, 40.0f, 20.0f));
        goals.add(new Goal("Running", "daily", 200.0f, 60.0f, 30.0f));
        goals.add(new Goal("Bench Press", "once", 150.0f, 80.0f, 50.0f));
        goals.add(new Goal("Weekly Pushups", "weekly", 70.0f, 50.0f, 25.0f));
        goals.add(new Goal("Daily Protein", "daily",28.0f, 17.0f, 11f));
        goals.add(new Goal("Weight", "once",190.0f, 280.0f, 300f));


        return goals;
    }

    public void updateSelectedGoalProgressBars(int position) {

        Goal selectedGoal = goalList.get(position);
title.setText(selectedGoal.getName());
        // Show progress bars for the selected goal
        selectedGoalProgressBars.setVisibility(View.VISIBLE);

        // Set progress values to the ProgressBarView (start, target, current)
        selectedGoalProgressBars.setProgressValues(selectedGoal.getStart(), selectedGoal.getTarget(), selectedGoal.getCurrent());
    }
    private ArrayList<Goal> loadGoalsFromFile() {

        File goalsFile = new File(getFilesDir(), "goals.txt");
        File completedGoalsFile = new File(getFilesDir(), "compgoals.txt");
        ArrayList<Goal> goals = new ArrayList<>();
        if (!goalsFile.exists()) {
            return null; // No file exists, nothing to load
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


                    goals.add(new Goal(goalName, goalFrequency,targetValue,progress,startValue));


            }
            return goals;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
