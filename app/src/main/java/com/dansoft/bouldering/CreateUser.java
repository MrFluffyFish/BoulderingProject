package com.dansoft.bouldering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateUser extends AppCompatActivity {

    private EditText firstNameEdt, surnameEdt, dateOfBirthEdt, emailEdt, mobileNumberEdt, usernameEdt, passwordEdt;
    private Button addUserBtn, readUsersBtn;
    private ImageButton adminSelectionBtn;
    private User user;
    private Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        //initializes all UI elements (buttons, forms)
        firstNameEdt = findViewById(R.id.idEdtFirstName);
        surnameEdt = findViewById(R.id.idEdtSurname);
        dateOfBirthEdt = findViewById(R.id.idEdtDateOfBirth);
        emailEdt = findViewById(R.id.idEdtEmail);
        mobileNumberEdt = findViewById(R.id.idEdtMobileNumber);
        usernameEdt = findViewById(R.id.idEdtUsername);
        passwordEdt = findViewById(R.id.idEdtPassword);

        addUserBtn = findViewById(R.id.idBtnAddUser);
        readUsersBtn = findViewById(R.id.idBtnReadUsers);
        adminSelectionBtn = findViewById(R.id.idBtnAdminSelection);

        //functionality of addUser button
        //takes inputs from forms and uses them to add a record to the database
        addUserBtn.setOnClickListener(v -> {

            String firstName = firstNameEdt.getText().toString();
            String surname = surnameEdt.getText().toString();
            String dateOfBirth = dateOfBirthEdt.getText().toString();
            String email = emailEdt.getText().toString();
            String mobileNumber = mobileNumberEdt.getText().toString();
            String username = usernameEdt.getText().toString();
            String password = passwordEdt.getText().toString();

            // validating if the text fields are empty or not.
            if (firstName.isEmpty() || surname.isEmpty() || dateOfBirth.isEmpty() || email.isEmpty() || mobileNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(CreateUser.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //other validations
            if (!Validations.lengthCheckString(firstName, 16)) {
                firstNameEdt.setText("");
                Toast.makeText(CreateUser.this, "First name is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(surname, 16)) {
                surnameEdt.setText("");
                Toast.makeText(CreateUser.this, "Surname is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(username, 16)) {
                usernameEdt.setText("");
                Toast.makeText(CreateUser.this, "Username is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(password, 16)) {
                passwordEdt.setText("");
                Toast.makeText(CreateUser.this, "Password is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(email, 32)) {
                emailEdt.setText("");
                Toast.makeText(CreateUser.this, "Email is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.typeCheckName(firstName) || (!Validations.typeCheckName(surname))) {
                firstNameEdt.setText("");
                surnameEdt.setText("");
                Toast.makeText(CreateUser.this, "Name contains invalid characters.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.emailFormatCheck(email)) {
                emailEdt.setText("");
                Toast.makeText(CreateUser.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.typeCheckNumbers(mobileNumber)) {
                mobileNumberEdt.setText("");
                Toast.makeText(CreateUser.this, "Mobile number must only contain numbers.", Toast.LENGTH_SHORT).show();
                return;
            }

            user = new User(firstName, surname, dateOfBirth, email, mobileNumber, username, password);

            //attempts to add user, outputting an appropriate toast
            String errorMessage = users.addUser(user);
            Toast.makeText(CreateUser.this, errorMessage, Toast.LENGTH_SHORT).show();

            if (errorMessage.equals(Users.ERROR_UNIQUE_USERNAME)) {
                usernameEdt.setText("");
            } else if (errorMessage.equals(Users.ERROR_UNIQUE_EMAIL)) {
                emailEdt.setText("");
            } else {
                firstNameEdt.setText("");
                dateOfBirthEdt.setText("");
                surnameEdt.setText("");
                emailEdt.setText("");
                mobileNumberEdt.setText("");
                usernameEdt.setText("");
                passwordEdt.setText("");
            }
        });

        //Btn: starts ViewUsers activity
        readUsersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ViewUsers.class);
            v.getContext().startActivity(intent);
        });

        //go back a page button
        adminSelectionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AdminSelection.class);
            v.getContext().startActivity(intent);
        });
    }
}