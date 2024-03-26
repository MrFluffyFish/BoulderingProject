package com.dansoft.bouldering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "BoulderingDB";
    private static final int DB_VERSION = 1;

    //constructor
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creates tables for my database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User ("
                + "userID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "firstName TEXT,"
                + "surname TEXT,"
                + "dateOfBirth TEXT,"
                + "email TEXT,"
                + "mobileNumber TEXT,"
                + "username TEXT,"
                + "password TEXT)");

        db.execSQL("CREATE TABLE Boulder ("
                + "boulderID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "grade TEXT,"
                + "posX INTEGER,"
                + "posY INTEGER,"
                + "colour TEXT)");

        db.execSQL("CREATE TABLE Booking ("
                + "bookingID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "userID INTEGER,"
                + "date TEXT,"
                + "startTime TEXT,"
                + "sessionType TEXT,"
                + "paymentType TEXT,"
                + "shoesHired BOOL,"
                + "FOREIGN KEY (userID) REFERENCES User(userID))");

        db.execSQL("CREATE TABLE Style ("
                + "styleID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "styleDescription TEXT)");

        db.execSQL("CREATE TABLE Analytic ("
                + "userID INTEGER,"
                + "boulderID INTEGER,"
                + "vote TEXT,"
                + "FOREIGN KEY (userID) REFERENCES User(userID),"
                + "FOREIGN KEY (boulderID) REFERENCES Boulder(boulderID))");

        db.execSQL("CREATE TABLE StyleBoulder("
                + "styleID INTEGER,"
                + "boulderID INTEGER,"
                + "FOREIGN KEY (styleID) REFERENCES Style(styleID),"
                + "FOREIGN KEY (boulderID) REFERENCES Boulder(boulderID))");

        //populating styleDescription
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Jugs')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Crimps')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Pinches')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Pockets')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Slopers')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Slab')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Overhang')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Arete')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Vertical')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Traverse')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Dynamic')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Fingery')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Technical')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Powerful')");
        db.execSQL("INSERT INTO Style(styleDescription)"
                + "VALUES ('Reachy')");
    }

    //adds a new user to the database
    public void addNewUser(String firstName, String surname, String dateOfBirth, String email, String mobileNumber, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("firstName", firstName);
        values.put("surname", surname);
        values.put("dateOfBirth", dateOfBirth);
        values.put("email", email);
        values.put("mobileNumber", mobileNumber);
        values.put("username", username);
        values.put("password", password);

        db.insert("User", null, values);
        db.close();
    }

    //deletes a user from the database by ID
    public void deleteUser(int userID) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("User", "userID=?", new String[]{String.valueOf(userID)});

        //deletes all bookings attached to the user
        db.delete("Booking", "userID=?", new String[]{String.valueOf(userID)});

        //deletes all analytics attached to the user
        db.delete("Analytic", "userID=?", new String[]{String.valueOf(userID)});
        db.close();
    }

    //updates a user record
    public void updateUser(int userID, String firstName, String surname, String dateOfBirth,
                           String email, String mobileNumber, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("firstName", firstName);
        values.put("surname", surname);
        values.put("dateOfBirth", dateOfBirth);
        values.put("email", email);
        values.put("mobileNumber", mobileNumber);
        values.put("username", username);
        values.put("password", password);

        db.update("User", values, "userID=?", new String[]{String.valueOf(userID)});
        db.close();
    }

    //reads all users in the database, returning an ArrayList of users
    public ArrayList<User> readUsers() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorUsers = db.rawQuery("SELECT * FROM User", null);

        ArrayList<User> usersArray = new ArrayList<>();
        User user;

        if (cursorUsers.moveToFirst()) {
            do {
                user = new User(cursorUsers.getString(1),
                        cursorUsers.getString(2),
                        cursorUsers.getString(3),
                        cursorUsers.getString(4),
                        cursorUsers.getString(5),
                        cursorUsers.getString(6),
                        cursorUsers.getString(7));
                user.setUserID(Integer.parseInt(cursorUsers.getString(0)));
                usersArray.add(user);
            } while (cursorUsers.moveToNext());
        }
        cursorUsers.close();
        return usersArray;
    }

    //fetches a user record by ID
    public User fetchUserByID(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorUser = db.rawQuery("SELECT * FROM User WHERE UserID=?", new String[]{String.valueOf(userID)});

        cursorUser.moveToFirst();
        User user = new User(cursorUser.getString(1),
                cursorUser.getString(2),
                cursorUser.getString(3),
                cursorUser.getString(4),
                cursorUser.getString(5),
                cursorUser.getString(6),
                cursorUser.getString(7));
        user.setUserID(userID);
        cursorUser.close();
        return user;
    }

    //fetches a user record by username
    public User fetchUserByUsername (String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorUser = db.rawQuery("SELECT * FROM User WHERE username=?", new String[]{String.valueOf(username)});

        cursorUser.moveToFirst();
        User user = new User(cursorUser.getString(1),
                cursorUser.getString(2),
                cursorUser.getString(3),
                cursorUser.getString(4),
                cursorUser.getString(5),
                cursorUser.getString(6),
                cursorUser.getString(7));
        user.setUserID(cursorUser.getInt(0));
        cursorUser.close();
        return user;
    }

    //adds a new boulder to the database
    public void addNewBoulder(String grade, int posX, int posY, String colour, ArrayList<Integer> styleIDs) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("grade", grade);
        values.put("posX", posX);
        values.put("posY", posY);
        values.put("colour", colour);

        int boulderID = (int) db.insert("Boulder", null, values);
        for (Integer i: styleIDs) {
            values = new ContentValues();
            values.put("styleID", i);
            values.put("boulderID", boulderID);
            db.insert("StyleBoulder", null, values);
        }
        db.close();
    }

    //deletes a boulder from the database by ID
    public void deleteBoulder(int boulderID) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Boulder", "boulderID=?", new String[]{String.valueOf(boulderID)});
        db.delete("StyleBoulder", "boulderID=?", new String[]{String.valueOf(boulderID)});

        //deletes all analytics attached to the boulder
        db.delete("Analytic", "boulderID=?", new String[]{String.valueOf(boulderID)});
        db.close();
    }

    //updates a boulder record
    public void updateBoulder(int boulderID, String grade, int posX, int posY, String colour, ArrayList<Integer> styleIDs) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("grade", grade);
        values.put("posX", posX);
        values.put("posY", posY);
        values.put("colour", colour);

        db.update("Boulder", values, "boulderID=?", new String[]{String.valueOf(boulderID)});

        //deletes all StyleBoulder entries for the boulder being updated and then adds the new StyleBoulder entries
        db.delete("StyleBoulder", "boulderID=?", new String[]{String.valueOf(boulderID)});
        for (Integer i: styleIDs) {
            values = new ContentValues();
            values.put("styleID", i);
            values.put("boulderID", boulderID);
            db.insert("StyleBoulder", null, values);
        }
        db.close();
    }

    //reads all boulders in the database and returns an ArrayList<Boulder>
    public ArrayList<Boulder> readBoulders() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorBoulders = db.rawQuery("SELECT * FROM Boulder", null);
        Cursor cursorStyles;

        ArrayList<Boulder> bouldersArray = new ArrayList<>();
        ArrayList<Integer> styleIDs;

        Boulder boulder;
        int boulderID;

        if (cursorBoulders.moveToFirst()) {
            do {
                boulderID = cursorBoulders.getInt(0);

                //fetches all the styleIDs attached to the boulder
                styleIDs = new ArrayList<>();
                cursorStyles = db.rawQuery("SELECT * FROM StyleBoulder WHERE boulderID=?", new String[]{String.valueOf(boulderID)});
                if (cursorStyles.moveToNext()) {
                    do {
                        styleIDs.add(cursorStyles.getInt(0));
                    }
                    while (cursorStyles.moveToNext());
                }
                cursorStyles.close();

                boulder = new Boulder(cursorBoulders.getString(1),
                        cursorBoulders.getInt(2),
                        cursorBoulders.getInt(3),
                        cursorBoulders.getString(4),
                        styleIDs);
                boulder.setBoulderID(boulderID);
                bouldersArray.add(boulder);
            } while (cursorBoulders.moveToNext());
        }
        cursorBoulders.close();
        return bouldersArray;
    }

    //fetches a boulder record by ID
    public Boulder fetchBoulderByID(int boulderID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorBoulder = db.rawQuery("SELECT * FROM Boulder WHERE boulderID=?", new String[]{String.valueOf(boulderID)});
        Cursor cursorStyles;

        ArrayList<Integer> styleIDs;
        Boulder boulder;

        cursorBoulder.moveToFirst();

        //fetches all the styleIDs attached to the boulder
        styleIDs = new ArrayList<>();
        cursorStyles = db.rawQuery("SELECT * FROM StyleBoulder WHERE boulderID=?", new String[]{String.valueOf(boulderID)});
        if (cursorStyles.moveToNext()) {
            do {
                styleIDs.add(cursorStyles.getInt(0));
            } while (cursorStyles.moveToNext());
        }
        cursorStyles.close();

        boulder = new Boulder(cursorBoulder.getString(1),
                        cursorBoulder.getInt(2),
                        cursorBoulder.getInt(3),
                        cursorBoulder.getString(4),
                        styleIDs);
        boulder.setBoulderID(boulderID);
        cursorBoulder.close();
        return boulder;
    }

    //fetches all the styleDescriptions and returns an ArrayList<String>
    public ArrayList<String> fetchStyleDescriptions(ArrayList<Integer> styleIDs) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorStyle;
        ArrayList<String> styleDescriptions = new ArrayList<>();

        for (Integer i: styleIDs) {
            cursorStyle = db.rawQuery("SELECT * FROM Style WHERE styleID=?", new String[]{String.valueOf(i)});
            cursorStyle.moveToFirst();
            styleDescriptions.add(cursorStyle.getString(1));
            cursorStyle.close();
        }
        return styleDescriptions;
    }

    //adds a new booking to the database
    public void addNewBooking(int userID, String date, String startTime, String sessionType, String paymentType, boolean shoesHired) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userID", userID);
        values.put("date", date);
        values.put("startTime", startTime);
        values.put("sessionType", sessionType);
        values.put("paymentType", paymentType);
        values.put("shoesHired", shoesHired);

        db.insert("Booking", null, values);
        db.close();
    }

    //deletes a booking from the database by ID
    public void deleteBooking(int bookingID) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Booking", "bookingID=?", new String[]{String.valueOf(bookingID)});
        db.close();
    }

    //updates a booking record
    public void updateBooking(int bookingID, int userID, String date, String startTime, String sessionType, String paymentType, boolean shoesHired) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userID", userID);
        values.put("date", date);
        values.put("startTime", startTime);
        values.put("sessionType", sessionType);
        values.put("paymentType", paymentType);
        values.put("shoesHired", shoesHired);

        db.update("Booking", values, "bookingID=?", new String[]{String.valueOf(bookingID)});
        db.close();
    }

    //reads all booking records from the database, returning and ArrayList<Booking>
    public ArrayList<Booking> readBookings() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorBookings = db.rawQuery("SELECT * FROM Booking", null);

        ArrayList<Booking> bookingsArray = new ArrayList<>();
        Booking booking;
        boolean shoesHired;

        if (cursorBookings.moveToFirst()) {
            do {
                if (cursorBookings.getString(6).equals("0")) {
                    shoesHired = false;
                } else {
                    shoesHired = true;
                }
                booking = new Booking(cursorBookings.getInt(1),
                        cursorBookings.getString(2),
                        cursorBookings.getString(3),
                        cursorBookings.getString(4),
                        cursorBookings.getString(5),
                        shoesHired);
                booking.setBookingID(Integer.parseInt(cursorBookings.getString(0)));
                bookingsArray.add(booking);
            } while (cursorBookings.moveToNext());
        }
        cursorBookings.close();
        return bookingsArray;
    }

    //fetches a booking record by ID
    public Booking fetchBookingByID(int bookingID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorBooking = db.rawQuery("SELECT * FROM Booking WHERE bookingID=?", new String[]{String.valueOf(bookingID)});

        Booking booking;
        boolean shoesHired;

        cursorBooking.moveToFirst();
        if (cursorBooking.getString(6).equals("0")) {
            shoesHired = false;
        } else {
            shoesHired = true;
        }
        booking = new Booking(cursorBooking.getInt(1),
                        cursorBooking.getString(2),
                        cursorBooking.getString(3),
                        cursorBooking.getString(4),
                        cursorBooking.getString(5),
                        shoesHired);
        booking.setBookingID(bookingID);

        cursorBooking.close();
        return booking;
    }

    public ArrayList<Booking> fetchBookingsByUserID(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorBookings = db.rawQuery("SELECT * FROM Booking WHERE userID=?", new String[]{String.valueOf(userID)});

        ArrayList<Booking> bookingsArray = new ArrayList<>();
        Booking booking;
        boolean shoesHired;

        if (cursorBookings.moveToFirst()) {
            do {
                if (cursorBookings.getString(6).equals("0")) {
                    shoesHired = false;
                } else {
                    shoesHired = true;
                }
                booking = new Booking(cursorBookings.getInt(1),
                        cursorBookings.getString(2),
                        cursorBookings.getString(3),
                        cursorBookings.getString(4),
                        cursorBookings.getString(5),
                        shoesHired);
                booking.setBookingID(Integer.parseInt(cursorBookings.getString(0)));
                bookingsArray.add(booking);
            } while (cursorBookings.moveToNext());
        }
        cursorBookings.close();
        return bookingsArray;
    }

    //adds a new analytic to the database
    public void addNewAnalytic(int userID, int boulderID, String vote) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userID", userID);
        values.put("boulderID", boulderID);
        values.put("vote", vote);

        db.insert("Analytic", null, values);
        db.close();
    }

    //deletes an analytic from the database by ID
    public void deleteAnalytic(int userID, int boulderID) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Analytic", "userID=? AND boulderID=?", new String[]{String.valueOf(userID), String.valueOf(boulderID)});
        db.close();
    }

    //updates and analytic record
    public void updateAnalytic(int originalUserID, int originalBoulderID, int userID, int boulderID, String vote) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userID", userID);
        values.put("boulderID", boulderID);
        values.put("vote", vote);

        db.update("Analytic", values, "userID=? AND boulderID=?", new String[]{String.valueOf(originalUserID), String.valueOf(originalBoulderID)});
        db.close();
    }

    //reads all analytics in the database and returns and ArrayList<Analytic>
    public ArrayList<Analytic> readAnalytics() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorAnalytics = db.rawQuery("SELECT * FROM Analytic", null);

        ArrayList<Analytic> analyticsArray = new ArrayList<>();
        Analytic analytic;

        if (cursorAnalytics.moveToFirst()) {
            do {
                analytic = new Analytic(cursorAnalytics.getInt(0),
                        cursorAnalytics.getInt(1),
                        cursorAnalytics.getString(2));
                analyticsArray.add(analytic);
            } while (cursorAnalytics.moveToNext());
        }
        cursorAnalytics.close();
        return analyticsArray;
    }

    //fetches an analytic record by ID
    public Analytic fetchAnalyticByIDs(int userID, int boulderID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorAnalytic = db.rawQuery("SELECT * FROM Analytic WHERE userID=? AND boulderID=?",
                new String[]{String.valueOf(userID), String.valueOf(boulderID)});

        Analytic analytic;

        cursorAnalytic.moveToFirst();
        analytic = new Analytic(cursorAnalytic.getInt(0),
                cursorAnalytic.getInt(1),
                cursorAnalytic.getString(2));

        cursorAnalytic.close();
        return analytic;
    }

    //fetches all the votes for a particular boulder
    public ArrayList<String> fetchVotesByBoulderID(int boulderID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorAnalytics = db.rawQuery("SELECT * FROM Analytic WHERE boulderID=?", new String[]{String.valueOf(boulderID)});

        ArrayList<String> votes = new ArrayList<>();
        String vote;

        if (cursorAnalytics.moveToFirst()) {
            do {
                vote = cursorAnalytics.getString(2);
                if (!vote.equals("N/A")) {
                    votes.add(vote);
                }
            } while (cursorAnalytics.moveToNext());
        }
        cursorAnalytics.close();
        return votes;
    }

    //checks if an analytic exists, doesn't check for vote
    public boolean isAnalyticExist(int userID, int boulderID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorAnalytic = db.rawQuery("SELECT * FROM Analytic WHERE userID=? AND boulderID=?",
                new String[]{String.valueOf(userID), String.valueOf(boulderID)});

        if (cursorAnalytic.moveToFirst()) {
            cursorAnalytic.close();
            return true;
        } else {
            cursorAnalytic.close();
            return false;
        }
    }

    // this method is called to check if the table exists already.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "User");
        onCreate(db);
    }
}
