package com.example.journeyfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View mainView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button tutorialButton = findViewById(R.id.tutorial_button);
        tutorialButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Tutorial.class);
            startActivity(intent);
        });

        Button profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(v -> checkAndNavigateToProfile());
    }

    private void checkAndNavigateToProfile() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.orderByChild("profile").limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String userId = snapshot.getKey();
                                Intent intent = new Intent(MainActivity.this, ProfileBio.class);
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                                return;
                            }
                        } else {
                            Intent intent = new Intent(MainActivity.this, ProfilePage.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this,
                                "Error checking profile: " + databaseError.getMessage(),
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, ProfilePage.class);
                        startActivity(intent);
                    }
                });
    }
}