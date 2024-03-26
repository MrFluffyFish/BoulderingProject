package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ImageButton;
import java.util.Calendar;
import java.util.Date;
public class SelectDate extends AppCompatActivity {
    private ImageButton selectSessionBtn;
    private CalendarView calendarView;
    private int currentHour, currentMinute, selectedDate, currentDay, selectedMonth, selectedYear;
    private long currentDate;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        //initialising UI elements
        selectSessionBtn = findViewById(R.id.idBtnSelectSession);
        calendarView = findViewById(R.id.calendarView);

        //getting current times and dates
        calendar = Calendar.getInstance();
        currentMinute = calendar.get(Calendar.MINUTE);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        //in milliseconds for some reason, need this to set min and max dates
        currentDate = calendarView.getDate();

        //setting min and max dates
        if (currentDay == Calendar.MONDAY ||
                currentDay == Calendar.TUESDAY ||
                currentDay == Calendar.WEDNESDAY ||
                currentDay == Calendar.THURSDAY ||
                currentDay == Calendar.FRIDAY) {
            if (currentHour >= 22 || (currentHour == 21 && currentMinute >= 30)) {
                calendarView.setMinDate(currentDate + 86400000);
            } else {
                calendarView.setMinDate(currentDate);
            }

        } else if (currentDay == Calendar.SATURDAY ||
                currentDay == Calendar.SUNDAY) {
            if (currentHour >= 18 || (currentHour == 17 && currentMinute >= 30)) {
                calendarView.setMinDate(currentDate + 86400000);
            } else {
                calendarView.setMinDate(currentDate);
            }
        }

        calendarView.setMaxDate(currentDate + 604800000);

        //waits for user to click on date and stores selected date in BookingBuilder class
        //then starts the SelectTime activity
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth;
            selectedMonth = month+1;
            selectedYear = year;

            //converting date into string to send to bookingBuilder
            String selectedDateString;
            if (selectedDate < 10) {
                selectedDateString = "0" + selectedDate;
            } else {
                selectedDateString = String.valueOf(selectedDate);
            }

            String selectedMonthString;
            if (selectedMonth < 10) {
                selectedMonthString = "0" + selectedMonth;
            } else {
                selectedMonthString = String.valueOf(selectedMonth);
            }

            MainBooking.bookingBuilder.getBooking().setDate(selectedDateString +
                    "/" + selectedMonthString + "/" + selectedYear);

            System.out.println(selectedDateString +
                    "/" + selectedMonthString + "/" + selectedYear);

            Intent intent = new Intent(view.getContext(), SelectTime.class);
            view.getContext().startActivity(intent);
        });

        //Btn: goes back to the SelectSession activity
        selectSessionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SelectSession.class);
            v.getContext().startActivity(intent);
        });
    }
}