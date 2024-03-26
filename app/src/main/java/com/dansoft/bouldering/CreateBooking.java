package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateBooking extends AppCompatActivity {

    private EditText userIDEdt, dateEdt, startTimeEdt, sessionTypeEdt, paymentTypeEdt;
    private Button addBookingBtn, readBookingsBtn;
    private ImageButton adminSelectionBtn;
    private CheckBox shoesHiredCB;
    private Booking booking;
    private Bookings bookings = new Bookings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);

        //initialising all UI elements
        userIDEdt = findViewById(R.id.idEdtUserID);
        dateEdt = findViewById(R.id.idEdtDate);
        startTimeEdt = findViewById(R.id.idEdtStartTime);
        sessionTypeEdt = findViewById(R.id.idEdtSessionType);
        paymentTypeEdt = findViewById(R.id.idEdtPaymentType);
        shoesHiredCB = findViewById(R.id.idCBShoesHired);

        addBookingBtn = findViewById(R.id.idBtnAddBooking);
        readBookingsBtn = findViewById(R.id.idBtnReadBookings);
        adminSelectionBtn = findViewById(R.id.idBtnAdminSelection);

        //functionality of add booking button
        //takes inputs from forms and uses them to add a record to the database
        addBookingBtn.setOnClickListener(v -> {

            String userID = userIDEdt.getText().toString();
            String date = dateEdt.getText().toString();
            String startTime = startTimeEdt.getText().toString();
            String sessionType = sessionTypeEdt.getText().toString();
            String paymentType = paymentTypeEdt.getText().toString();
            boolean shoesHired = shoesHiredCB.isChecked();

            // validating if the text fields are empty or not.
            if (userID.isEmpty() || date.isEmpty() || startTime.isEmpty() || sessionType.isEmpty() || paymentType.isEmpty()) {
                Toast.makeText(CreateBooking.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //other validations
            if (!Validations.typeCheckNumbers(userID)) {
                userIDEdt.setText("");
                Toast.makeText(CreateBooking.this, "IDs must be numbers.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.sessionTypeCheck(sessionType)) {
                sessionTypeEdt.setText("");
                Toast.makeText(CreateBooking.this, "Invalid session type.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.paymentTypeCheck(paymentType)) {
                paymentTypeEdt.setText("");
                Toast.makeText(CreateBooking.this, "Invalid payment type.", Toast.LENGTH_SHORT).show();
                return;
            }

            booking = new Booking(Integer.parseInt(userID), date, startTime, sessionType, paymentType, shoesHired);
            //attempts to add a booking, returning an appropriate message
            String errorMessage = bookings.addBooking(booking);
            Toast.makeText(CreateBooking.this, errorMessage, Toast.LENGTH_SHORT).show();

            if (errorMessage.equals(Bookings.ERROR_USER_NOT_EXIST)) {
                userIDEdt.setText("");
            } else if (errorMessage.equals(Bookings.ERROR_UNIQUE_BOOKING)) {
                dateEdt.setText("");
                startTimeEdt.setText("");
            } else {
                userIDEdt.setText("");
                dateEdt.setText("");
                startTimeEdt.setText("");
                sessionTypeEdt.setText("");
                paymentTypeEdt.setText("");
                shoesHiredCB.setChecked(false);
            }
        });

        //Btn that starts ViewBookings activity
        readBookingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ViewBookings.class);
            v.getContext().startActivity(intent);
        });

        //go back a page button
        adminSelectionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AdminSelection.class);
            v.getContext().startActivity(intent);
        });
    }
}