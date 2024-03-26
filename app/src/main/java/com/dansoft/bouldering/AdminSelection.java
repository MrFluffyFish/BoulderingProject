package com.dansoft.bouldering;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

//admin selection menu
public class AdminSelection extends AppCompatActivity {

    private Button usersBtn, bouldersBtn, bookingsBtn, analyticsBtn;
    private ImageButton mainMenuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_selection);

        //initialising UI elements
        usersBtn = findViewById(R.id.idBtnUsers);
        bouldersBtn = findViewById(R.id.idBtnBoulders);
        bookingsBtn = findViewById(R.id.idBtnBookings);
        analyticsBtn = findViewById(R.id.idBtnAnalytics);
        mainMenuBtn = findViewById(R.id.idBtnMainMenu);

        //usersBtn: starts CreateUser activity
        usersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CreateUser.class);
            v.getContext().startActivity(intent);
        });

        //bouldersBtn: starts CreateBoulder activity
        bouldersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CreateBoulder.class);
            v.getContext().startActivity(intent);
        });

        //bookingsBtn: starts CreateBooking activity
        bookingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CreateBooking.class);
            v.getContext().startActivity(intent);
        });

        //analyticsBtn: starts CreateAnalytic activity
        analyticsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CreateAnalytic.class);
            v.getContext().startActivity(intent);
        });

        //mainMenuBtn: navigates to the main menu
        mainMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);
        });
    }
}
