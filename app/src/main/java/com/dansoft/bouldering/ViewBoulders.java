package com.dansoft.bouldering;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//uses a BoulderRVAdapter to populate recycleView with boulder_rv_items
public class ViewBoulders extends AppCompatActivity {
    private ArrayList<Boulder> bouldersArray;
    private BoulderRVAdapter boulderRVAdapter;
    private RecyclerView bouldersRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_boulders);

        bouldersArray = MainActivity.dbHandler.readBoulders();

        boulderRVAdapter = new BoulderRVAdapter(bouldersArray, ViewBoulders.this);
        bouldersRV = findViewById(R.id.idRVBoulders);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewBoulders.this, RecyclerView.VERTICAL, false);
        bouldersRV.setLayoutManager(linearLayoutManager);

        bouldersRV.setAdapter(boulderRVAdapter);
    }
}