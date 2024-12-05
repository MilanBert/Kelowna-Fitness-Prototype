package com.example.step4test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class LogCalories extends AppCompatActivity {

    private static final int ADD_FOOD_REQUEST_CODE = 1;

    private ArrayList<LoggedFood> loggedFoods = new ArrayList<>();
    private int totalCalories = 0;
    private int calorieGoal = 2000; // Example calorie goal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_calories);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button done = findViewById(R.id.logcaloriesback);
        Button addFoodButton = findViewById(R.id.addFoodButton);
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        TextView totalCaloriesView = findViewById(R.id.totalCalories);
        TextView caloriesTillGoalView = findViewById(R.id.caloriesTillGoal);

        // Set initial calorie views
        totalCaloriesView.setText("Total Calories: " + totalCalories);
        caloriesTillGoalView.setText("Calories Till Goal: " + (calorieGoal - totalCalories));

        done.setOnClickListener(v -> finish());

        // Add Food button click
        addFoodButton.setOnClickListener(v -> {
            Intent intent = new Intent(LogCalories.this, AddFood.class);
            startActivityForResult(intent, ADD_FOOD_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_FOOD_REQUEST_CODE && resultCode == RESULT_OK) {
            String foodName = data.getStringExtra("foodName");
            int calories = data.getIntExtra("calories", 0);

            if (foodName != null && calories > 0) {
                // Add the logged food to the list
                loggedFoods.add(new LoggedFood(foodName, calories));

                // Update total calories and calorie goal
                totalCalories += calories;

                // Update the views
                updateCalorieViews();

                // Add a new entry to the scrollable menu
                addFoodEntryToScrollView(foodName, calories);
            }
        }
    }

    private void updateCalorieViews() {
        TextView totalCaloriesView = findViewById(R.id.totalCalories);
        TextView caloriesTillGoalView = findViewById(R.id.caloriesTillGoal);

        totalCaloriesView.setText("Total Calories: " + totalCalories);
        caloriesTillGoalView.setText("Calories Till Goal: " + (calorieGoal - totalCalories));
    }

    private void addFoodEntryToScrollView(String foodName, int calories) {
        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        // Create a new LinearLayout to hold the food entry
        LinearLayout foodEntryLayout = new LinearLayout(this);
        foodEntryLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create a TextView for the food info
        TextView foodInfoView = new TextView(this);
        foodInfoView.setText(foodName + ": " + calories + " Kcal");
        foodInfoView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Set text color
        foodInfoView.setTypeface(Typeface.DEFAULT_BOLD); // Set text style (e.g., bold)
        foodInfoView.setPadding(16, 16, 16, 16); // Set padding
        foodInfoView.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        // Create a delete button
        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setBackgroundColor(Color.parseColor("#FF0000"));
        deleteButton.setOnClickListener(v -> {
            // Remove the food entry
            loggedFoods.removeIf(food -> food.getName().equals(foodName) && food.getCalories() == calories);
            totalCalories -= calories;
            updateCalorieViews();
            linearLayout.removeView(foodEntryLayout);
        });

        // Add the TextView and Button to the food entry layout
        foodEntryLayout.addView(foodInfoView);
        foodEntryLayout.addView(deleteButton);

        // Add the food entry layout to the scrollable menu
        linearLayout.addView(foodEntryLayout);
    }

    // Class to store food entries
    private static class LoggedFood {
        private final String name;
        private final int calories;

        public LoggedFood(String name, int calories) {
            this.name = name;
            this.calories = calories;
        }

        public String getName() {
            return name;
        }

        public int getCalories() {
            return calories;
        }
    }
}
