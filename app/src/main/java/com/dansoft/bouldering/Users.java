package com.dansoft.bouldering;

import java.util.ArrayList;

//used to manipulate user objects
class Users {
  public static final String ERROR_UNIQUE_USERNAME = "Username is already taken.";
  public static final String ERROR_UNIQUE_EMAIL = "Email is not unique.";
  public static final String CONFIRMATION_ADDED = "User has been added";

  //constructor
  public Users() {
  }

  //adds user to the database, stops duplicate username and email
  public String addUser(User user) {
    if (!checkUniqueUsername(user)) {
      return "Username is not unique.";
    } else if (!checkUniqueEmail(user)) {
      return "Email is not unique.";
    } else {
      MainActivity.dbHandler.addNewUser(user.getFirstName(), user.getSurname(), user.getDateOfBirth(),
              user.getEmail(), user.getMobileNumber(), user.getUsername(), user.getPassword());
      return CONFIRMATION_ADDED;
    }
  }

  //removes user from the database
  public void removeUser(User user) {
    MainActivity.dbHandler.deleteUser(user.getUserID());
  }

  //updates a user record, stops duplicate usernames and emails
  public String updateUser(User user) {
    if (!checkUniqueUsername(user)) {
      return ERROR_UNIQUE_USERNAME;
    } else if (!checkUniqueEmail(user)) {
      return ERROR_UNIQUE_EMAIL;
    } else {
      MainActivity.dbHandler.updateUser(user.getUserID(), user.getFirstName(), user.getSurname(), user.getDateOfBirth(),
              user.getEmail(), user.getMobileNumber(), user.getUsername(), user.getPassword());
      return "User has been updated";
    }
  }

  //checks if a user has a unique username
  public boolean checkUniqueUsername(User user) {
    ArrayList<User> users = MainActivity.dbHandler.readUsers();
    for (User i : users) {
      //extra argument after && is to stop errors with updateUser() comparing with itself
      if (user.getUsername().equals(i.getUsername()) && !(user.getUserID() == i.getUserID())) {
        return false;
      }
    }
    return true;
  }

  //checks if a user has a unique email
  public boolean checkUniqueEmail(User user) {
    ArrayList<User> users = MainActivity.dbHandler.readUsers();
    for (User i : users) {
      //extra argument after && is to stop errors with updateUser() comparing with itself
      if (user.getEmail().equals(i.getEmail()) && !(user.getUserID() == i.getUserID())) {
        return false;
      }
    }
    return true;
  }

  //checks if the user has entered a valid username+password
  public boolean attemptLogin(String username, String password) {
    ArrayList<User> users = MainActivity.dbHandler.readUsers();
    for (User i: users) {
      if (i.getUsername().equals(username) && i.getPassword().equals(password)) {
        return true;
      }
    }
    return false;
  }
}

