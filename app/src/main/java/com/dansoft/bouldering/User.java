package com.dansoft.bouldering;

class User {
  private int userID;
  private String firstName;
  private String surname;
  private String username;
  private String password;
  private String dateOfBirth; //in database type is DATE, drop down menu
  private String email;
  private String mobileNumber;

  //constructor
  public User(String firstName, String surname, String dateOfBirth, String email,
              String mobileNumber, String username, String password) {
    this.firstName = firstName;
    this.surname = surname;
    this.username = username;
    this.password = password;
    this.dateOfBirth = dateOfBirth;
    this.email = email;
    this.mobileNumber = mobileNumber;
  }

  //getters and setters
  public int getUserID() {
  	return userID;
  }
  public void setUserID(int userID) {
  	this.userID = userID;
  }
  public String getFirstName() {
  	return firstName;
  }
  public void setFirstName(String firstName) {
  	this.firstName = firstName;
  }
  public String getSurname() {
  	return surname;
  }
  public void setSurname(String surname) {
  	this.surname = surname;
  }
  public String getUsername() {
  	return username;
  }
  public void setUsername(String username) {
  	this.username = username;
  }
  public String getPassword() {
  	return password;
  }
  public void setPassword(String password) {
  	this.password = password;
  }
  public String getDateOfBirth() {
  	return dateOfBirth;
  }
  public void setDateOfBirth(String dateOfBirth) {
  	this.dateOfBirth = dateOfBirth;
  }
  public String getEmail() {
  	return email;
  }
  public void setEmail(String email) {
  	this.email = email;
  }
  public String getMobileNumber() {
  	return mobileNumber;
  }
  public void setMobileNumber(String mobileNumber) {
  	this.mobileNumber = mobileNumber;
  }

  //override toString() method
  public String toString() {
    return "ID: " + userID + ", First name: " + firstName + ", Surname: " + surname + ", Date of birth: " + dateOfBirth
            + ", Email: " + email + ", Mobile number: " + mobileNumber + ", Username: " + username + ", Password: " + password;
  }
}