package com.dansoft.bouldering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public class SelectTime extends AppCompatActivity {

    private ImageButton selectDateBtn;
    private Button submitTimeBtn;
    private TextView timeSlotTV;
    private int currentHour, currentDay, currentMinute, selectedHour, selectedMinute, closingTime;
    private Calendar calendar;
    private TimePicker timePicker;
    private NumberPicker minuteSpinner;
    private NumberPicker hourSpinner;
    private String[] displayedMinutes, displayedHours;
    private String timeSlotStart, timeSlotEnd;
    private int timeSlotDuration;
    private boolean confirmed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);

        //initialising UI elements
        selectDateBtn = findViewById(R.id.idBtnSelectDate);
        timePicker = findViewById(R.id.timePicker);
        timeSlotTV = findViewById(R.id.idTVTimeSlot);
        submitTimeBtn = findViewById(R.id.idBtnSubmitTime);
        timePicker.setIs24HourView(true);

        //getting current times and dates
        calendar = Calendar.getInstance();
        currentMinute = calendar.get(Calendar.MINUTE);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        //retrieving the day and session type
        String dateSelected = MainBooking.bookingBuilder.getBooking().getDate();
        int daySelected = Integer.parseInt(dateSelected.substring(0, 2));
        String sessionType = MainBooking.bookingBuilder.getBooking().getSessionType();

        //setting timeSlotDuration based on the session type selected
        switch (sessionType) {
            case "open session":
                timeSlotDuration = 3;
                break;
            case "power hour":
                timeSlotDuration = 1;
                break;
        }

        //converting the date selected as a string to a Date object
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateSelected);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        calendar.setTime(date);

        //getting closing time for selected day
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            closingTime = 18;
        } else {
            closingTime = 22;
        }

        //setting selectable minutes to 0 or 30
        minuteSpinner = (NumberPicker) findViewById(Resources.getSystem().getIdentifier("minute", "id", "android"));
        minuteSpinner.setMinValue(0);
        minuteSpinner.setMaxValue(1);
        displayedMinutes = new String[]{"00", "30"};
        minuteSpinner.setDisplayedValues(displayedMinutes);

        //declared to stop dodgy hour changes
        minuteSpinner.setOnValueChangedListener((numberPicker, i, i1) -> {
            updateTimeSlot();
        });

        //setting selectable hours to the hours of operation for that day
        hourSpinner = (NumberPicker) findViewById(Resources.getSystem().getIdentifier("hour", "id", "android"));
        hourSpinner.setMinValue(0);
        if (closingTime == 18) {
            hourSpinner.setMaxValue(7);
            displayedHours = new String[]{"10", "11", "12", "13", "14", "15", "16", "17"};
        } else {
            hourSpinner.setMaxValue(11);
            displayedHours = new String[]{"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
        }
        hourSpinner.setDisplayedValues(displayedHours);

        //setting the timePicker to the next available time
        //using the indices in displayedHours
        if (daySelected == currentDay) {
            if (currentMinute == 0) {
                timePicker.setHour(currentHour - 10);
                timePicker.setMinute(0);
            } else if (currentMinute > 0 && currentMinute < 30) {
                timePicker.setHour(currentHour - 10);
                timePicker.setMinute(1);
            } else if (currentMinute > 30 && currentMinute <= 59) {
                timePicker.setHour(currentHour + 1 - 10);
                timePicker.setMinute(0);
            }
        } else {
            timePicker.setHour(0);
            timePicker.setMinute(0);
        }

        //initial time slot displayed by timeSlotTV
        updateTimeSlot();

        //changes time slot shown when the user changes the time
        timePicker.setOnTimeChangedListener((timePicker, i, i1) -> {
            updateTimeSlot();
        });

        //Btn: updates bookingBuilder with the time
        //displays an error message if the time is invalid
        submitTimeBtn.setOnClickListener(v -> {
            //checking if the selected time is valid
            if (daySelected == currentDay) {
                if (selectedHour < currentHour) {
                    Toast.makeText(SelectTime.this, "This time has already passed.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (selectedHour == currentHour && selectedMinute < currentMinute) {
                    Toast.makeText(SelectTime.this, "This time has already passed", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (selectedHour >= closingTime) {
                Toast.makeText(SelectTime.this, "This time is outside our hours of operation.", Toast.LENGTH_SHORT).show();
                return;
            }

            //notifies the user when their selected time is near to the closing time
            //they must press the submit button again to confirm
            if (!confirmed) {
                if (closingTime-selectedHour < 3 || (closingTime-selectedHour == 3 && selectedMinute == 30)) {
                    Toast.makeText(SelectTime.this,
                            "Your session length may be reduced due to our closing times.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(SelectTime.this,
                            "Please confirm your time.", Toast.LENGTH_SHORT).show();
                    confirmed = true;
                    return;
                }
            }

            //converting the selected time into a string to be sent to bookingBuilder
            String time = timeFormatter(selectedHour, selectedMinute);
            System.out.println(time);

            //add time to bookingBuilder and start the next selection page
            MainBooking.bookingBuilder.getBooking().setTime(time);

            Intent intent = new Intent(v.getContext(), ScheduleSession.class);
            v.getContext().startActivity(intent);
        });

        //Btn: goes back to the SelectDate activity
        selectDateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SelectDate.class);
            v.getContext().startActivity(intent);
        });
    }

    //updates the time slot shown
    public void updateTimeSlot() {
        //extracts the selected time from the arrays of displayed values
        selectedHour = Integer.parseInt(displayedHours[timePicker.getHour()]);
        selectedMinute = Integer.parseInt(displayedMinutes[timePicker.getMinute()]);

        timeSlotStart = timeFormatter(selectedHour, selectedMinute);
        if (selectedHour+timeSlotDuration >= closingTime) {
            timeSlotEnd = timeFormatter(closingTime, 0);
        } else {
            timeSlotEnd = timeFormatter(selectedHour+timeSlotDuration, selectedMinute);
        }
        timeSlotTV.setText(timeSlotStart + " - " + timeSlotEnd);
    }

    //takes hours and minutes as integers
    //converts into a single string representing a time
    public String timeFormatter(int hour, int minute) {
        String hourString;
        if (hour < 10) {
            hourString = "0" + hour;
        } else {
            hourString = String.valueOf(hour);
        }

        String minuteString;
        if (minute == 0) {
            minuteString = "0" + minute;
        } else {
            minuteString = String.valueOf(minute);
        }

        return hourString + ":" + minuteString;
    }
}