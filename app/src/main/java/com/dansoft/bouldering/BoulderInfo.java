package com.dansoft.bouldering;

import static java.util.Map.entry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BoulderInfo extends AppCompatActivity {

    //useful variables
    private LinearLayout boulderTopLL;
    private TextView gradeTV, stylesTV;
    private ImageButton boulderingMainBtn, completeBoulderBtn;
    private Button submitVoteBtn;
    private Spinner voteSpinner;
    private String selectedVote;
    private Boulder boulder;
    private int userID = MainActivity.loggedIn.getLoggedInUserID();
    private Analytic analytic;
    private Analytic oldAnalytic;
    private Analytics analytics = new Analytics();
    private boolean isCompleted;

    //bar chart variables
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntriesArray;
    ArrayList<String> xLabel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boulder_info);

        //initialising UI elements
        boulderTopLL = findViewById(R.id.idLLBoulderTop);
        gradeTV = findViewById(R.id.idTVGrade);
        stylesTV = findViewById(R.id.idTVStyles);
        boulderingMainBtn = findViewById(R.id.idBtnBoulderingMain);
        completeBoulderBtn = findViewById(R.id.idBtnCompleteBoulder);
        submitVoteBtn = findViewById(R.id.idBtnSubmitVote);
        voteSpinner = findViewById(R.id.voteSpinner);
        barChart = findViewById(R.id.idBarChart);

        //setting up spinner with values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.votes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voteSpinner.setAdapter(adapter);

        //fetching information from previous activity
        String boulderID = getIntent().getStringExtra("boulderID");
        boulder = MainActivity.dbHandler.fetchBoulderByID(Integer.parseInt(boulderID));
        isCompleted = MainActivity.dbHandler.isAnalyticExist(userID, boulder.getBoulderID());

        //sets the vote entry to their current vote should they wish to change it
        if (isCompleted) {
            oldAnalytic = MainActivity.dbHandler.fetchAnalyticByIDs(userID, boulder.getBoulderID());
            String vote = oldAnalytic.getVote();

            //voteEdt should be empty if vote was N/A
            if (vote.equals("N/A")) {
                vote = "Select a vote";
            }

            ArrayList<String> votes = new ArrayList<>(Arrays.asList("Select a vote","4","5","5+","6a","6a+","6b","6b+","6c","6c+","7a","7a+","7b","7b+","7c","7c+","8a","8a+","8b","8b+"));
            voteSpinner.setSelection(votes.indexOf(vote));
        }

        //sets the colour of boulderTop
        switch(boulder.getColour()) {
            case "black":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderBlack));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderBlack));
                break;
            case "red":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderRed));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderRed));
                break;
            case "orange":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderOrange));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderOrange));
                break;
            case "yellow":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderYellow));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderYellow));
                break;
            case "purple":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderPurple));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderPurple));
                break;
            case "blue":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderBlue));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderBlue));
                break;
            case "green":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderGreen));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderGreen));
                break;
            case "brown":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderBrown));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderBrown));
                break;
            case "pink":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderPink));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderPink));
                break;
            case "white":
                boulderTopLL.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderWhite));
                boulderingMainBtn.setBackgroundTintList(ContextCompat.getColorStateList(BoulderInfo.this, R.color.boulderWhite));
                gradeTV.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.boulderBlack));
                //setting X to black
                boulderingMainBtn.setImageDrawable(AppCompatResources.getDrawable(BoulderInfo.this, R.drawable.blackx));
                break;
        }

        //sets the text of gradeTV to the grade of the boulder
        //if the boulder is completed, this is also added at the end of gradeTV
        if (isCompleted) {
            gradeTV.setText(boulder.getGrade() + " (completed)");
        } else {
            gradeTV.setText(boulder.getGrade());
        }

        //outputs the styleDescriptions
        ArrayList<String> styleDescriptions = MainActivity.dbHandler.fetchStyleDescriptions(boulder.getStyleIDs());
        stylesTV.setText(styleDescriptions.toString());

        //completeBoulderBtn: allows the user to complete a boulder
        //adds an analytic record to the database
        completeBoulderBtn.setOnClickListener(v -> {

            //the user should not be able to complete a boulder if they are not logged in
            if (MainActivity.loggedIn.getLoggedInUserID() == 0) {
                Toast.makeText(BoulderInfo.this, "You must login to use this feature.", Toast.LENGTH_SHORT).show();
                return;
            }

            analytic = new Analytic(userID, boulder.getBoulderID(), "N/A");

            //attempts to add analytic, returning an appropriate message
            String errorMessage = analytics.addAnalytic(analytic);
            if (errorMessage.equals(Analytics.ERROR_NOT_UNIQUE)) {
                errorMessage = "You have already completed this boulder.";
            } else {
                isCompleted = true;
                gradeTV.setText(boulder.getGrade() + " (completed)");
                errorMessage = "Boulder has been completed!";
            }
            Toast.makeText(BoulderInfo.this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        voteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(24);
                selectedVote = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Btn: allows the user to submit a vote
        //updates their analytic record with this vote
        submitVoteBtn.setOnClickListener(v -> {

            //validating if the vote field is empty or not.
            if (selectedVote.equals("Select a vote")) {
                Toast.makeText(BoulderInfo.this, "Please select a vote.", Toast.LENGTH_SHORT).show();
                return;
            }

            analytic = new Analytic(userID, Integer.parseInt(boulderID), selectedVote);

            //attempts to update analytic record with the vote, returning appropriate messages
            if (!isCompleted) {
                Toast.makeText(BoulderInfo.this, "You must complete the boulder before you can vote.", Toast.LENGTH_SHORT).show();
                return;
            }

            oldAnalytic = MainActivity.dbHandler.fetchAnalyticByIDs(analytic.getUserID(), analytic.getBoulderID());
            if (selectedVote.equals(oldAnalytic.getVote())) {
                Toast.makeText(BoulderInfo.this, "You have already submitted this vote", Toast.LENGTH_SHORT).show();
                return;
            }
            analytics.updateAnalytic(userID, Integer.parseInt(boulderID), analytic);
            Toast.makeText(BoulderInfo.this, "Your vote has been submitted", Toast.LENGTH_SHORT).show();
        });

        //Btn: goes back to the bouldering main menu
        boulderingMainBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainBouldering.class);
            v.getContext().startActivity(intent);
        });

        //bar chart stuff
        getBarEntries();
        barDataSet = new BarDataSet(barEntriesArray, "Votes");
        barData = new BarData(barDataSet);
        barChart.setData(barData);

        // adding color to the bar data set
        switch(boulder.getColour()) {
            case "black":
                barDataSet.setColor(getColor(R.color.boulderBlack));
                break;
            case "red":
                barDataSet.setColor(getColor(R.color.boulderRed));
                break;
            case "orange":
                barDataSet.setColor(getColor(R.color.boulderOrange));
                break;
            case "yellow":
                barDataSet.setColor(getColor(R.color.boulderYellow));
                break;
            case "purple":
                barDataSet.setColor(getColor(R.color.boulderPurple));
                break;
            case "blue":
                barDataSet.setColor(getColor(R.color.boulderBlue));
                break;
            case "green":
                barDataSet.setColor(getColor(R.color.boulderGreen));
                break;
            case "brown":
                barDataSet.setColor(getColor(R.color.boulderBrown));
                break;
            case "pink":
                barDataSet.setColor(getColor(R.color.boulderPink));
                break;
            case "white":
                barDataSet.setColor(getColor(R.color.boulderWhite));
                break;
        }

        // setting text color
        barDataSet.setValueTextColor(getColor(R.color.boulderWhite));

        //useful settings
        barDataSet.setValueTextSize(16f);
        barChart.animateY(1000);
        barChart.setFitBars(true);

        // disabling labels and touch control
        barChart.getDescription().setEnabled(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getLegend().setEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);

        //formatting axis
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setAxisMinimum(0);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        xAxis.setTextColor(getColor(R.color.boulderWhite));

        //formats floats on the x-axis into grade labels
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xLabel.get((int)value);
            }
        });

        //sets the float values atop the bars to integers
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
    }

    private void getBarEntries() {
        barEntriesArray = new ArrayList<>();

        //fetches all the votes for the boulder
        ArrayList<String> votes = MainActivity.dbHandler.fetchVotesByBoulderID(boulder.getBoulderID());

        //initialises a HashMap where each grade has 0 votes
        LinkedHashMap<String, Integer> votesTally = new LinkedHashMap<>();
        for (String grade: Arrays.asList("4","5","5+","6a","6a+","6b","6b+","6c","6c+","7a","7a+","7b","7b+","7c","7c+","8a","8a+","8b","8b+")) {
            votesTally.put(grade, 0);
        }

        //adds the number of votes for each grade to the HashMap
        int tempTally;
        for (String v: votes) {
            switch (v) {
                case "4":
                    tempTally = votesTally.get("4");
                    votesTally.put("4", tempTally+1);
                    break;
                case "5":
                    tempTally = votesTally.get("5");
                    votesTally.put("5", tempTally+1);
                    break;
                case "5+":
                    tempTally = votesTally.get("5+");
                    votesTally.put("5+", tempTally+1);
                    break;
                case "6a":
                    tempTally = votesTally.get("6a");
                    votesTally.put("6a", tempTally+1);
                    break;
                case "6a+":
                    tempTally = votesTally.get("6a+");
                    votesTally.put("6a+", tempTally+1);
                    break;
                case "6b":
                    tempTally = votesTally.get("6b");
                    votesTally.put("6b", tempTally+1);
                    break;
                case "6b+":
                    tempTally = votesTally.get("6b+");
                    votesTally.put("6b+", tempTally+1);
                    break;
                case "6c":
                    tempTally = votesTally.get("6c");
                    votesTally.put("6c", tempTally+1);
                    break;
                case "6c+":
                    tempTally = votesTally.get("6c+");
                    votesTally.put("6c+", tempTally+1);
                    break;
                case "7a":
                    tempTally = votesTally.get("7a");
                    votesTally.put("7a", tempTally+1);
                    break;
                case "7a+":
                    tempTally = votesTally.get("7a+");
                    votesTally.put("7a+", tempTally+1);
                    break;
                case "7b":
                    tempTally = votesTally.get("7b");
                    votesTally.put("7b", tempTally+1);
                    break;
                case "7b+":
                    tempTally = votesTally.get("7b+");
                    votesTally.put("7b+", tempTally+1);
                    break;
                case "7c":
                    tempTally = votesTally.get("7c");
                    votesTally.put("7c", tempTally+1);
                    break;
                case "7c+":
                    tempTally = votesTally.get("7c+");
                    votesTally.put("7c+", tempTally+1);
                    break;
                case "8a":
                    tempTally = votesTally.get("8a");
                    votesTally.put("8a", tempTally+1);
                    break;
                case "8a+":
                    tempTally = votesTally.get("8a+");
                    votesTally.put("8a+", tempTally+1);
                    break;
                case "8b":
                    tempTally = votesTally.get("8b");
                    votesTally.put("8b", tempTally+1);
                    break;
                case "8b+":
                    tempTally = votesTally.get("8b+");
                    votesTally.put("8b+", tempTally+1);
                    break;
            }
        }

        //creates a bar for each grade, height = number of votes
        float x = 0f;
        for (Map.Entry<String, Integer> entry: votesTally.entrySet()) {
            if (entry.getValue() >= 1) {
                xLabel.add(entry.getKey());
                barEntriesArray.add(new BarEntry(x, entry.getValue()));
                x+=1;
            }
        }
    }
}