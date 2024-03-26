package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

public class Filters extends AppCompatActivity {

    private ImageButton boulderingBtn;
    private CheckBox jugCB, crimpCB, pinchCB, pocketCB, sloperCB,
            slabCB, overhangCB, areteCB, verticalCB, traverseCB,
            dynamicCB, fingeryCB, technicalCB, powerfulCB, reachyCB;

    private Button applyFiltersBtn;
    private TextView gradeRangeTV;
    private RangeSlider gradeSliderRS;

    private ArrayList<Integer> styleIDs = new ArrayList<>();
    private Integer styleID;
    private ArrayList<Float> boundaries;
    private String lower, upper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        //retrieving current filters
        styleIDs = MainActivity.currentFilters.getStyleIDs();

        //initializing UI elements
        boulderingBtn = findViewById(R.id.idBtnBouldering);
        applyFiltersBtn = findViewById(R.id.idBtnApplyFilters);

        gradeRangeTV = findViewById(R.id.idTVGradeRange);
        gradeSliderRS = findViewById(R.id.idRSGradeSlider);
        gradeSliderRS.setLabelBehavior(LabelFormatter.LABEL_GONE);

        jugCB = findViewById(R.id.idCBJug);
        crimpCB = findViewById(R.id.idCBCrimp);
        pinchCB = findViewById(R.id.idCBPinch);
        pocketCB = findViewById(R.id.idCBPocket);
        sloperCB = findViewById(R.id.idCBSloper);
        slabCB = findViewById(R.id.idCBSlab);
        overhangCB = findViewById(R.id.idCBOverhang);
        areteCB = findViewById(R.id.idCBArete);
        verticalCB = findViewById(R.id.idCBVertical);
        traverseCB = findViewById(R.id.idCBTraverse);
        dynamicCB = findViewById(R.id.idCBDynamic);
        fingeryCB = findViewById(R.id.idCBFingery);
        technicalCB = findViewById(R.id.idCBTechnical);
        powerfulCB = findViewById(R.id.idCBPowerful);
        reachyCB = findViewById(R.id.idCBReachy);

        //setting checkboxes to show currently applied filters
        setCheckboxesFalse();
        setCheckboxes(styleIDs);

        //setting initial text and slider position
        boundaries = MainActivity.currentFilters.getGradeBoundaries();
        if (boundaries.size() != 0) {
            gradeSliderRS.setValues(boundaries);

            lower = numToGrade(boundaries.get(0));
            upper = numToGrade(boundaries.get(1));
            gradeRangeTV.setText("Grade range: " + lower + " - " + upper);
        } else {
            gradeRangeTV.setText("Grade range: 4 - 8b+");
        }

        //reads slider upon change
        gradeSliderRS.addOnChangeListener((slider, value, fromUser) -> {
            boundaries = (ArrayList<Float>) gradeSliderRS.getValues();

            lower = numToGrade(boundaries.get(0));
            upper = numToGrade(boundaries.get(1));
            gradeRangeTV.setText("Grade range: " + lower + " - " + upper);

            MainActivity.currentFilters.setGradeBoundaries(boundaries);
        });

        //updates the current filters with new checkboxes
        applyFiltersBtn.setOnClickListener(v -> {
            styleIDs = new ArrayList<>();
            readCheckboxes();
            MainActivity.currentFilters.setStyleIDs(styleIDs);
            Toast.makeText(Filters.this, "Filters applied successfully.", Toast.LENGTH_SHORT).show();
        });

        //Btn: goes back to the Bouldering main menu
        boulderingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainBouldering.class);
            v.getContext().startActivity(intent);
        });
    }

    public void setCheckboxesFalse() {
        jugCB.setChecked(false);
        crimpCB.setChecked(false);
        pinchCB.setChecked(false);
        pocketCB.setChecked(false);
        sloperCB.setChecked(false);
        slabCB.setChecked(false);
        overhangCB.setChecked(false);
        areteCB.setChecked(false);
        verticalCB.setChecked(false);
        traverseCB.setChecked(false);
        dynamicCB.setChecked(false);
        fingeryCB.setChecked(false);
        technicalCB.setChecked(false);
        powerfulCB.setChecked(false);
        reachyCB.setChecked(false);
    }

    public void setCheckboxes(ArrayList<Integer> styleIDs) {
        for (Integer i: styleIDs) {
            switch(i) {
                case 1:
                    jugCB.setChecked(true);
                    break;
                case 2:
                    crimpCB.setChecked(true);
                    break;
                case 3:
                    pinchCB.setChecked(true);
                    break;
                case 4:
                    pocketCB.setChecked(true);
                    break;
                case 5:
                    sloperCB.setChecked(true);
                    break;
                case 6:
                    slabCB.setChecked(true);
                    break;
                case 7:
                    overhangCB.setChecked(true);
                    break;
                case 8:
                    areteCB.setChecked(true);
                    break;
                case 9:
                    verticalCB.setChecked(true);
                    break;
                case 10:
                    traverseCB.setChecked(true);
                    break;
                case 11:
                    dynamicCB.setChecked(true);
                    break;
                case 12:
                    fingeryCB.setChecked(true);
                    break;
                case 13:
                    technicalCB.setChecked(true);
                    break;
                case 14:
                    powerfulCB.setChecked(true);
                    break;
                case 15:
                    reachyCB.setChecked(true);
                    break;
            }
        }
    }

    //adds styleIDs to an array based on which checkboxes are ticked
    public void readCheckboxes() {
        if (jugCB.isChecked()){
            styleID = 1;
            styleIDs.add(styleID);
        }
        if (crimpCB.isChecked()) {
            styleID = 2;
            styleIDs.add(styleID);
        }
        if (pinchCB.isChecked()){
            styleID = 3;
            styleIDs.add(styleID);
        }
        if (pocketCB.isChecked()){
            styleID = 4;
            styleIDs.add(styleID);
        }
        if (sloperCB.isChecked()){
            styleID = 5;
            styleIDs.add(styleID);
        }
        if (slabCB.isChecked()){
            styleID = 6;
            styleIDs.add(styleID);
        }
        if (overhangCB.isChecked()){
            styleID = 7;
            styleIDs.add(styleID);
        }
        if (areteCB.isChecked()){
            styleID = 8;
            styleIDs.add(styleID);
        }
        if (verticalCB.isChecked()){
            styleID = 9;
            styleIDs.add(styleID);
        }
        if (traverseCB.isChecked()){
            styleID = 10;
            styleIDs.add(styleID);
        }
        if (dynamicCB.isChecked()){
            styleID = 11;
            styleIDs.add(styleID);
        }
        if (fingeryCB.isChecked()){
            styleID = 12;
            styleIDs.add(styleID);
        }
        if (technicalCB.isChecked()){
            styleID = 13;
            styleIDs.add(styleID);
        }
        if (powerfulCB.isChecked()){
            styleID = 14;
            styleIDs.add(styleID);
        }
        if (reachyCB.isChecked()){
            styleID = 15;
            styleIDs.add(styleID);
        }
    }

    //conversion between String grade and the associated float value on the slider
    public static float gradeToNum(String grade) {
        switch(grade) {
            case "4":
                return 0.0F;
            case "5":
                return 1.0F;
            case "5+":
                return 2.0F;
            case "6a":
                return 3.0F;
            case "6a+":
                return 4.0F;
            case "6b":
                return 5.0F;
            case "6b+":
                return 6.0F;
            case "6c":
                return 7.0F;
            case "6c+":
                return 8.0F;
            case "7a":
                return 9.0F;
            case "7a+":
                return 10.0F;
            case "7b":
                return 11.0F;
            case "7b+":
                return 12.0F;
            case "7c":
                return 13.0F;
            case "7c+":
                return 14.0F;
            case "8a":
                return 15.0F;
            case "8a+":
                return 16.0F;
            case "8b":
                return 17.0F;
            case "8b+":
                return 18.0F;
        }
        return -1.0F;
    }

    public static String numToGrade(float num) {
        switch((int) num) {
            case 0:
                return "4";
            case 1:
                return "5";
            case 2:
                return "5+";
            case 3:
                return "6a";
            case 4:
                return "6a+";
            case 5:
                return "6b";
            case 6:
                return "6b+";
            case 7:
                return "6c";
            case 8:
                return "6c+";
            case 9:
                return "7a";
            case 10:
                return "7a+";
            case 11:
                return "7b";
            case 12:
                return "7b+";
            case 13:
                return "7c";
            case 14:
                return "7c+";
            case 15:
                return "8a";
            case 16:
                return "8a+";
            case 17:
                return "8b";
            case 18:
                return "8b+";
        }
        return "error";
    }
}