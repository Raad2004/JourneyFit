package com.example.journeyfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FoodTracking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.food_tracking);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.food_tracking), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    // bottom navigation button
        Button foodButton = findViewById(R.id.food_button);
        Button progressButton = findViewById(R.id.progress_button);
        Button nearbyButton = findViewById(R.id.nearby_button);

    // "+" Buttons alongside the "meal" names.
    Button addMeal = findViewById(R.id.add);

    //Add-meal form
        LinearLayout popupAddMeal = findViewById(R.id.popup_add_meal);
        popupAddMeal.setVisibility(View.GONE);
        //Form is visible upon clicking "add-meal".
        addMeal.setOnClickListener(v -> popupAddMeal.setVisibility(View.VISIBLE));

        //finding elements from add-form
        RadioGroup mealSelector = findViewById(R.id.meal_selector);
        EditText caloriesInput = findViewById(R.id.calories_input);
        EditText carbsInput = findViewById(R.id.carbs_input);
        EditText fatsInput = findViewById(R.id.fats_input);
        EditText proteinInput = findViewById(R.id.protein_input);
        Button confirmButton = findViewById(R.id.confirm_button);

        //finding the fields we need to update upon user confirming
        TextView meal1cals = findViewById(R.id.meal1cals);
        TextView meal1carbs = findViewById(R.id.meal1carbs);
        TextView meal1fats = findViewById(R.id.meal1fats);
        TextView meal1protein = findViewById(R.id.meal1protein);

        TextView meal2cals = findViewById(R.id.meal2cals);
        TextView meal2carbs = findViewById(R.id.meal2carbs);
        TextView meal2fats = findViewById(R.id.meal2fats);
        TextView meal2protein = findViewById(R.id.meal2protein);

        TextView meal3cals = findViewById(R.id.meal3cals);
        TextView meal3carbs = findViewById(R.id.meal3carbs);
        TextView meal3fats = findViewById(R.id.meal3fats);
        TextView meal3protein = findViewById(R.id.meal3protein);

        Button homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(FoodTracking.this, MainActivity.class);
            startActivity(intent);
        });

        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodTracking.this, ProgressActivity.class);
                startActivity(intent);
            }
        });


        // finding what meal the user desires to add to
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedMealId = mealSelector.getCheckedRadioButtonId();

                // Parse and store the nutritional values as integers
                try {
                    int caloriesValue = Integer.parseInt(caloriesInput.getText().toString().trim());
                    int carbsValue = Integer.parseInt(carbsInput.getText().toString().trim());
                    int fatsValue = Integer.parseInt(fatsInput.getText().toString().trim());
                    int proteinValue = Integer.parseInt(proteinInput.getText().toString().trim());

                    // Check which meal is selected and add the values accordingly
                    if (selectedMealId == R.id.meal1_radio) {  // Assuming R.id.meal1RadioButton is the radio button for meal 1
                        // Get the current values of meal1
                        int currentMeal1Cals = Integer.parseInt(meal1cals.getText().toString());
                        int currentMeal1Carbs = Integer.parseInt(meal1carbs.getText().toString());
                        int currentMeal1Fats = Integer.parseInt(meal1fats.getText().toString());
                        int currentMeal1Protein = Integer.parseInt(meal1protein.getText().toString());

                        // Add the new input values to the existing values
                        meal1cals.setText(String.valueOf(currentMeal1Cals + caloriesValue));
                        meal1carbs.setText(String.valueOf(currentMeal1Carbs + carbsValue));
                        meal1fats.setText(String.valueOf(currentMeal1Fats + fatsValue));
                        meal1protein.setText(String.valueOf(currentMeal1Protein + proteinValue));
                    }
                    else if (selectedMealId == R.id.meal2_radio) {  // Assuming R.id.meal2RadioButton is the radio button for meal 2
                        // Similar code for meal 2
                        int currentMeal2Cals = Integer.parseInt(meal2cals.getText().toString());
                        int currentMeal2Carbs = Integer.parseInt(meal2carbs.getText().toString());
                        int currentMeal2Fats = Integer.parseInt(meal2fats.getText().toString());
                        int currentMeal2Protein = Integer.parseInt(meal2protein.getText().toString());

                        meal2cals.setText(String.valueOf(currentMeal2Cals + caloriesValue));
                        meal2carbs.setText(String.valueOf(currentMeal2Carbs + carbsValue));
                        meal2fats.setText(String.valueOf(currentMeal2Fats + fatsValue));
                        meal2protein.setText(String.valueOf(currentMeal2Protein + proteinValue));
                    }
                    else if (selectedMealId == R.id.meal3_radio) {  // Assuming R.id.meal3RadioButton is the radio button for meal 3
                        // Similar code for meal 3
                        int currentMeal3Cals = Integer.parseInt(meal3cals.getText().toString());
                        int currentMeal3Carbs = Integer.parseInt(meal3carbs.getText().toString());
                        int currentMeal3Fats = Integer.parseInt(meal3fats.getText().toString());
                        int currentMeal3Protein = Integer.parseInt(meal3protein.getText().toString());

                        meal3cals.setText(String.valueOf(currentMeal3Cals + caloriesValue));
                        meal3carbs.setText(String.valueOf(currentMeal3Carbs + carbsValue));
                        meal3fats.setText(String.valueOf(currentMeal3Fats + fatsValue));
                        meal3protein.setText(String.valueOf(currentMeal3Protein + proteinValue));
                    }
                } catch (NumberFormatException e) {
                    // Handle invalid input (non-numeric values or empty fields)
                    System.out.println("Error: Please enter valid numeric values for all fields.");
                    return;
                }

                // Hide the add meal form after confirming
                popupAddMeal.setVisibility(View.GONE);
            }
        });






    }
}
