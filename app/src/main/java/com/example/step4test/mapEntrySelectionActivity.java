package com.example.step4test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class mapEntrySelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map_entry_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button end = findViewById(R.id.button2);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button submit = findViewById(R.id.button3);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.editTextText);
                String location = input.getText().toString();
                Intent intent = new Intent(mapEntrySelectionActivity.this, mapFragActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("location", location);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Button useLocation = findViewById(R.id.buttonUseLoc);
        useLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mapEntrySelectionActivity.this, mapFragActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("location", "useLocation");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}