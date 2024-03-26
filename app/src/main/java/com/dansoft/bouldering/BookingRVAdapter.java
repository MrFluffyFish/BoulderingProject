package com.dansoft.bouldering;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//used to populate ViewBookings recycleView with booking_rv_items
public class BookingRVAdapter extends RecyclerView.Adapter<BookingRVAdapter.ViewHolder> {

    private ArrayList<Booking> bookingsArray;
    private Context context;

    public BookingRVAdapter(ArrayList<Booking> bookingsArray, Context context) {
        this.bookingsArray = bookingsArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //for each booking record in the database
        //a booking_rv_item is created and personalised to that record
        Booking booking = bookingsArray.get(position);
        holder.bookingIDTV.setText(String.valueOf(booking.getBookingID()));
        holder.userIDTV.setText(String.valueOf(booking.getUserID()));
        holder.dateTV.setText(booking.getDate());
        holder.startTimeTV.setText(booking.getTime());
        holder.sessionTypeTV.setText(booking.getSessionType());
        holder.paymentTypeTV.setText(booking.getPaymentType());

        //converting boolean into String
        String shoesHiredText;
        if (booking.isShoesHired()) {
            shoesHiredText = "True";
        } else {
            shoesHiredText = "False";
        }

        holder.shoesHiredTV.setText(shoesHiredText);

        //upon clicking on a booking_rv_item
        //starts UpdateBooking activity and sends relevant information
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, UpdateBooking.class);

            i.putExtra("bookingID", String.valueOf(booking.getBookingID()));
            i.putExtra("userID", String.valueOf(booking.getUserID()));
            i.putExtra("date", booking.getDate());
            i.putExtra("startTime", booking.getTime());
            i.putExtra("sessionType", booking.getSessionType());
            i.putExtra("paymentType", booking.getPaymentType());
            i.putExtra("shoesHired", shoesHiredText);

            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return bookingsArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bookingIDTV, userIDTV, dateTV, startTimeTV, sessionTypeTV, paymentTypeTV, shoesHiredTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookingIDTV = itemView.findViewById(R.id.idTVBookingID);
            userIDTV = itemView.findViewById(R.id.idTVUserID);
            dateTV = itemView.findViewById(R.id.idTVDate);
            startTimeTV = itemView.findViewById(R.id.idTVStartTime);
            sessionTypeTV = itemView.findViewById(R.id.idTVSessionType);
            paymentTypeTV = itemView.findViewById(R.id.idTVPaymentType);
            shoesHiredTV = itemView.findViewById(R.id.idTVShoesHired);
        }
    }
}
