package com.dansoft.bouldering;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//uses analyticRVAdapter to populate a recycleView with analytic_rv_items
public class ViewAnalytics extends AppCompatActivity {

    private ArrayList<Analytic> analyticsArray;
    private AnalyticRVAdapter analyticRVAdapter;
    private RecyclerView analyticsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_analytics);

        analyticsArray = MainActivity.dbHandler.readAnalytics();

        analyticRVAdapter = new AnalyticRVAdapter(analyticsArray, ViewAnalytics.this);
        analyticsRV = findViewById(R.id.idRVAnalytics);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewAnalytics.this, RecyclerView.VERTICAL, false);
        analyticsRV.setLayoutManager(linearLayoutManager);

        analyticsRV.setAdapter(analyticRVAdapter);
    }
}