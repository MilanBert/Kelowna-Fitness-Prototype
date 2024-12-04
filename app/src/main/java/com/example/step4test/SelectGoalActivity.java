package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectGoalActivity extends AppCompatActivity {

    private Button btnBack;
    private TextView txtGoalName, txtGoalDescription;
    private ProgressBar progressGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goal);

        // Initialize UI components
        btnBack = findViewById(R.id.btnBack);
        txtGoalName = findViewById(R.id.txtGoalName);
        txtGoalDescription = findViewById(R.id.txtGoalDescription);
        progressGoal = findViewById(R.id.progressGoal);

        // Get data from Intent
        Intent intent = getIntent();
        String goalName = intent.getStringExtra("goalName");
        String goalDescription = intent.getStringExtra("goalDescription");
        int goalProgress = intent.getIntExtra("goalProgress", 0);

        // Set data to UI components
        txtGoalName.setText(goalName);
        txtGoalDescription.setText(goalDescription);
        progressGoal.setProgress(goalProgress);

        // Set up Back button functionality
        btnBack.setOnClickListener(v -> finish());
    }
}
