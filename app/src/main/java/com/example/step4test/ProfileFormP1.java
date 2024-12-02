package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ProfileFormP1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_form_p1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner weights = findViewById(R.id.weights);
        Spinner heights = findViewById(R.id.heights);
        Spinner ages = findViewById(R.id.ages);
        Spinner genders = findViewById(R.id.genders);

        // Load weight options
        List<String> weightOptions = new ArrayList<>();
        for (int i = 40; i <= 200; i += 5) { // Weight range: 40kg to 200kg
            weightOptions.add(i + "kg/" + (int) (i * 2.20462) + "lb"); // Convert kg to lb
        }
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, weightOptions);
        weights.setAdapter(weightAdapter);

        // Load height options
        List<String> heightOptions = new ArrayList<>();
        for (int i = 120; i <= 210; i += 5) { // Height range: 120cm to 210cm
            int feet = (int) (i / 30.48);
            int inches = (int) ((i / 2.54) % 12);
            heightOptions.add(i + "cm/" + feet + "'" + inches + "\""); // Convert cm to feet and inches
        }
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, heightOptions);
        heights.setAdapter(heightAdapter);

        // Load age options
        List<String> ageOptions = new ArrayList<>();
        for (int i = 10; i <= 60; i++) {
            ageOptions.add(String.valueOf(i));
        }
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ageOptions);
        ages.setAdapter(ageAdapter);

        // Load gender options
        List<String> genderOptions = new ArrayList<>();
        genderOptions.add("Male");
        genderOptions.add("Female");
        genderOptions.add("Non-Binary");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderOptions);
        genders.setAdapter(genderAdapter);

        // Back button click listener
        Button back = findViewById(R.id.profile_back_button);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileFormP1.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Next button click listener
        Button next = findViewById(R.id.profile_next_button);
        next.setOnClickListener(v -> {
            // Read the selected values from spinners
            String selectedWeight = weights.getSelectedItem().toString();
            String selectedHeight = heights.getSelectedItem().toString();
            String selectedAge = ages.getSelectedItem().toString();
            String selectedGender = genders.getSelectedItem().toString();

            // Pass the data to ProfileFormP2
            Intent intent = new Intent(ProfileFormP1.this, ProfileFormP2.class);
            intent.putExtra("weight", selectedWeight);
            intent.putExtra("height", selectedHeight);
            intent.putExtra("age", selectedAge);
            intent.putExtra("gender", selectedGender);
            startActivity(intent);
        });
    }
}
