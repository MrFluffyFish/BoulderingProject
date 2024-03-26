package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText usernameEdt, passwordEdt;
    private Button loginBtn, makeAccountBtn;
    private ImageButton mainMenuBtn;
    private Users users = new Users();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialising UI elements
        usernameEdt = findViewById(R.id.idEdtUsername);
        passwordEdt = findViewById(R.id.idEdtPassword);

        mainMenuBtn = findViewById(R.id.idBtnMainMenu);
        loginBtn = findViewById(R.id.idBtnLogin);
        makeAccountBtn = findViewById(R.id.idBtnMakeAccount);

        loginBtn.setOnClickListener(v -> {
            String username = usernameEdt.getText().toString();
            String password = passwordEdt.getText().toString();

            // validating if the text fields are empty or not.
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //attempts to login the user, outputting an appropriate message
            if (users.attemptLogin(username, password)) {
                //sets a global variable for which userID is logged in
                user = MainActivity.dbHandler.fetchUserByUsername(username);
                MainActivity.loggedIn.setLoggedInUserID(user.getUserID());

                Toast.makeText(Login.this, "You are logged in.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            } else {
                Toast.makeText(Login.this, "Invalid username and password.", Toast.LENGTH_SHORT).show();
                usernameEdt.setText("");
                passwordEdt.setText("");
            }
        });

        //Btn: goes back to the main menu
        mainMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);
        });

        //Btn: starts the MakeAccount activity
        makeAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MakeAccount.class);
            v.getContext().startActivity(intent);
        });
    }
}