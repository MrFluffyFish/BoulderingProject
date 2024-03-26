package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.SharedValues;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateAnalytic extends AppCompatActivity {

    private EditText userIDEdt, boulderIDEdt, voteEdt;
    private Button addAnalyticBtn, readAnalyticsBtn;
    private ImageButton adminSelectionBtn;
    private Analytic analytic;
    private Analytics analytics = new Analytics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_analytic);

        //initialising UI elements
        userIDEdt = findViewById(R.id.idEdtUserID);
        boulderIDEdt = findViewById(R.id.idEdtBoulderID);
        voteEdt = findViewById(R.id.idEdtVote);

        addAnalyticBtn = findViewById(R.id.idBtnAddAnalytic);
        readAnalyticsBtn = findViewById(R.id.idBtnReadAnalytics);
        adminSelectionBtn = findViewById(R.id.idBtnAdminSelection);

        //functionality of addUser button
        //takes inputs from forms and uses them to add a record to the database
        addAnalyticBtn.setOnClickListener(v -> {

            String userID = userIDEdt.getText().toString();
            String boulderID = boulderIDEdt.getText().toString();
            String vote = voteEdt.getText().toString();

            // validating if the text fields are empty or not.
            if (userID.isEmpty() || boulderID.isEmpty()) {
                Toast.makeText(CreateAnalytic.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //other validations
            if (!Validations.typeCheckNumbers(userID) || !Validations.typeCheckNumbers(boulderID)) {
                userIDEdt.setText("");
                Toast.makeText(CreateAnalytic.this, "IDs must be numbers.", Toast.LENGTH_SHORT).show();
                return;
            }

            //sets vote to N/A if the don't enter a vote
            if (vote.isEmpty()) {
                vote = "N/A";
            }
            if (!Validations.gradeCheck(vote)) {
                voteEdt.setText("");
                Toast.makeText(CreateAnalytic.this, "Vote entered is invalid.", Toast.LENGTH_SHORT).show();
                return;
            }

            analytic = new Analytic(Integer.parseInt(userID), Integer.parseInt(boulderID), vote);

            //attempts to add analytic, returning an appropriate message
            String errorMessage = analytics.addAnalytic(analytic);
            Toast.makeText(CreateAnalytic.this, errorMessage, Toast.LENGTH_SHORT).show();

            if (errorMessage.equals(Analytics.ERROR_USER_NOT_EXIST)) {
                userIDEdt.setText("");
            } else if (errorMessage.equals(Analytics.ERROR_BOULDER_NOT_EXIST)) {
                boulderIDEdt.setText("");
            } else if (errorMessage.equals(Analytics.ERROR_NOT_UNIQUE)) {
                userIDEdt.setText("");
                boulderIDEdt.setText("");
            } else {
                userIDEdt.setText("");
                boulderIDEdt.setText("");
                voteEdt.setText("");
            }
        });

        //btn to start ViewAnalytics class
        readAnalyticsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ViewAnalytics.class);
            v.getContext().startActivity(intent);
        });

        //go back a page button
        adminSelectionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AdminSelection.class);
            v.getContext().startActivity(intent);
        });
    }
}