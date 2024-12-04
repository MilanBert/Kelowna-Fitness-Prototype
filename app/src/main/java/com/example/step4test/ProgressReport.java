package com.example.step4test;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProgressReport extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter myAdapter;
    private ArrayList<Goal> goalList; // Change to store Goal objects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_progress_report);

        // Apply window insets (not related to RecyclerView issue, but part of your code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ProgressReport), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("RecyclerView", "I just ran the onCreate method!");

        // Initialize the goals and set up RecyclerView
        goalList = initializeDummyGoals(); // Call the method to initialize dummy data
        setUpRecyclerview(); // Set up RecyclerView after data is initialized
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
        ArrayList<Goal> goals = new ArrayList<>();

        // Create dummy goal objects with hardcoded values
        goals.add(new Goal("Daily Kcal", 100.0f, 40.0f, 20.0f));
        goals.add(new Goal("Running", 200.0f, 60.0f, 30.0f));
        goals.add(new Goal("Bench Press", 150.0f, 80.0f, 50.0f));
        goals.add(new Goal("Weekly Pushups", 70.0f, 50.0f, 25.0f));
        goals.add(new Goal("Daily Protein", 28.0f, 17.0f, 11f));
        goals.add(new Goal("Weight", 190.0f, 280.0f, 300f));

        return goals;
    }
}
