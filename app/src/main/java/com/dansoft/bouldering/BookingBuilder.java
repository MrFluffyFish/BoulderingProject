package com.dansoft.bouldering;

public class BookingBuilder {
    private Booking booking;

    public BookingBuilder() {
        booking = new Booking(MainActivity.loggedIn.getLoggedInUserID(), "", "", "", "", false);
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }
}
