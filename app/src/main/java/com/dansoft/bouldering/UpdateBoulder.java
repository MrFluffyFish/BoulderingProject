package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class UpdateBoulder extends AppCompatActivity {

    private EditText gradeEdt, posXEdt, posYEdt, colourEdt;
    private Button updateBoulderBtn, removeBoulderBtn;
    private CheckBox jugCB, crimpCB, pinchCB, pocketCB, sloperCB,
            slabCB, overhangCB, areteCB, verticalCB, traverseCB,
            dynamicCB, fingeryCB, technicalCB, powerfulCB, reachyCB;
    private String boulderID, grade, posX, posY, colour;
    private ArrayList<Integer> oldStyleIDs;
    private ArrayList<Integer> styleIDs = new ArrayList<>();
    private Integer styleID;
    private Boulder boulder;
    private Boulders boulders = new Boulders();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_boulder);

        //initialising UI elements
        gradeEdt = findViewById(R.id.idEdtGrade);
        posXEdt = findViewById(R.id.idEdtPosX);
        posYEdt = findViewById(R.id.idEdtPosY);
        colourEdt = findViewById(R.id.idEdtColour);

        updateBoulderBtn = findViewById(R.id.idBtnUpdateBoulder);
        removeBoulderBtn = findViewById(R.id.idBtnRemoveBoulder);

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

        //gets info from ViewBoulders to fill out form with current info
        boulderID = getIntent().getStringExtra("boulderID");
        grade = getIntent().getStringExtra("grade");
        posX = getIntent().getStringExtra("posX");
        posY = getIntent().getStringExtra("posY");
        colour = getIntent().getStringExtra("colour");
        oldStyleIDs = getIntent().getIntegerArrayListExtra("styleIDs");

        gradeEdt.setText(grade);
        posXEdt.setText(posX);
        posYEdt.setText(posY);
        colourEdt.setText(colour);

        //iterates through through the styleIDs of the retrieved boulder and sets the appropriates checkboxes to true
        for (Integer i: oldStyleIDs) {
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

        //takes user input from forms to update a boulder record
        updateBoulderBtn.setOnClickListener(v -> {

            //validating if the text fields are empty or not.
            if (grade.isEmpty() || posX.isEmpty() || posY.isEmpty() || colour.isEmpty()) {
                Toast.makeText(UpdateBoulder.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //other validations
            if (!Validations.gradeCheck(grade)) {
                gradeEdt.setText("");
                Toast.makeText(UpdateBoulder.this, "Invalid grade.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.typeCheckNumbers(posX) || !Validations.typeCheckNumbers(posY)) {
                posXEdt.setText("");
                posYEdt.setText("");
                Toast.makeText(UpdateBoulder.this, "Coordinates must be numbers.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.rangeCheck(Integer.parseInt(posX), 0, 950)) {
                posXEdt.setText("");
                Toast.makeText(UpdateBoulder.this, "Invalid posX.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.rangeCheck(Integer.parseInt(posY), 0, 1440)){
                posYEdt.setText("");
                Toast.makeText(UpdateBoulder.this, "Invalid posY.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.colourCheck(colour)) {
                colourEdt.setText("");
                Toast.makeText(UpdateBoulder.this, "Invalid colour.", Toast.LENGTH_SHORT).show();
                return;
            }

            readCheckboxes();

            boulder = new Boulder(gradeEdt.getText().toString(), Integer.parseInt(posXEdt.getText().toString()),
                    Integer.parseInt(posYEdt.getText().toString()), colourEdt.getText().toString(), styleIDs);
            boulder.setBoulderID(Integer.parseInt(boulderID));

            boulders.updateBoulder(boulder);
            Toast.makeText(UpdateBoulder.this, "Boulder Updated.", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(UpdateBoulder.this, CreateBoulder.class);
            startActivity(i);
        });

        //functionality of removeBoulder button
        removeBoulderBtn.setOnClickListener(v -> {
            boulder = MainActivity.dbHandler.fetchBoulderByID(Integer.parseInt(boulderID));
            boulders.removeBoulder(boulder);

            Toast.makeText(UpdateBoulder.this, "Boulder removed", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UpdateBoulder.this, CreateBoulder.class);
            startActivity(i);
        });
    }

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