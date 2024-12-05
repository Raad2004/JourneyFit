package com.example.journeyfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NearbyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nearby);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout eventList = findViewById(R.id.event_list);

        // Sample community events
        String[][] events = {
                {"Run Event", "40", "Kelowna Downtown", "5 km", "Join a fun group run in downtown Kelowna!"},
                {"Walk Event", "20", "Lakeshore Rd", "3 km", "Explore the beautiful waterfront with others."},
                {"Challenge Event", "75", "Kelowna", "10 km", "Test your endurance with this exciting challenge!"}
        };

        // Inflate and populate each event
        for (String[] event : events) {
            View eventView = createEventView(eventList, event[0], event[1], event[2], event[3], event[4]);
            eventList.addView(eventView);
        }

        //bottom navigation buttons
        Button ProgressButton=findViewById(R.id.progress_button);
        Button foodButton = findViewById(R.id.food_button);
        Button homeButton = findViewById(R.id.home_button);
        // Add listener for Food button
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyActivity.this, FoodTracking.class);
                startActivity(intent);
            }
        });
        ProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyActivity.this,ProgressActivity.class);
                startActivity(intent);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NearbyActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private View createEventView(LinearLayout parent, String name, String points, String location, String distance, String description) {
        // Inflate only the event template from the XML
        View eventView = LayoutInflater.from(this).inflate(R.layout.event_layout, null);

        // Set event details
        TextView eventName = eventView.findViewById(R.id.event_name);
        TextView eventDistance = eventView.findViewById(R.id.event_distance);
        TextView eventDescription = eventView.findViewById(R.id.event_description);
        Button joinButton = eventView.findViewById(R.id.join_button);
        TextView eventLocation = eventView.findViewById(R.id.event_location);
        TextView eventPoints = eventView.findViewById(R.id.event_points);

        eventName.setText(name);
        eventDistance.setText(distance);
        eventDescription.setText(description);
        eventLocation.setText(location);
        eventPoints.setText(points);

        // Handle join button click
        joinButton.setOnClickListener(v -> {
            if (joinButton.getText().toString().equals("Join")) {
                joinButton.setText("Joined");
            }
            else if (joinButton.getText().toString().equals("Joined")) {
                joinButton.setText("Join");
            }
        });

        return eventView;

    }
}