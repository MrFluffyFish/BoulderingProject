package com.dansoft.bouldering;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static DBHandler dbHandler;
    public static LoggedIn loggedIn = new LoggedIn();
    public static CurrentFilters currentFilters = new CurrentFilters();
    private Button adminBtn, boulderingBtn, bookingBtn, loginPageBtn;
    private ImageButton exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialising the DBHandler to be used throughout the project
        dbHandler = new DBHandler(MainActivity.this);

        //initialising UI elements
        adminBtn = findViewById(R.id.idBtnAdmin);
        boulderingBtn = findViewById(R.id.idBtnBouldering);
        bookingBtn = findViewById(R.id.idBtnBooking);
        loginPageBtn = findViewById(R.id.idBtnLoginPage);
        exitBtn = findViewById(R.id.idBtnExit);

        if (MainActivity.loggedIn.getLoggedInUserID() != 0) {
            loginPageBtn.setText("Logout");
        }

        //functionality of adminButtton: starts AdminSelection activity
        adminBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AdminSelection.class);
            v.getContext().startActivity(intent);
        });

        //functionality of boulderingBtn: starts MainBouldering activity
        boulderingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainBouldering.class);
            v.getContext().startActivity(intent);
        });

        //functionality of bookingBtn: starts MainBooking activity
        bookingBtn.setOnClickListener(v -> {
            if (MainActivity.loggedIn.getLoggedInUserID() == 0) {
                Toast.makeText(MainActivity.this, "You must login to use this feature.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(v.getContext(), MainBooking.class);
                v.getContext().startActivity(intent);
            }
        });

        //functionality of loginPageBtn: starts Login activity or logs the user out
        loginPageBtn.setOnClickListener(v -> {
            if (loginPageBtn.getText().equals("Logout")) {
                MainActivity.loggedIn.setLoggedInUserID(0);
                loginPageBtn.setText("Login");
                Toast.makeText(MainActivity.this, "You have been logged out.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(v.getContext(), Login.class);
                v.getContext().startActivity(intent);
            }
        });

        //functionality of exitBtn: probably dodgy
        exitBtn.setOnClickListener(v -> {
        });
    }
}

