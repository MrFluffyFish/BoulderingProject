package com.dansoft.bouldering;

class Analytic {
  private int userID;
  private int boulderID;
  private String vote;

  //constructor
  public Analytic(int userID, int boulderID, String vote) {
    this.userID = userID;
    this.boulderID = boulderID;
    this.vote = vote;
  }

  //getters and setters
  public int getUserID() {
  	return userID;
  }
  public void setUserID(int userID) {
  	this.userID = userID;
  }
  public int getBoulderID() {
  	return boulderID;
  }
  public void setBoulderID(int boulderID) {
  	this.boulderID = boulderID;
  }
  public String getVote() {
  	return vote;
  }
  public void setVote(String vote) {
  	this.vote = vote;
  }
}