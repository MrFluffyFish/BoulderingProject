package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleSession extends AppCompatActivity {

    private Button scheduleSessionBtn;
    private RadioButton adultRB, concessionRB, memberRB, multipassRB;
    private ImageButton selectTimeBtn;
    private CheckBox shoesHiredCB;
    private TextView usernameTV;

    private Booking booking;
    private Bookings bookings = new Bookings();
    String paymentType;
    Boolean shoesHired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_session);

        //initialising UI elements
        scheduleSessionBtn = findViewById(R.id.idBtnScheduleSession);
        shoesHiredCB = findViewById(R.id.idCBShoesHired);
        usernameTV = findViewById(R.id.idTVUsername);
        selectTimeBtn = findViewById(R.id.idBtnSelectTime);
        adultRB = findViewById(R.id.idRBAdult);
        concessionRB = findViewById(R.id.idRBConcession);
        memberRB = findViewById(R.id.idRBMember);
        multipassRB = findViewById(R.id.idRBMultipass);

        //sets usernameTV to the username of the user that is currently logged in
        int loggedInUserID = MainActivity.loggedIn.getLoggedInUserID();
        User loggedInUser = MainActivity.dbHandler.fetchUserByID(loggedInUserID);
        usernameTV.setText(loggedInUser.getUsername());

        //Btn: reads the radio buttons and checkbox
        //finishes updating bookingBuilder and adds booking to database
        scheduleSessionBtn.setOnClickListener(v -> {
            if (adultRB.isChecked()) {
                paymentType = "adult";
            } else if (concessionRB.isChecked()) {
                paymentType = "concession";
            } else if (memberRB.isChecked()) {
                paymentType = "member";
            } else if (multipassRB.isChecked()) {
                paymentType = "multipass";
            } else {
                Toast.makeText(ScheduleSession.this, "Please select a fee.", Toast.LENGTH_SHORT).show();
                return;
            }

            shoesHired = shoesHiredCB.isChecked();
            MainBooking.bookingBuilder.getBooking().setPaymentType(paymentType);
            MainBooking.bookingBuilder.getBooking().setShoesHired(shoesHired);

            booking = MainBooking.bookingBuilder.getBooking();

            //attempts to add a booking, returning an appropriate message
            String errorMessage = bookings.addBooking(booking);
            Toast.makeText(ScheduleSession.this, errorMessage, Toast.LENGTH_SHORT).show();

            //if the user has already booked an event at this time they are sent back to selectDate
            //otherwise they are sent back to MainActivity
            if (errorMessage.equals(Bookings.ERROR_UNIQUE_BOOKING)) {
                Intent intent = new Intent(v.getContext(), SelectDate.class);
                v.getContext().startActivity(intent);
            } else {
                Intent intent = new Intent(v.getContext(), MainBooking.class);
                v.getContext().startActivity(intent);
            }

        });

        //Btn: goes back to the SelectTime activity
        selectTimeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SelectTime.class);
            v.getContext().startActivity(intent);
        });
    }
}