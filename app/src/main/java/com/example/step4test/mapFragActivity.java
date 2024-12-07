package com.example.step4test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapFragActivity extends AppCompatActivity implements OnMapReadyCallback {
     String location;
     Marker firstMark, secondMark, thirdMark, fourMark, fiveMark,sixMark, sevenMark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        location = bundle.getString("location");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_frag);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button end = findViewById(R.id.buttonToExit);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        RadioGroup open = findViewById(R.id.radgroupone);
        open.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                sevenMark.setVisible(false);
                fourMark.setVisible(false);
                firstMark.setVisible(false);
            }
        });

        RadioGroup close = findViewById(R.id.radgrouptwo);
        close.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                sixMark.setVisible(false);
                fourMark.setVisible(false);
                secondMark.setVisible(false);
            }
        });



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng kelowna;
        firstMark = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.8810, -119.4921))
                .title("Global Okanagan")
                .contentDescription("10am-10pm"));

        secondMark = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.8839, -119.4982))
                .title("GoodLife Fitness")
                .contentDescription("6am-10pm"));

        thirdMark = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.8892, -119.4964))
                .title("Golds Gym")
                .contentDescription("6am-8pm"));
        fourMark = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.8815, -119.4940))
                .title("Health Gym")
                .contentDescription("10am-10pm"));
        fiveMark = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.9394, -119.3948))
                .title("UBCO Gym")
                .contentDescription("6am-10pm"));
        sixMark = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.0517, -119.4126))
                .title("Grind Gym")
                .contentDescription("8am-8pm"));
        sevenMark = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(49.8865, -119.4989))
                .title("Bull Gym")
                .contentDescription("8am-5pm"));


        Marker [] markers = {firstMark, secondMark, thirdMark, fourMark, fiveMark,sixMark, sevenMark};
        if(location.equalsIgnoreCase("useLocation")){
            kelowna = new LatLng(49.9394, -119.3948);     //I couldn't figure out how to use the location manager to pull location data properly


        }else {
            kelowna = findSearchResult(location, markers);
        }
        // Set the map type to Normal.
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Add a marker on the map coordinates.
        //Move the camera to the map coordinates and zoom in closer.

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kelowna, 15));
        googleMap.getUiSettings().setZoomControlsEnabled(true);




    }

    LatLng findSearchResult(String gymName, Marker [] markers){
        for(Marker currMarker: markers){
            if(gymName.equalsIgnoreCase(currMarker.getTitle()))
                return currMarker.getPosition();
        }
        Toast.makeText(this, "Error Finding Gym By That Name, Redirecting to Downtown Kelowna", Toast.LENGTH_LONG).show();
        return new LatLng(49.8863, -119.4966);
    }
}