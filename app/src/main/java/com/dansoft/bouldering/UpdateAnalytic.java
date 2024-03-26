package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateAnalytic extends AppCompatActivity {

    private EditText userIDEdt, boulderIDEdt, voteEdt;
    private Button updateAnalyticBtn, removeAnalyticBtn;
    private Analytic analytic;
    String userID, boulderID, vote;
    private Analytics analytics = new Analytics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_analytic);

        //initialising UI elements
        userIDEdt = findViewById(R.id.idEdtUserID);
        boulderIDEdt = findViewById(R.id.idEdtBoulderID);
        voteEdt = findViewById(R.id.idEdtVote);

        updateAnalyticBtn = findViewById(R.id.idBtnUpdateAnalytic);
        removeAnalyticBtn = findViewById(R.id.idBtnRemoveAnalytic);

        //gets info from ViewAnalytics to fill out form with current info
        userID = getIntent().getStringExtra("userID");
        boulderID = getIntent().getStringExtra("boulderID");
        vote = getIntent().getStringExtra("vote");

        //if vote is "N/A" field should be set to ""
        if (vote.equals("N/A")) {
            vote = "";
        }

        userIDEdt.setText(userID);
        boulderIDEdt.setText(boulderID);
        voteEdt.setText(vote);

        //takes users inputs from a form and then updates the record
        updateAnalyticBtn.setOnClickListener(v -> {

            // validating if the text fields are empty or not.
            if (userID.isEmpty() || boulderID.isEmpty()) {
                Toast.makeText(UpdateAnalytic.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //other validations
            if (!Validations.typeCheckNumbers(userID) || !Validations.typeCheckNumbers(boulderID)) {
                userIDEdt.setText("");
                Toast.makeText(UpdateAnalytic.this, "IDs must be numbers.", Toast.LENGTH_SHORT).show();
                return;
            }

            //if user doesn't enter a vote, it is set to "N/A"
            if (voteEdt.getText().toString().isEmpty()) {
                vote = "N/A";
            }
            if (!Validations.gradeCheck(vote)) {
                voteEdt.setText("");
                Toast.makeText(UpdateAnalytic.this, "Vote entered is invalid.", Toast.LENGTH_SHORT).show();
                return;
            }


            analytic = new Analytic(Integer.parseInt(userIDEdt.getText().toString()),
                    Integer.parseInt(boulderIDEdt.getText().toString()), vote);

            String errorMessage = analytics.updateAnalytic(Integer.parseInt(userID), Integer.parseInt(boulderID), analytic);
            Toast.makeText(UpdateAnalytic.this, errorMessage, Toast.LENGTH_SHORT).show();

            if (errorMessage.equals(Analytics.ERROR_USER_NOT_EXIST)) {
                userIDEdt.setText("");
            } else if (errorMessage.equals(Analytics.ERROR_BOULDER_NOT_EXIST)) {
                boulderIDEdt.setText("");
            } else if (errorMessage.equals(Analytics.ERROR_NOT_UNIQUE)) {
                userIDEdt.setText("");
                boulderIDEdt.setText("");
            } else {
                Intent i = new Intent(UpdateAnalytic.this, CreateAnalytic.class);
                startActivity(i);
            }
        });

        //functionality of removeAnalytic button
        removeAnalyticBtn.setOnClickListener(v -> {
            analytic = MainActivity.dbHandler.fetchAnalyticByIDs(Integer.parseInt(userID), Integer.parseInt(boulderID));
            analytics.removeAnalytic(analytic);

            Toast.makeText(UpdateAnalytic.this, "Analytic removed", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UpdateAnalytic.this, CreateAnalytic.class);
            startActivity(i);
        });
    }
}