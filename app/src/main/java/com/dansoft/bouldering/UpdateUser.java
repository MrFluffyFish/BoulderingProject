package com.dansoft.bouldering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class UpdateUser extends AppCompatActivity {

    private EditText firstNameEdt, surnameEdt, dateOfBirthEdt, emailEdt, mobileNumberEdt, usernameEdt, passwordEdt;
    private Button updateUserBtn, removeUserBtn;
    String userID, firstName, surname, dateOfBirth, email, mobileNumber, username, password;
    private User user;
    private Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        //initialising UI elements
        firstNameEdt = findViewById(R.id.idEdtFirstName);
        surnameEdt = findViewById(R.id.idEdtSurname);
        dateOfBirthEdt = findViewById(R.id.idEdtDateOfBirth);
        emailEdt = findViewById(R.id.idEdtEmail);
        mobileNumberEdt = findViewById(R.id.idEdtMobileNumber);
        usernameEdt = findViewById(R.id.idEdtUsername);
        passwordEdt = findViewById(R.id.idEdtPassword);
        updateUserBtn = findViewById(R.id.idBtnUpdateUser);
        removeUserBtn = findViewById(R.id.idBtnRemoveUser);

        //gets info from ViewUsers to fill in form with current info
        userID = getIntent().getStringExtra("userID");
        firstName = getIntent().getStringExtra("firstName");
        surname = getIntent().getStringExtra("surname");
        dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        email = getIntent().getStringExtra("email");
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        firstNameEdt.setText(firstName);
        surnameEdt.setText(surname);
        dateOfBirthEdt.setText(dateOfBirth);
        emailEdt.setText(email);
        mobileNumberEdt.setText(mobileNumber);
        usernameEdt.setText(username);
        passwordEdt.setText(password);

        //takes user input from form to update a user record
        updateUserBtn.setOnClickListener(v -> {
            // validating if the text fields are empty or not.
            if (firstName.isEmpty() || surname.isEmpty() || dateOfBirth.isEmpty() || email.isEmpty() || mobileNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(UpdateUser.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //other validations
            if (!Validations.lengthCheckString(firstName, 16)) {
                firstNameEdt.setText("");
                Toast.makeText(UpdateUser.this, "First name is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(surname, 16)) {
                surnameEdt.setText("");
                Toast.makeText(UpdateUser.this, "Surname is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(username, 16)) {
                usernameEdt.setText("");
                Toast.makeText(UpdateUser.this, "Username is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(password, 16)) {
                passwordEdt.setText("");
                Toast.makeText(UpdateUser.this, "Password is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(email, 32)) {
                emailEdt.setText("");
                Toast.makeText(UpdateUser.this, "Email is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.typeCheckName(firstName) || (!Validations.typeCheckName(surname))) {
                firstNameEdt.setText("");
                surnameEdt.setText("");
                Toast.makeText(UpdateUser.this, "Name contains invalid characters.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.emailFormatCheck(email)) {
                emailEdt.setText("");
                Toast.makeText(UpdateUser.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.typeCheckNumbers(mobileNumber)) {
                mobileNumberEdt.setText("");
                Toast.makeText(UpdateUser.this, "Mobile number must only contain numbers.", Toast.LENGTH_SHORT).show();
                return;
            }
            //sfesfes
            if (!Validations.validCharsCheck(password)) {
                mobileNumberEdt.setText("");
                Toast.makeText(UpdateUser.this, "Mobile number must only contain numbers.", Toast.LENGTH_SHORT).show();
                return;
            }

            user = new User(firstNameEdt.getText().toString(), surnameEdt.getText().toString(), dateOfBirthEdt.getText().toString(),
                    emailEdt.getText().toString(), mobileNumberEdt.getText().toString(), usernameEdt.getText().toString(), passwordEdt.getText().toString());
            user.setUserID(Integer.parseInt(userID));

            //attempts to update user, outputting an appropriate error message
            String errorMessage = users.updateUser(user);
            Toast.makeText(UpdateUser.this, errorMessage, Toast.LENGTH_SHORT).show();

            if (errorMessage.equals(Users.ERROR_UNIQUE_USERNAME)) {
                usernameEdt.setText("");
            } else if (errorMessage.equals("Email is not unique.")) {
                emailEdt.setText("");
            } else {
                Intent i = new Intent(UpdateUser.this, CreateUser.class);
                startActivity(i);
            }
        });

        //functionality of removeUser button
        removeUserBtn.setOnClickListener(v -> {
            user = MainActivity.dbHandler.fetchUserByID(Integer.parseInt(userID));
            users.removeUser(user);

            Toast.makeText(UpdateUser.this, "User removed", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UpdateUser.this, CreateUser.class);
            startActivity(i);
        });
    }
}