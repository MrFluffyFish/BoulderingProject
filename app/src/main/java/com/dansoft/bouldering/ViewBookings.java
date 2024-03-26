package com.dansoft.bouldering;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//uses a BookingRVAdapter to populate recycleView with booking_rv_items
public class ViewBookings extends AppCompatActivity {
    private ArrayList<Booking> bookingsArray;
    private BookingRVAdapter bookingRVAdapter;
    private RecyclerView bookingsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        bookingsArray = MainActivity.dbHandler.readBookings();

        bookingRVAdapter = new BookingRVAdapter(bookingsArray, ViewBookings.this);
        bookingsRV = findViewById(R.id.idRVBookings);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewBookings.this, RecyclerView.VERTICAL, false);
        bookingsRV.setLayoutManager(linearLayoutManager);

        bookingsRV.setAdapter(bookingRVAdapter);
    }
}