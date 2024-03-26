package com.dansoft.bouldering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CreateBoulder extends AppCompatActivity {
    private EditText gradeEdt, posXEdt, posYEdt, colourEdt;
    private Button addBoulderBtn, readBouldersBtn;
    private ImageButton adminSelectionBtn;
    private CheckBox jugCB, crimpCB, pinchCB, pocketCB, sloperCB,
            slabCB, overhangCB, areteCB, verticalCB, traverseCB,
            dynamicCB, fingeryCB, technicalCB, powerfulCB, reachyCB;
    private ScrollView createBoulderScroll;

    private ArrayList<Integer> styleIDs = new ArrayList<>();
    private Integer styleID;
    private Boulder boulder;
    private Boulders boulders = new Boulders();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializes all UI elements (buttons, forms)
        setContentView(R.layout.activity_create_boulder);
        gradeEdt = findViewById(R.id.idEdtGrade);
        posXEdt = findViewById(R.id.idEdtPosX);
        posYEdt = findViewById(R.id.idEdtPosY);
        colourEdt = findViewById(R.id.idEdtColour);

        addBoulderBtn = findViewById(R.id.idBtnAddBoulder);
        readBouldersBtn = findViewById(R.id.idBtnReadBoulders);
        adminSelectionBtn = findViewById(R.id.idBtnAdminSelection);

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
        createBoulderScroll = findViewById(R.id.idCreateBoulderScroll);

        //functionality of addBoulder button
        //takes inputs from forms and uses them to add a record to the database
        addBoulderBtn.setOnClickListener(v -> {

            String grade = gradeEdt.getText().toString();
            String posX = posXEdt.getText().toString();
            String posY = posYEdt.getText().toString();
            String colour = colourEdt.getText().toString();

            readCheckboxes();

            //validating if the text fields are empty or not.
            if (grade.isEmpty() || posX.isEmpty() || posY.isEmpty() || colour.isEmpty()) {
                Toast.makeText(CreateBoulder.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //other validations
            if (!Validations.gradeCheck(grade)) {
                gradeEdt.setText("");
                Toast.makeText(CreateBoulder.this, "Invalid grade.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.typeCheckNumbers(posX) || !Validations.typeCheckNumbers(posY)) {
                posXEdt.setText("");
                posYEdt.setText("");
                Toast.makeText(CreateBoulder.this, "Coordinates must be numbers.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.rangeCheck(Integer.parseInt(posX), 0, 950)) {
                posXEdt.setText("");
                Toast.makeText(CreateBoulder.this, "Invalid posX.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.rangeCheck(Integer.parseInt(posY), 0, 1440)){
                posYEdt.setText("");
                Toast.makeText(CreateBoulder.this, "Invalid posY.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.colourCheck(colour)) {
                colourEdt.setText("");
                Toast.makeText(CreateBoulder.this, "Invalid colour.", Toast.LENGTH_SHORT).show();
                return;
            }

            boulder = new Boulder(grade, Integer.parseInt(posX), Integer.parseInt(posY), colour, styleIDs);
            boulders.addBoulder(boulder);

            Toast.makeText(CreateBoulder.this, "Boulder has been added.", Toast.LENGTH_SHORT).show();
            gradeEdt.setText("");
            posXEdt.setText("");
            posYEdt.setText("");
            colourEdt.setText("");

            createBoulderScroll.fullScroll(ScrollView.FOCUS_UP);
            setCheckboxesFalse();
            styleIDs = new ArrayList<>();
        });

        //Btn that navigates to ViewBoulders activity
        readBouldersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ViewBoulders.class);
            v.getContext().startActivity(intent);
        });

        //go back button
        adminSelectionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AdminSelection.class);
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
}