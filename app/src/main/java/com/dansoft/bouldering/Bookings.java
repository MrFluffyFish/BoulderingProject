package com.dansoft.bouldering;

import java.util.ArrayList;

//used to manipulate booking objects
class Bookings {
  private ArrayList<Booking> bookings = new ArrayList<>();

  public static final String ERROR_UNIQUE_BOOKING = "You have already booked an event at this time.";
  public static final String ERROR_USER_NOT_EXIST = "User doesn't exist.";
  //constructor
  public Bookings() {
  }

  //adds booking to the database
  //stops user from making the same booking twice
  //makes sure booking is made with a userID that exists
  public String addBooking(Booking booking) {
    if (!checkUserExists(booking)) {
      return ERROR_USER_NOT_EXIST;
    } else if (!checkUniqueBooking(booking)){
      return ERROR_UNIQUE_BOOKING;
    } else {
      MainActivity.dbHandler.addNewBooking(booking.getUserID(), booking.getDate(), booking.getTime(),
              booking.getSessionType(), booking.getPaymentType(), booking.isShoesHired());
      return "Booking added.";
    }
  }

  //removes a booking record from database
  public void removeBooking(Booking booking) {
    MainActivity.dbHandler.deleteBooking(booking.getBookingID());
  }

  //updates a booking record
  public String updateBooking(Booking booking) {
    if (!checkUserExists(booking)) {
      return ERROR_USER_NOT_EXIST;
    } else if (!checkUniqueBooking(booking)){
      return ERROR_UNIQUE_BOOKING;
    } else {
      MainActivity.dbHandler.updateBooking(booking.getBookingID(), booking.getUserID(), booking.getDate(), booking.getTime(),
              booking.getSessionType(), booking.getPaymentType(), booking.isShoesHired());
      return "Booking updated.";
    }
  }

  //checks that the booking is unique
  public boolean checkUniqueBooking(Booking booking) {
    ArrayList<Booking> bookings = MainActivity.dbHandler.readBookings();
    for (Booking i: bookings) {
      //extra argument after && is to stop errors with updateBooking() comparing with itself
      if (i.getUserID() == booking.getUserID() && i.getTime().equals(booking.getTime())
              && i.getDate().equals(booking.getDate()) && !(i.getBookingID() == booking.getBookingID())) {
        return false;
      }
    }
    return true;
  }

  //checks that a user is in the database
  public boolean checkUserExists(Booking booking) {
    ArrayList<User> users = MainActivity.dbHandler.readUsers();
    for (User i: users) {
      if (i.getUserID() == booking.getUserID()) {
        return true;
      }
    }
    return false;
  }
}