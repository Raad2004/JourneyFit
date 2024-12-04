package com.example.journeyfit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {
    private TextInputEditText aboutMeEditText, heightEditText, weightEditText, stepsEditText;
    private Button createProfileButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        aboutMeEditText = findViewById(R.id.aboutMeEditText);
        heightEditText = findViewById(R.id.heightEditText);
        weightEditText = findViewById(R.id.weightEditText);
        stepsEditText = findViewById(R.id.stepsEditText);
        createProfileButton = findViewById(R.id.createProfileButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createProfileButton.setOnClickListener(v -> createProfile());
    }

    private void createProfile() {
        String aboutMe = aboutMeEditText.getText().toString().trim();
        String height = heightEditText.getText().toString().trim();
        String weight = weightEditText.getText().toString().trim();
        String steps = stepsEditText.getText().toString().trim();

        if (validateInputs(aboutMe, height, weight, steps)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference usersRef = database.getReference("users");

            String userId = usersRef.push().getKey();

            Map<String, Object> profileData = new HashMap<>();
            profileData.put("aboutMe", aboutMe);
            profileData.put("height", Double.parseDouble(height));
            profileData.put("weight", Double.parseDouble(weight));
            profileData.put("steps", Integer.parseInt(steps));
            profileData.put("createdAt", System.currentTimeMillis());

            usersRef.child(userId).child("profile").setValue(profileData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(ProfilePage.this, "Profile Created Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfilePage.this, ProfileBio.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ProfilePage.this, "Failed to create profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }


    private boolean validateInputs(String aboutMe, String height, String weight, String steps) {
        boolean isValid = true;

        if (aboutMe.isEmpty()) {
            aboutMeEditText.setError("About Me cannot be empty");
            isValid = false;
        }

        try {
            if (height.isEmpty()) {
                heightEditText.setError("Height cannot be empty");
                isValid = false;
            } else {
                double heightValue = Double.parseDouble(height);
                if (heightValue <= 0) {
                    heightEditText.setError("Height must be a positive number");
                    isValid = false;
                }
            }

            if (weight.isEmpty()) {
                weightEditText.setError("Weight cannot be empty");
                isValid = false;
            } else {
                double weightValue = Double.parseDouble(weight);
                if (weightValue <= 0) {
                    weightEditText.setError("Weight must be a positive number");
                    isValid = false;
                }
            }

            if (steps.isEmpty()) {
                stepsEditText.setError("Steps cannot be empty");
                isValid = false;
            } else {
                int stepsValue = Integer.parseInt(steps);
                if (stepsValue < 0) {
                    stepsEditText.setError("Steps cannot be negative");
                    isValid = false;
                }
            }
        } catch (NumberFormatException e) {
            if (height.isEmpty()) {
                heightEditText.setError("Invalid height format");
            }
            if (weight.isEmpty()) {
                weightEditText.setError("Invalid weight format");
            }
            if (steps.isEmpty()) {
                stepsEditText.setError("Invalid steps format");
            }
            isValid = false;
        }

        return isValid;
    }
}