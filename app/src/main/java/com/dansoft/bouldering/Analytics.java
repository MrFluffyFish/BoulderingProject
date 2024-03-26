package com.dansoft.bouldering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
//import java.util.Math;

//used to manipulate analytic objects
class Analytics {
  private static ArrayList<Analytic> analytics = new ArrayList<>();
  public static final String ERROR_NOT_UNIQUE = "This analytic already exists.";
  public static final String ERROR_USER_NOT_EXIST = "User doesn't exist";
  public static final String ERROR_BOULDER_NOT_EXIST = "Boulder doesn't exist";

  //constructor
  public Analytics() {
  }

  //adds analytic to the database
  //makes sure that the user and the boulder both exist
  //stops user from making an analytic with the same boulder
  public String addAnalytic(Analytic analytic) {
    if (!checkUserExists(analytic)) {
      return ERROR_USER_NOT_EXIST;
    } else if (!checkBoulderExists(analytic)) {
      return ERROR_BOULDER_NOT_EXIST;
    } else if (!checkUnique(analytic)) {
      return ERROR_NOT_UNIQUE;
    } else {
      MainActivity.dbHandler.addNewAnalytic(analytic.getUserID(), analytic.getBoulderID(), analytic.getVote());
      return "Analytic has been added.";
    }
  }

  //remove an analytic record from the database
  public void removeAnalytic(Analytic analytic) {
    MainActivity.dbHandler.deleteAnalytic(analytic.getUserID(), analytic.getBoulderID());
  }

  //updates an analytic record
  public String updateAnalytic(int originalUserID, int originalBoulderID, Analytic analytic) {
    if (!checkUserExists(analytic)) {
      return ERROR_USER_NOT_EXIST;
    } else if (!checkBoulderExists(analytic)) {
      return ERROR_BOULDER_NOT_EXIST;
    } else if (!checkUniqueUpdate(originalUserID, originalBoulderID, analytic)) {
      return ERROR_NOT_UNIQUE;
    } else {
      MainActivity.dbHandler.updateAnalytic(originalUserID, originalBoulderID, analytic.getUserID(), analytic.getBoulderID(), analytic.getVote());
      return "Analytic has been updated.";
    }
  }

  //Makes sure that userID and boulderID are a unique composite primary key
  public boolean checkUnique(Analytic analytic) {
    ArrayList<Analytic> analytics = MainActivity.dbHandler.readAnalytics();
    for (Analytic i: analytics) {
      if (i.getUserID() == analytic.getUserID() && i.getBoulderID() == analytic.getBoulderID()) {
        return false;
      }
    }
    return true;
  }

  //extra function is to stop errors with updateAnalytic() comparing with itself
  public boolean checkUniqueUpdate(int originalUserID, int originalBoulderID, Analytic analytic) {
    if (analytic.getUserID() == originalUserID && analytic.getBoulderID() == originalBoulderID) {
      return true;
    } else {
      return checkUnique(analytic);
    }
  }

  //checks that a user exists in the database
  public boolean checkUserExists(Analytic analytic) {
    ArrayList<User> users = MainActivity.dbHandler.readUsers();
    for (User i: users) {
      if (i.getUserID() == analytic.getUserID()) {
        return true;
      }
    }
    return false;
  }

  //checks that a boulder exists in the database
  public boolean checkBoulderExists(Analytic analytic) {
    ArrayList<Boulder> boulders = MainActivity.dbHandler.readBoulders();
    for (Boulder i: boulders) {
      if (i.getBoulderID() == analytic.getBoulderID()) {
        return true;
      }
    }
    return false;
  }

  //calculates the average grade completed by a user by assigning all possible grades to indices in an ArrayList
//  public String averageGrade(User user) {
//    ArrayList<String> gradeValues = new ArrayList<>(Arrays.asList("NA", "4", "5", "5+", "6a", "6a+", "6b", "6b+", "6c", "6c+", "7a", "7a+", "7b+", "7c", "7c+", "8a", "8a+", "8b", "8b"));
//
//    int gradeTotal = 0;
//    for (Analytic i: analytics) {
//      if (i.getUser() == user) {
//        gradeTotal += gradeValues.indexOf(i.getBoulder().getGrade());
//      }
//    }
//    double average = gradeTotal/bouldersCompleted(user);
//    int roundedAverage = (int) Math.round(average);
//    return gradeValues.get(roundedAverage);
//  }

  //queries database for number of analytic records that contain a certain user
//  public int bouldersCompleted(User user) {
//    int bouldersCompleted = 0;
//    for (Analytic i: analytics) {
//      if (i.getUser() == user) {
//        bouldersCompleted += 1;
//      }
//    }
//    return bouldersCompleted;
//  }

  //calculates the proportion (as a decimal) of different holds used for a certain user
//  public HashMap<String, Double> holdProportions(User user) {
//    HashMap<String, Double> holds = new HashMap<>();
//    int jug = 0;
//    int crimp = 0;
//    int pinch = 0;
//    int pocket = 0;
//    int sloper = 0;
//
//    for (Analytic i: analytics) {
//      if (i.getUser() == user) {
//        for (String j: i.getBoulder().getStyleDescriptions()) {
//          switch (j) {
//            case "jug":
//              jug += 1;
//              break;
//            case "crimp":
//              crimp += 1;
//              break;
//            case "pinch":
//              pinch += 1;
//              break;
//            case "pocket":
//              pocket += 1;
//              break;
//            case "sloper":
//              sloper += 1;
//              break;
//          }
//        }
//      }
//    }
//    int total = jug+crimp+pinch+pocket+sloper;
//    double proportion;
//    proportion = (double)(jug/total);
//    holds.put("jug", proportion);
//    proportion = (double)(crimp/total);
//    holds.put("crimp", proportion);
//    proportion = (double)(pinch/total);
//    holds.put("pinch", proportion);
//    proportion = (double)(pocket/total);
//    holds.put("pocket", proportion);
//    proportion = (double)(sloper/total);
//    holds.put("sloper", proportion);
//
//    return holds;
//  }
//
//  //calculates the proportion (as a decimal) of different walls used for a certain user
//  public HashMap<String, Double> wallProportions(User user) {
//    HashMap<String, Double> walls = new HashMap<>();
//    int slab = 0;
//    int overhang = 0;
//    int arete = 0;
//    int vertical = 0;
//    int traverse = 0;
//
//    for (Analytic i: analytics) {
//      if (i.getUser() == user) {
//        for (String j: i.getBoulder().getStyleDescriptions()) {
//          switch (j) {
//            case "slab":
//              slab += 1;
//              break;
//            case "overhang":
//              overhang += 1;
//              break;
//            case "arete":
//              arete += 1;
//              break;
//            case "vertical":
//              vertical += 1;
//              break;
//            case "traverse":
//              traverse += 1;
//              break;
//          }
//        }
//      }
//    }
//    int total = slab+overhang+arete+vertical+traverse;
//    double proportion;
//    proportion = (double)(slab/total);
//    walls.put("slab", proportion);
//    proportion = (double)(overhang/total);
//    walls.put("overhang", proportion);
//    proportion = (double)(arete/total);
//    walls.put("arete", proportion);
//    proportion = (double)(vertical/total);
//    walls.put("vertical", proportion);
//    proportion = (double)(traverse/total);
//    walls.put("traverse", proportion);
//
//    return walls;
//  }
//
//  //calculates the proportion (as a decimal) of different skills used for a certain user
//  public HashMap<String, Double> skillProportions(User user) {
//    HashMap<String, Double> skills = new HashMap<>();
//    int dynamic = 0;
//    int fingery = 0;
//    int technical = 0;
//    int powerful = 0;
//    int reachy = 0;
//
//    for (Analytic i: analytics) {
//      if (i.getUser() == user) {
//        for (String j: i.getBoulder().getStyleDescriptions()) {
//          switch (j) {
//            case "dynamic":
//              dynamic += 1;
//              break;
//            case "fingery":
//              fingery += 1;
//              break;
//            case "technical":
//              technical += 1;
//              break;
//            case "powerful":
//              powerful += 1;
//              break;
//            case "reachy":
//              reachy += 1;
//              break;
//          }
//        }
//      }
//    }
//    int total = dynamic+fingery+technical+powerful+reachy;
//    double proportion;
//    proportion = (double)(dynamic/total);
//    skills.put("dynamic", proportion);
//    proportion = (double)(fingery/total);
//    skills.put("fingery", proportion);
//    proportion = (double)(technical/total);
//    skills.put("technical", proportion);
//    proportion = (double)(powerful/total);
//    skills.put("powerful", proportion);
//    proportion = (double)(reachy/total);
//    skills.put("reachy", proportion);
//
//    return skills;
//  }
}