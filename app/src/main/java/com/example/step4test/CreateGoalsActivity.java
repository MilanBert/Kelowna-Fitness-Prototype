package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class CreateGoalsActivity extends AppCompatActivity {

    private Button btnBack, btnSaveGoal;
    private EditText edtGoalName, edtGoalDescription;
    private Spinner spinnerObjectives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goals);

        // Initialize UI components
        btnBack = findViewById(R.id.btnBack);
        btnSaveGoal = findViewById(R.id.btnSaveGoal);
        edtGoalName = findViewById(R.id.edtGoalName);
        edtGoalDescription = findViewById(R.id.edtGoalDescription);
        spinnerObjectives = findViewById(R.id.spinnerObjectives);

        // Set up Back button functionality
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to GoalsActivity
                Intent intent = new Intent(CreateGoalsActivity.this, GoalsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set up Save Goal button functionality
        btnSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoalToFile();
            }
        });

        // Populate the spinner with predefined objectives
        setupSpinner();
    }

    private void setupSpinner() {
        // Example: Populate the spinner with predefined objectives
        String[] objectives = {"Lose Weight", "Build Muscle", "Improve Endurance", "General Fitness"};
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, objectives);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerObjectives.setAdapter(adapter);
    }

    private void saveGoalToFile() {
        String goalName = edtGoalName.getText().toString().trim();
        String goalDescription = edtGoalDescription.getText().toString().trim();
        String selectedObjective = spinnerObjectives.getSelectedItem().toString();

        // Validate inputs
        if (goalName.isEmpty() || goalDescription.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        String goalData = goalName + ";" + goalDescription + ";" + selectedObjective + "\n";

        try {
            // Save the goal data to a file
            FileOutputStream fos = openFileOutput("goals.txt", MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(goalData);
            osw.close();
            fos.close();

            // Log successful save
            android.util.Log.d("CreateGoalsActivity", "Goal saved successfully: " + goalData);

            // Show success message
            Toast.makeText(this, "Goal saved successfully!", Toast.LENGTH_SHORT).show();

            // Navigate back to GoalsActivity
            Intent intent = new Intent(CreateGoalsActivity.this, GoalsActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving goal: " + e.getMessage(), Toast.LENGTH_LONG).show();
            android.util.Log.e("CreateGoalsActivity", "Error saving goal", e);
        }
    }

}
