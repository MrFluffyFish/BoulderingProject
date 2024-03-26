package com.dansoft.bouldering;

//stores the userID of the user that is currently logged in
public class LoggedIn {
    private int loggedInUserID;

    //put this back to 0
    public LoggedIn() {
        this.loggedInUserID = 1;
    }
    public int getLoggedInUserID() {
        return loggedInUserID;
    }
    public void setLoggedInUserID(int loggedInUserID) {
        this.loggedInUserID = loggedInUserID;
    }
}
