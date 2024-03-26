package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class SelectSession extends AppCompatActivity {

    private ImageButton bookingInfoBtn;
    private RadioButton openSessionRB, powerHourRB;
    private Button submitSessionBtn;
    private String selectedSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_session);

        //initializing UI elements
        bookingInfoBtn = findViewById(R.id.idBtnBookingInfo);
        openSessionRB = findViewById(R.id.idRBOpenSession);
        powerHourRB = findViewById(R.id.idRBPowerHour);
        submitSessionBtn = findViewById(R.id.idBtnSubmitSession);

        //Btn: updates bookingBuilder with the session type selected
        //starts selectDate activity
        submitSessionBtn.setOnClickListener(v -> {
            if (openSessionRB.isChecked()) {
                selectedSession = "open session";
            } else if (powerHourRB.isChecked()) {
                selectedSession = "power hour";
            } else {
                Toast.makeText(SelectSession.this, "Please select a session.", Toast.LENGTH_SHORT).show();
                return;
            }

            MainBooking.bookingBuilder.getBooking().setSessionType(selectedSession);
            Intent intent = new Intent(v.getContext(), SelectDate.class);
            v.getContext().startActivity(intent);
        });

        //Btn: goes back to the BookingInfo activity
        bookingInfoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BookingInfo.class);
            v.getContext().startActivity(intent);
        });
    }
}