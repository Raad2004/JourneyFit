package com.example.journeyfit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_progress);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button foodButton = findViewById(R.id.Food);
        // Add listener for Food button
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressActivity.this, FoodTracking.class);
                startActivity(intent);
            }
        });

        Spinner spinner = findViewById(R.id.Spinner);
        String[] options = {"Weight", "Calories Burned", "Distance", "Bench Press", "Squat"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ProgressBar progressBar = findViewById(R.id.horizontalBarForSteps);
        progressBar.setProgress(50);

        LinearLayout graphContainer = findViewById(R.id.graphContainer);

        // Example data points for the graph
        float[] dataPoints = {10, 20, 15, 30, 25};

        // Create and add the custom graph view
        LineGraphView graphView = new LineGraphView(this, dataPoints);
        graphContainer.addView(graphView);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateGraphAndYAxisLabel(position);

                // Example of updating graph data based on spinner selection
                switch (position) {
                    case 0: // Weight
                        graphView.setDataPoints(new float[]{10, 14, 15, 18, 19});
                        break;
                    case 1: // Calories Burned
                        graphView.setDataPoints(new float[]{20, 25, 30, 280, 320});
                        break;
                    case 2: // Distance
                        graphView.setDataPoints(new float[]{1, 2, 3, 2.5f, 3.5f});
                        break;
                    case 3: // Bench Press
                        graphView.setDataPoints(new float[]{0, 30, 40, 50, 95});
                        break;
                    case 4: // Squat
                        graphView.setDataPoints(new float[]{0, 30, 40, 50, 80});
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    // Correct placement of the updateGraphAndYAxisLabel method (outside onCreate)
    private void updateGraphAndYAxisLabel(int position) {
        TextView yAxisLabel = findViewById(R.id.yAxisLabel);  // Reference to the Y-axis label TextView

        switch (position) {
            case 0: // Weight
                yAxisLabel.setText("LB");
                break;
            case 1: // Calories Burned
                yAxisLabel.setText("KCAL");
                break;
            case 2: // Distance
                yAxisLabel.setText("KMS");
                break;
            case 3: // Bench Press
                yAxisLabel.setText("LB");
                break;
            case 4: // Squat
                yAxisLabel.setText("LB");
                break;
            default:
                yAxisLabel.setText(""); // Default empty label if selection is unexpected
                break;
        }
    }

    // Custom view for drawing the graph
    public static class LineGraphView extends View {
        private Paint paint;
        private float[] dataPoints;

        public LineGraphView(Context context, float[] dataPoints) {
            super(context);
            this.dataPoints = dataPoints;
            init();
        }

        private void init() {
            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
        }

        public void setDataPoints(float[] dataPoints) {
            this.dataPoints = dataPoints;
            invalidate(); // Redraw the view with new data
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (dataPoints == null || dataPoints.length < 2) {
                return; // Not enough points to draw a line
            }

            float width = getWidth();
            float height = getHeight();
            float maxDataPoint = getMaxDataPoint();

            float xInterval = width / (dataPoints.length - 1);
            float yScale = height / maxDataPoint;

            for (int i = 0; i < dataPoints.length - 1; i++) {
                float startX = i * xInterval;
                float startY = height - (dataPoints[i] * yScale);
                float stopX = (i + 1) * xInterval;
                float stopY = height - (dataPoints[i + 1] * yScale);
                canvas.drawLine(startX, startY, stopX, stopY, paint);
            }
        }

        private float getMaxDataPoint() {
            float max = Float.MIN_VALUE;
            for (float dataPoint : dataPoints) {
                if (dataPoint > max) {
                    max = dataPoint;
                }
            }
            return max;
        }
    }
}
