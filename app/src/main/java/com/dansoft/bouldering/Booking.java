package com.dansoft.bouldering;

class Booking {
  private int bookingID;
  private int userID;
  private String date;
  private String time;
  private String paymentType;
  private boolean shoesHired;
  private String sessionType;

  //constructor
  public Booking(int userID, String date, String time,  String sessionType, String paymentType, boolean shoesHired) {
    this.userID = userID;
    this.date = date;
    this.time = time;
    this.paymentType = paymentType;
    this.shoesHired = shoesHired;
    this.sessionType = sessionType;
  }
  
  //getters and setters
  public int getBookingID() {
  	return bookingID;
  }
  public void setBookingID(int bookingID) {
  	this.bookingID = bookingID;
  }
  public int getUserID() {
  	return userID;
  }
  public void setUserID(int userID) {
  	this.userID = userID;
  }
  public String getDate() {
  	return date;
  }
  public void setDate(String date) {
  	this.date = date;
  }
  public String getTime() {
  	return time;
  }
  public void setTime(String time) {
  	this.time = time;
  }
  public String getPaymentType() {
  	return paymentType;
  }
  public void setPaymentType(String paymentType) {
  	this.paymentType = paymentType;
  }
  public boolean isShoesHired() {
  	return shoesHired;
  }
  public void setShoesHired(boolean shoesHired) {
  	this.shoesHired = shoesHired;
  }
  public String getSessionType() {
    return sessionType;
  }
  public void setSessionType(String sessionType) {
    this.sessionType = sessionType;    
  }

  //override toString() method
  public String toString() {
    return "Date: " + date + ", Time: " + time + ",\nSession Type: " + sessionType
            + ",\nPayment Type: " + paymentType + ",\nShoes Hired: " + shoesHired;
  }
}