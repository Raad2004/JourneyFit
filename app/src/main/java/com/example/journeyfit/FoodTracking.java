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

    //default goals
    private int calGoal = 2000;
    private int carbGoal = 250;
    private int fatGoal = 67;
    private int proteinGoal = 100;

    private int totalCals = 0;
    private int totalCarbs = 0;
    private int totalFats = 0;
    private int totalProtein = 0;

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

        LinearLayout popupDietSelection = findViewById(R.id.popup_diet_selection);

        //set initial remaining fields values (with default goals
        updateRemainingFields();

    // bottom navigation button
        Button foodButton = findViewById(R.id.food_button);
        Button progressButton = findViewById(R.id.progress_button);
        Button nearbyButton = findViewById(R.id.nearby_button);

        nearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodTracking.this,NearbyActivity.class);
                startActivity(intent);
            }
        });

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

        TextView totalCals = findViewById(R.id.meal1cals2);
        TextView totalCarbs = findViewById(R.id.meal1cals3);
        TextView totalFats = findViewById(R.id.meal1cals4);
        TextView totalProtein = findViewById(R.id.meal1cals5);

        meal1carbs.setVisibility(View.GONE);
        meal1fats.setVisibility(View.GONE);
        meal1protein.setVisibility(View.GONE);

        meal2carbs.setVisibility(View.GONE);
        meal2fats.setVisibility(View.GONE);
        meal2protein.setVisibility(View.GONE);

        meal3carbs.setVisibility(View.GONE);
        meal3fats.setVisibility(View.GONE);
        meal3protein.setVisibility(View.GONE);



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

                try {
                    int caloriesValue = Integer.parseInt(caloriesInput.getText().toString().trim());
                    int carbsValue = Integer.parseInt(carbsInput.getText().toString().trim());
                    int fatsValue = Integer.parseInt(fatsInput.getText().toString().trim());
                    int proteinValue = Integer.parseInt(proteinInput.getText().toString().trim());

                    if (selectedMealId == R.id.meal1_radio) {
                        int currentMeal1Cals = Integer.parseInt(meal1cals.getText().toString());
                        int currentMeal1Carbs = Integer.parseInt(meal1carbs.getText().toString());
                        int currentMeal1Fats = Integer.parseInt(meal1fats.getText().toString());
                        int currentMeal1Protein = Integer.parseInt(meal1protein.getText().toString());

                        meal1cals.setText(String.valueOf(currentMeal1Cals + caloriesValue));
                        meal1carbs.setText(String.valueOf(currentMeal1Carbs + carbsValue));
                        meal1fats.setText(String.valueOf(currentMeal1Fats + fatsValue));
                        meal1protein.setText(String.valueOf(currentMeal1Protein + proteinValue));
                    } else if (selectedMealId == R.id.meal2_radio) {
                        int currentMeal2Cals = Integer.parseInt(meal2cals.getText().toString());
                        int currentMeal2Carbs = Integer.parseInt(meal2carbs.getText().toString());
                        int currentMeal2Fats = Integer.parseInt(meal2fats.getText().toString());
                        int currentMeal2Protein = Integer.parseInt(meal2protein.getText().toString());

                        meal2cals.setText(String.valueOf(currentMeal2Cals + caloriesValue));
                        meal2carbs.setText(String.valueOf(currentMeal2Carbs + carbsValue));
                        meal2fats.setText(String.valueOf(currentMeal2Fats + fatsValue));
                        meal2protein.setText(String.valueOf(currentMeal2Protein + proteinValue));
                    } else if (selectedMealId == R.id.meal3_radio) {
                        int currentMeal3Cals = Integer.parseInt(meal3cals.getText().toString());
                        int currentMeal3Carbs = Integer.parseInt(meal3carbs.getText().toString());
                        int currentMeal3Fats = Integer.parseInt(meal3fats.getText().toString());
                        int currentMeal3Protein = Integer.parseInt(meal3protein.getText().toString());

                        meal3cals.setText(String.valueOf(currentMeal3Cals + caloriesValue));
                        meal3carbs.setText(String.valueOf(currentMeal3Carbs + carbsValue));
                        meal3fats.setText(String.valueOf(currentMeal3Fats + fatsValue));
                        meal3protein.setText(String.valueOf(currentMeal3Protein + proteinValue));
                    }

                    // Calculate and update totals
                    int totalCalsValue = Integer.parseInt(meal1cals.getText().toString())
                            + Integer.parseInt(meal2cals.getText().toString())
                            + Integer.parseInt(meal3cals.getText().toString());
                    int totalCarbsValue = Integer.parseInt(meal1carbs.getText().toString())
                            + Integer.parseInt(meal2carbs.getText().toString())
                            + Integer.parseInt(meal3carbs.getText().toString());
                    int totalFatsValue = Integer.parseInt(meal1fats.getText().toString())
                            + Integer.parseInt(meal2fats.getText().toString())
                            + Integer.parseInt(meal3fats.getText().toString());
                    int totalProteinValue = Integer.parseInt(meal1protein.getText().toString())
                            + Integer.parseInt(meal2protein.getText().toString())
                            + Integer.parseInt(meal3protein.getText().toString());

                    setTotalCals(totalCalsValue);
                    setTotalCarbs(totalCarbsValue);
                    setTotalFats(totalFatsValue);
                    setTotalProtein(totalProteinValue);

                    // Update total fields
                    totalCals.setText(String.valueOf(totalCalsValue));
                    totalCarbs.setText(String.valueOf(totalCarbsValue));
                    totalFats.setText(String.valueOf(totalFatsValue));
                    totalProtein.setText(String.valueOf(totalProteinValue));

                    //update remaining fields
                    updateRemainingFields();

                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter valid numeric values for all fields.");
                    return;
                }

                popupAddMeal.setVisibility(View.GONE);
            }
        });

    Button generate = findViewById(R.id.btn_generate_food);
    generate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //this button gives the user options to select from pre selected goals.
            //these goals will be: keto, pescatarian

            popupDietSelection.setVisibility(View.VISIBLE);
            popupDietSelection.bringToFront();

        }
    });

        Button selectButton = findViewById(R.id.select_button);
        RadioGroup radioGroupDiet = findViewById(R.id.radioGroupDiet);

        //select button for diet selection
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected radio button's ID
                int selectedRadioButtonId = radioGroupDiet.getCheckedRadioButtonId();

                // Perform actions based on the selected diet
                if (selectedRadioButtonId == R.id.radioKeto) {
                    //set goals based on keto diet. this means low carbs and lower calories

                    setCalGoal(1500);
                    setCarbGoal(55);
                    setFatGoal(83);
                    setProteinGoal(131);


                } else if (selectedRadioButtonId == R.id.radioPescatarian) {
                    // Action for Pescatarian Diet

                    setCalGoal(2300);
                    setCarbGoal(173);
                    setFatGoal(103);
                    setProteinGoal(173);
                } else if (selectedRadioButtonId == R.id.radioVegetarian) {
                    // Action for Vegetarian Diet
                    setCalGoal(2000);
                    setCarbGoal(215);
                    setFatGoal(96);
                    setProteinGoal(70);
                }

                // Hide the popup after selection
                updateRemainingFields();
                popupDietSelection.setVisibility(View.GONE);

            }



        });
    }
    public int getCalGoal(){return this.calGoal;}
    public int getCarbGoal(){return this.carbGoal;}
    public int getFatGoal(){return this.fatGoal;}
    public int getProteinGoal(){return this.proteinGoal;}
    public int getTotalCals(){return this.totalCals;}
    public int getTotalCarbs(){return this.totalCarbs;}
    public int getTotalFats(){return this.totalFats;}
    public int getTotalProtein(){return this.totalProtein;}
    public void setCalGoal(int goal){this.calGoal = goal;}
    public void setCarbGoal(int goal){this.carbGoal = goal;}
    public void setFatGoal(int goal){this.fatGoal = goal;}
    public void setProteinGoal(int goal){this.proteinGoal = goal;}
    public void setTotalCals(int cals){this.totalCals = cals;}
    public void setTotalCarbs(int carbs){this.totalCarbs = carbs;}
    public void setTotalFats(int fats){this.totalFats = fats;}
    public void setTotalProtein(int protein){this.totalProtein = protein;}

    public void updateRemainingFields(){
        //set remaining values
        TextView remainingCals = findViewById(R.id.remainingCals);
        TextView remainingCarbs = findViewById(R.id.remainingCarbs);
        TextView remainingFats = findViewById(R.id.remainingFats);
        TextView remainingProtein = findViewById(R.id.remainingProtein);

        remainingCals.setText(String.valueOf(getCalGoal() - getTotalCals()));
        remainingCarbs.setText(String.valueOf(getCarbGoal() - getTotalCarbs()) + "g");
        remainingFats.setText(String.valueOf(getFatGoal() - getTotalFats()) + "g");
        remainingProtein.setText(String.valueOf(getProteinGoal() - getTotalProtein()) + "g");


    }



}
