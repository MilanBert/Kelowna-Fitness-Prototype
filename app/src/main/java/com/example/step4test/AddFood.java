package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class AddFood extends AppCompatActivity {

    private int count = 1; // Default count
    private int caloriesPerItem = 0; // Calories for one item
    private final HashMap<String, Integer> foodTable = new HashMap<>(); // Common food table

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        Button cancel = findViewById(R.id.addFoodCancel);
        Button more = findViewById(R.id.more);
        Button less = findViewById(R.id.less);
        Button logFood = findViewById(R.id.logfood);

        TextView countButton = findViewById(R.id.count);
        EditText foodInput = findViewById(R.id.food_input);
        TextView calDisplay = findViewById(R.id.calDisplay);

        // Populate the table of common foods
        populateFoodTable();

        // Cancel button: Go back to the previous activity
        cancel.setOnClickListener(v -> finish());

        // More button: Increment count and update calories
        more.setOnClickListener(v -> {
            count++;
            countButton.setText(String.valueOf(count));
            updateCalorieDisplay(calDisplay);
        });

        // Less button: Decrement count (minimum 1) and update calories
        less.setOnClickListener(v -> {
            if (count > 1) {
                count--;
                countButton.setText(String.valueOf(count));
                updateCalorieDisplay(calDisplay);
            } else {
                Toast.makeText(this, "Cannot go below 1", Toast.LENGTH_SHORT).show();
            }
        });

        // Food input: Check if food is in the table and display calories
        foodInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String foodName = foodInput.getText().toString().trim();
                if (foodTable.containsKey(foodName)) {
                    caloriesPerItem = foodTable.get(foodName);
                    calDisplay.setText(caloriesPerItem + " Kcal");
                } else {
                    calDisplay.setText("Food not found");
                }
            }
        });

        // Log food button: Pass data back to the LogCalories activity
        logFood.setOnClickListener(v -> {
            String foodName = foodInput.getText().toString().trim();
            if (!foodName.isEmpty() && caloriesPerItem > 0) {
                Intent intent = new Intent();
                intent.putExtra("foodName", foodName);
                intent.putExtra("calories", caloriesPerItem * count);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Please enter a valid food and quantity", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Populate a table of common foods with their calories
    private void populateFoodTable() {
        foodTable.put("Apple", 52);
        foodTable.put("Banana", 89);
        foodTable.put("Cake", 250);
        foodTable.put("Egg", 68);
        foodTable.put("Milk", 103);
        foodTable.put("Bread", 79);
        foodTable.put("Rice", 205);
        foodTable.put("Chicken Breast", 165);
        foodTable.put("Orange", 47);
        foodTable.put("Potato", 110);
        foodTable.put("Cheese", 113);
        foodTable.put("Butter", 102);
        foodTable.put("Yogurt", 150);
        foodTable.put("Strawberry", 32);
        foodTable.put("Grapes", 69);
        foodTable.put("Carrot", 25);
        foodTable.put("Tomato", 22);
        foodTable.put("Beef", 250);
        foodTable.put("Fish", 206);
        foodTable.put("Pasta", 221);
        foodTable.put("Pizza", 285);
        foodTable.put("Burger", 354);
        foodTable.put("Hot Dog", 151);
        foodTable.put("Ice Cream", 137);
        foodTable.put("Chocolate Bar", 229);
        foodTable.put("Peanut Butter", 94);
        foodTable.put("Almonds", 164);
        foodTable.put("Cashews", 157);
        foodTable.put("Walnuts", 185);
        foodTable.put("Avocado", 240);
        foodTable.put("Blueberries", 57);
        foodTable.put("Mango", 202);
        foodTable.put("Peach", 58);
        foodTable.put("Pear", 101);
        foodTable.put("Cucumber", 45);
        foodTable.put("Lettuce", 15);
        foodTable.put("Spinach", 23);
        foodTable.put("Broccoli", 55);
        foodTable.put("Cauliflower", 25);
        foodTable.put("Sweet Potato", 86);
        foodTable.put("Corn", 123);
        foodTable.put("Green Beans", 31);
        foodTable.put("Eggplant", 25);
        foodTable.put("Onion", 44);
        foodTable.put("Bell Pepper", 24);
        foodTable.put("Zucchini", 17);
        foodTable.put("Mushroom", 22);
        foodTable.put("Cherries", 50);
        foodTable.put("Watermelon", 30);
        foodTable.put("Pineapple", 50);
        foodTable.put("Kiwi", 61);
        foodTable.put("Plum", 46);
        foodTable.put("Raspberries", 52);
        foodTable.put("Blackberries", 43);
        foodTable.put("Coconut", 354);
        foodTable.put("Shrimp", 99);
        foodTable.put("Turkey", 135);
        foodTable.put("Salmon", 208);
        foodTable.put("Tofu", 76);
        foodTable.put("Honey", 64);
        foodTable.put("Pancake", 227);
        foodTable.put("Oatmeal", 158);
        foodTable.put("Granola", 471);
        foodTable.put("Bagel", 250);
        foodTable.put("Crackers", 123);
        foodTable.put("Popcorn", 31);
        foodTable.put("Soup", 75);
        foodTable.put("Steak", 271);
        foodTable.put("Sausage", 229);
        foodTable.put("Brown Rice", 216);
        foodTable.put("Quinoa", 120);
        foodTable.put("Lentils", 116);
        foodTable.put("Chickpeas", 164);
        foodTable.put("Black Beans", 132);
        foodTable.put("Peas", 81);
        foodTable.put("Dates", 282);
        foodTable.put("Raisins", 299);
        foodTable.put("Fig", 74);
        foodTable.put("Maple Syrup", 52);
        foodTable.put("Jam", 56);
        foodTable.put("Pickles", 12);
        foodTable.put("Kimchi", 23);
        foodTable.put("Nuts", 607);
        foodTable.put("Olive Oil", 119);
    }


    // Update the calorie display based on count
    private void updateCalorieDisplay(TextView calDisplay) {
        if (caloriesPerItem > 0) {
            calDisplay.setText((caloriesPerItem * count) + " Kcal");
        }
    }
}
