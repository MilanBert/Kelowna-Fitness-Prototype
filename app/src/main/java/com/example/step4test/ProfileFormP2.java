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

import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileFormP2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_form_p2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Retrieve data from Intent
        String weight = getIntent().getStringExtra("weight");
        String height = getIntent().getStringExtra("height");
        String age = getIntent().getStringExtra("age");
        String gender = getIntent().getStringExtra("gender");

        // Display the data in TextViews
        TextView show_weight = findViewById(R.id.weight);
        TextView show_height = findViewById(R.id.height);
        TextView show_age = findViewById(R.id.age);
        TextView show_gender = findViewById(R.id.gender);

        show_weight.setText(weight);
        show_height.setText(height);
        show_age.setText(age);
        show_gender.setText(gender);

        // EditText for entering name
        EditText enterName = findViewById(R.id.enterName);

        // Back button functionality
        Button back = findViewById(R.id.profile_back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileFormP2.this, ProfileFormP1.class);
            startActivity(intent);
            finish();
        });

        // Submit button functionality
        Button submit = findViewById(R.id.profile_submit);
        submit.setOnClickListener(v -> {
            String name = enterName.getText().toString().trim();

            // Validate name input
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save all information to a text file
            String fileName = "profile_info.txt";
            String fileContent = "Name: " + name + "\n" +
                    "Weight: " + weight + "\n" +
                    "Height: " + height + "\n" +
                    "Age: " + age + "\n" +
                    "Gender: " + gender;

            try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {
                fos.write(fileContent.getBytes());
                Toast.makeText(this, "Profile information saved!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save profile information.", Toast.LENGTH_SHORT).show();
            }

            // Navigate back to MainActivity
            Intent intent = new Intent(ProfileFormP2.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
