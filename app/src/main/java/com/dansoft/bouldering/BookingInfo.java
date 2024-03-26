package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class BookingInfo extends AppCompatActivity {

    private Button selectSessionBtn;
    private ImageButton mainBookingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);

        //initialising UI elements
        selectSessionBtn = findViewById(R.id.idBtnSelectSession);
        mainBookingBtn = findViewById(R.id.idBtnMainBooking);

        //Btn: starts select session activity
        selectSessionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SelectSession.class);
            v.getContext().startActivity(intent);
        });

        //Btn: goes back to the main booking menu
        mainBookingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainBooking.class);
            v.getContext().startActivity(intent);
        });
    }
}