package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MakeAccount extends AppCompatActivity {
    private EditText firstNameEdt, surnameEdt, dateOfBirthEdt, emailEdt, mobileNumberEdt, usernameEdt, passwordEdt;
    private Button makeAccountBtn, loginPageBtn;
    private User user;
    private Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_account);


        //initializes all UI elements (buttons, forms)
        firstNameEdt = findViewById(R.id.idEdtFirstName);
        surnameEdt = findViewById(R.id.idEdtSurname);
        dateOfBirthEdt = findViewById(R.id.idEdtDateOfBirth);
        emailEdt = findViewById(R.id.idEdtEmail);
        mobileNumberEdt = findViewById(R.id.idEdtMobileNumber);
        usernameEdt = findViewById(R.id.idEdtUsername);
        passwordEdt = findViewById(R.id.idEdtPassword);

        makeAccountBtn = findViewById(R.id.idBtnMakeAccount);
        loginPageBtn = findViewById(R.id.idBtnLoginPage);

        //functionality of makeAccount
        //takes inputs from forms and uses them to add a record to the database
        makeAccountBtn.setOnClickListener(v -> {

            String firstName = firstNameEdt.getText().toString();
            String surname = surnameEdt.getText().toString();
            String dateOfBirth = dateOfBirthEdt.getText().toString();
            String email = emailEdt.getText().toString();
            String mobileNumber = mobileNumberEdt.getText().toString();
            String username = usernameEdt.getText().toString();
            String password = passwordEdt.getText().toString();

            // validating if the text fields are empty or not.
            if (firstName.isEmpty() || surname.isEmpty() || dateOfBirth.isEmpty() || email.isEmpty() || mobileNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MakeAccount.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //other validations
            if (!Validations.lengthCheckString(firstName, 16)) {
                firstNameEdt.setText("");
                Toast.makeText(MakeAccount.this, "First name is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(surname, 16)) {
                surnameEdt.setText("");
                Toast.makeText(MakeAccount.this, "Surname is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(username, 16)) {
                usernameEdt.setText("");
                Toast.makeText(MakeAccount.this, "Username is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(password, 16)) {
                passwordEdt.setText("");
                Toast.makeText(MakeAccount.this, "Password is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.lengthCheckString(email, 32)) {
                emailEdt.setText("");
                Toast.makeText(MakeAccount.this, "Email is too long.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.typeCheckName(firstName) || (!Validations.typeCheckName(surname))) {
                firstNameEdt.setText("");
                surnameEdt.setText("");
                Toast.makeText(MakeAccount.this, "Name contains invalid characters.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.emailFormatCheck(email)) {
                emailEdt.setText("");
                Toast.makeText(MakeAccount.this, "Invalid email.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.typeCheckNumbers(mobileNumber)) {
                mobileNumberEdt.setText("");
                Toast.makeText(MakeAccount.this, "Mobile number must only contain numbers.", Toast.LENGTH_SHORT).show();
                return;
            }

            user = new User(firstName, surname, dateOfBirth, email, mobileNumber, username, password);

            String errorMessage = users.addUser(user);
            if (errorMessage.equals(Users.CONFIRMATION_ADDED)) {
                errorMessage = "You have made an account.";
            }

            //attempts to add user, outputting an appropriate toast
            Toast.makeText(MakeAccount.this, errorMessage, Toast.LENGTH_SHORT).show();

            if (errorMessage.equals(Users.ERROR_UNIQUE_USERNAME)) {
                usernameEdt.setText("");
            } else if (errorMessage.equals(Users.ERROR_UNIQUE_EMAIL)) {
                emailEdt.setText("");
            } else {
                Intent intent = new Intent(v.getContext(), Login.class);
                v.getContext().startActivity(intent);
            }
        });

        //Btn: goes back to the login page
        loginPageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Login.class);
            v.getContext().startActivity(intent);
        });
    }
}