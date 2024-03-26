package com.dansoft.bouldering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateBooking extends AppCompatActivity {

    private EditText userIDEdt, dateEdt, startTimeEdt, sessionTypeEdt, paymentTypeEdt;
    private Button updateBookingBtn, removeBookingBtn;
    private CheckBox shoesHiredCB;

    String bookingID, userID, date, startTime, sessionType, paymentType, shoesHiredText;

    private Booking booking;
    private Bookings bookings = new Bookings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_booking);

        //initialising all UI elements
        userIDEdt = findViewById(R.id.idEdtUserID);
        dateEdt = findViewById(R.id.idEdtDate);
        startTimeEdt = findViewById(R.id.idEdtStartTime);
        sessionTypeEdt = findViewById(R.id.idEdtSessionType);
        paymentTypeEdt = findViewById(R.id.idEdtPaymentType);
        shoesHiredCB = findViewById(R.id.idCBShoesHired);

        updateBookingBtn = findViewById(R.id.idBtnUpdateBooking);
        removeBookingBtn = findViewById(R.id.idBtnRemoveBooking);

        //gets info from ViewBookings to fill out form with current info
        bookingID = getIntent().getStringExtra("bookingID");
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("date");
        startTime = getIntent().getStringExtra("startTime");
        sessionType = getIntent().getStringExtra("sessionType");
        paymentType = getIntent().getStringExtra("paymentType");
        shoesHiredText = getIntent().getStringExtra("shoesHired");

        userIDEdt.setText(userID);
        dateEdt.setText(date);
        startTimeEdt.setText(startTime);
        sessionTypeEdt.setText(sessionType);
        paymentTypeEdt.setText(paymentType);

        shoesHiredCB.setChecked(!shoesHiredText.equals("False"));

        //updateBookingBtn: takes users inputs from a form to update a booking record
        updateBookingBtn.setOnClickListener(v -> {
            // validating if the text fields are empty or not.
            if (userID.isEmpty() || date.isEmpty() || startTime.isEmpty() || sessionType.isEmpty() || paymentType.isEmpty()) {
                Toast.makeText(UpdateBooking.this, "Please enter all the data.", Toast.LENGTH_SHORT).show();
                return;
            }

            //other validations
            if (!Validations.typeCheckNumbers(userID)) {
                userIDEdt.setText("");
                Toast.makeText(UpdateBooking.this, "IDs must be numbers.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.sessionTypeCheck(sessionType)) {
                sessionTypeEdt.setText("");
                Toast.makeText(UpdateBooking.this, "Invalid session type.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Validations.paymentTypeCheck(paymentType)) {
                paymentTypeEdt.setText("");
                Toast.makeText(UpdateBooking.this, "Invalid payment type.", Toast.LENGTH_SHORT).show();
                return;
            }

            booking = new Booking(Integer.parseInt(userIDEdt.getText().toString()),
                    dateEdt.getText().toString(), startTimeEdt.getText().toString(), sessionTypeEdt.getText().toString(),
                    paymentTypeEdt.getText().toString(), shoesHiredCB.isChecked());
            booking.setBookingID(Integer.parseInt(bookingID));

            String errorMessage = bookings.updateBooking(booking);
            Toast.makeText(UpdateBooking.this, errorMessage, Toast.LENGTH_SHORT).show();

            if (errorMessage.equals(Bookings.ERROR_USER_NOT_EXIST)) {
                userIDEdt.setText("");
            } else if (errorMessage.equals(Bookings.ERROR_UNIQUE_BOOKING)) {
                dateEdt.setText("");
                startTimeEdt.setText("");
            } else {
                Intent i = new Intent(UpdateBooking.this, CreateBooking.class);
                startActivity(i);
            }
        });

        //functionality of removeUser button
        removeBookingBtn.setOnClickListener(v -> {
            booking = MainActivity.dbHandler.fetchBookingByID(Integer.parseInt(bookingID));
            bookings.removeBooking(booking);

            Toast.makeText(UpdateBooking.this, "Booking removed", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UpdateBooking.this, CreateBooking.class);
            startActivity(i);
        });
    }
}