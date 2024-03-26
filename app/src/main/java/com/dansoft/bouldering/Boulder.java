package com.dansoft.bouldering;

import java.util.ArrayList;

class Boulder {
  private int boulderID;
  private String grade;
  private int x;
  private int y;
  private String colour;
  private ArrayList<Integer> styleIDs = new ArrayList<>();

  //constructor
  public Boulder(String grade, int x, int y, String colour, ArrayList<Integer> styleIDs) {
    this.grade = grade;
    this.x = x;
    this.y = y;
    this.colour = colour;
    this.styleIDs.addAll(styleIDs);
  }

  //getters and setters
  public int getBoulderID() {
  	return boulderID;
  }
  public void setBoulderID(int boulderID) {
  	this.boulderID = boulderID;
  }
  public String getGrade() {
  	return grade;
  }
  public void setGrade(String grade) {
  	this.grade = grade;
  }
  public int getX() {
  	return x;
  }
  public void setX(Integer x) {
  	this.x = x;
  }
  public int getY() {
  	return y;
  }
  public void setY(Integer y) {
  	this.y = y;
  }
  public String getColour() {
    return colour;
  }
  public void setColour(String colour) {
    this.colour = colour;
  }
  public ArrayList<Integer> getStyleIDs() {
  	return styleIDs;
  }
  public void setStyleIDs(ArrayList<Integer> styleIDs) {
      this.styleIDs = new ArrayList<>();
      this.styleIDs.addAll(styleIDs);
  }

  //override toString() method
  public String toString() {
    return "ID: " + boulderID + ", Grade: " + grade + ", posX: " + x
            + ", posY: " + y + ", colour: " + colour + ", styleIDs: " + styleIDs.toString();
  }
}