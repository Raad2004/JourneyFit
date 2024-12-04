package com.example.journeyfit;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileBio extends AppCompatActivity {

    private TextView aboutMeTextView, heightTextView, weightTextView, stepsTextView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_bio);

        // Initialize UI components
        aboutMeTextView = findViewById(R.id.textView7);
        heightTextView = findViewById(R.id.textView8);
        weightTextView = findViewById(R.id.textView10);
        stepsTextView = findViewById(R.id.textView11);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        retrieveProfileData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void retrieveProfileData() {
        String userId = getIntent().getStringExtra("userId");

        if (userId == null) {
            Log.e("ProfileBio", "UserId is null. Cannot retrieve profile data.");
            Toast.makeText(this, "User ID is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child("users").child(userId).child("profile")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String aboutMe = dataSnapshot.child("aboutMe").getValue(String.class);
                            Double height = dataSnapshot.child("height").getValue(Double.class);
                            Double weight = dataSnapshot.child("weight").getValue(Double.class);
                            Integer steps = dataSnapshot.child("steps").getValue(Integer.class);

                            if (aboutMe != null) {
                                aboutMeTextView.setText(aboutMe);
                            } else {
                                aboutMeTextView.setText("No information provided.");
                            }

                            if (height != null) {
                                heightTextView.setText(String.format("%.2f", height));  // Format to 2 decimal places
                            } else {
                                heightTextView.setText("Not specified.");
                            }

                            if (weight != null) {
                                weightTextView.setText(String.format("%.2f", weight));  // Format to 2 decimal places
                            } else {
                                weightTextView.setText("Not specified.");
                            }

                            if (steps != null) {
                                stepsTextView.setText(String.valueOf(steps));
                            } else {
                                stepsTextView.setText("No step count available.");
                            }
                        } else {
                            Log.e("ProfileBio", "No profile data found for userId: " + userId);
                            Toast.makeText(ProfileBio.this, "No profile data found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("ProfileBio", "Failed to retrieve data: " + databaseError.getMessage());
                        Toast.makeText(ProfileBio.this, "Error retrieving profile data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
