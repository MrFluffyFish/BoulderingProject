package com.dansoft.bouldering;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//uses a UserRVAdapter to populate recycleView with user_rv_items
public class ViewUsers extends AppCompatActivity {
    private ArrayList<User> usersArray;
    private UserRVAdapter userRVAdapter;
    private RecyclerView usersRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        usersArray = MainActivity.dbHandler.readUsers();

        userRVAdapter = new UserRVAdapter(usersArray, ViewUsers.this);
        usersRV = findViewById(R.id.idRVUsers);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewUsers.this, RecyclerView.VERTICAL, false);
        usersRV.setLayoutManager(linearLayoutManager);

        usersRV.setAdapter(userRVAdapter);
    }
}