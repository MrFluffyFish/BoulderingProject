package com.dansoft.bouldering;

//used to manipulate boulder objects
class Boulders {
  //constructor
  public Boulders(){
  }

  //adds boulder to the database
  public void addBoulder(Boulder boulder) {
    MainActivity.dbHandler.addNewBoulder(boulder.getGrade(), boulder.getX(),
            boulder.getY(), boulder.getColour(), boulder.getStyleIDs());
  }

  //removes boulders from the database
  public void removeBoulder(Boulder boulder) {
    MainActivity.dbHandler.deleteBoulder(boulder.getBoulderID());
  }

  //updates a boulder record
  public void updateBoulder(Boulder boulder) {
    MainActivity.dbHandler.updateBoulder(boulder.getBoulderID(), boulder.getGrade(), boulder.getX(),
            boulder.getY(), boulder.getColour(), boulder.getStyleIDs());
  }
}