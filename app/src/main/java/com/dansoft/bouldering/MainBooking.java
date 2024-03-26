package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

//booking main menu
public class MainBooking extends AppCompatActivity {

    public static BookingBuilder bookingBuilder = new BookingBuilder();
    private ImageButton mainMenuBtn;
    private Button bookingInfoBtn;
    private TextView bookingsTV;
    private ArrayList<Booking> bookingsArray;
    private int userID = MainActivity.loggedIn.getLoggedInUserID();

    private int currentMinute, currentHour, currentDate, currentMonth, currentYear;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_booking);

        //initialising UI elements
        mainMenuBtn = findViewById(R.id.idBtnMainMenu);
        bookingInfoBtn = findViewById(R.id.idBtnBookingInfo);
        bookingsTV = findViewById(R.id.idTVBookings);

        //getting current date, month and year
        calendar = Calendar.getInstance();
        currentMinute = calendar.get(Calendar.MINUTE);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentDate = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentYear = calendar.get(Calendar.YEAR);

        //fetching all bookings for the logged in user
        bookingsArray = MainActivity.dbHandler.fetchBookingsByUserID(userID);

        //removing bookings that have expired
        ArrayList<Booking> bookingsToRemove = new ArrayList<>();
        for (Booking b: bookingsArray) {
            String stringDate = b.getDate();
            int date = Integer.parseInt(stringDate.substring(0,2));
            int month = Integer.parseInt(stringDate.substring(3,5));
            int year = Integer.parseInt(stringDate.substring(6,10));

            String stringTime = b.getTime();
            int hour = Integer.parseInt(stringTime.substring(0,2));
            int minute = Integer.parseInt(stringTime.substring(3,5));

            if (year < currentYear) {
                bookingsToRemove.add(b);
            } else if (month < currentMonth && year == currentYear) {
                bookingsToRemove.add(b);
            } else if (date < currentDate && month == currentMonth && year == currentYear) {
                bookingsToRemove.add(b);
            } else if (hour < currentHour && date == currentDate && month == currentMonth && year == currentYear) {
                bookingsToRemove.add(b);
            } else if (minute < currentMinute && hour == currentHour && date == currentDate && month == currentMonth && year == currentYear) {
                bookingsToRemove.add(b);
            }
        }
        bookingsArray.removeAll(bookingsToRemove);

        //updating bookingsTV to display all upcoming bookings
        if (bookingsArray.size() == 0) {
            bookingsTV.setText("No upcoming bookings");
        } else {
            for (Booking b: bookingsArray) {
                bookingsTV.setText(bookingsTV.getText() + b.toString() + "\n\n");
            }
        }

        //Btn: goes to BookingInfo activity
        bookingInfoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), BookingInfo.class);
            v.getContext().startActivity(intent);
        });

        //Btn: goes back to the main menu
        mainMenuBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);
        });
    }
}