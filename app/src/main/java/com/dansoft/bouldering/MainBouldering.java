package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

//bouldering main menu
public class MainBouldering extends AppCompatActivity {
    private ImageButton mainMenuBtn;
    private Button analysisBtn, filtersBtn;
    private ImageView view;
    private Bitmap bmp;

    private ArrayList<Boulder> boulderArray = MainActivity.dbHandler.readBoulders();
    private int circleRadius = 20;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bouldering);

        //initialising UI elements
        mainMenuBtn = findViewById(R.id.idBtnMainMenu);
        filtersBtn = findViewById(R.id.idBtnFilters);
        analysisBtn = findViewById(R.id.idBtnAnalysis);
        view = findViewById(R.id.imageView);

        //Btn: goes back to the main menu
        mainMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);
        });

        //Btn: starts Filters activity
        filtersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Filters.class);
            v.getContext().startActivity(intent);
        });

        //Btn: starts Analysis activity
        analysisBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Analysis.class);
            v.getContext().startActivity(intent);
        });

        //draws all the coloured circles
        view.addOnLayoutChangeListener((v, i, i1, i2, i3, i4, i5, i6, i7) -> {

            //applies filters to database of boulders
            ArrayList<Float> gradeBoundaries = MainActivity.currentFilters.getGradeBoundaries();
            if (gradeBoundaries.size() != 0) {
                float lower = gradeBoundaries.get(0);
                float upper = gradeBoundaries.get(1);

                ArrayList<Boulder> toBeRemoved = new ArrayList<>();
                for (Boulder b: boulderArray) {
                    float grade = Filters.gradeToNum(b.getGrade());
                    if (grade < lower || grade > upper) {
                        toBeRemoved.add(b);
                    }
                }
                boulderArray.removeAll(toBeRemoved);
            }

            //displays error message if no boulders match the applied filters
            if (boulderArray.size() == 0) {
                Toast.makeText(MainBouldering.this, "There are no boulders that match your filters.", Toast.LENGTH_SHORT).show();
            }

            ArrayList<Integer> styleIDs = MainActivity.currentFilters.getStyleIDs();
            ArrayList<Boulder> filteredBoulders = new ArrayList<>();
            for (Boulder b: boulderArray) {
                boolean isSubset = b.getStyleIDs().containsAll(styleIDs);
                if (isSubset) {
                    filteredBoulders.add(b);
                }
            }

            // Create a bitmap object of the view size
            if(bmp==null) bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

            // Create a canvas with the bitmap
            Canvas canvas = new Canvas(bmp);

            //set the background of the canvas to boulder_layout
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.boulder_layout, null);
            d.setBounds(0, 50, view.getWidth(), 1400);
            d.draw(canvas);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            //for loop to draw all boulders
            for (Boulder b: filteredBoulders) {
                //switch statement to set the colour of each boulder
                switch (b.getColour()) {
                    case "white":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderWhite));
                        break;
                    case "black":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderBlack));
                        break;
                    case "blue":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderBlue));
                        break;
                    case "orange":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderOrange));
                        break;
                    case "yellow":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderYellow));
                        break;
                    case "red":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderRed));
                        break;
                    case "green":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderGreen));
                        break;
                    case "pink":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderPink));
                        break;
                    case "brown":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderBrown));
                        break;
                    case "purple":
                        paint.setColor(ContextCompat.getColor(getBaseContext(), R.color.boulderPurple));
                        break;
                }
                canvas.drawCircle(b.getX(), b.getY(), circleRadius, paint);
            }

            view.setImageBitmap(bmp);

        });

        //onTouchListener to detect if someone clicks on a boulder
        view.setOnTouchListener((v, event) -> {
            Point point = new Point();
            point.x = (int) event.getX();
            point.y = (int) event.getY();

            System.out.println("point: " + point);

            double centerX;
            double centerY;
            double distanceX;
            double distanceY;

            //for loop to set region for each boulder
            for (Boulder b: boulderArray) {

                centerX = b.getX();
                centerY = b.getY();
                distanceX = point.x - centerX;
                distanceY = point.y - centerY;

                Boolean isInside = (distanceX * distanceX) + (distanceY * distanceY) <= circleRadius * circleRadius;

                //if a click is detected inside the region of a boulder
                //the BoulderInfo activity is started and customized for that boulder
                if (isInside) {
                    Intent intent = new Intent(v.getContext(), BoulderInfo.class);
                    intent.putExtra("boulderID", String.valueOf(b.getBoulderID()));
                    v.getContext().startActivity(intent);
                }
            }
            return false;
        });
    }
}