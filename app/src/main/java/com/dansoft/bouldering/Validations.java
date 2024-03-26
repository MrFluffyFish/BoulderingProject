package com.dansoft.bouldering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//all validations needed in admin menu
public class Validations {

    //checks a string has length less than a specified value
    public static boolean lengthCheckString(String s, int lengthMax) {
        return s.length() < lengthMax;
    }

    //checks an integer has length less than a specified value
    public static boolean lengthCheckInt(int i, int lengthMax) {
        String s = Integer.toString(i);
        return s.length() < lengthMax;
    }

    //checks that name entries only contain a specific set of characters
    public static boolean typeCheckName(String name) {
        ArrayList<String> allowed = new ArrayList<>(Arrays.asList(
                "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                "à","è","ì","ò","ù","À","È","Ì","Ò","Ù",
                "á","é","í","ó","ú","ý","Á","É","Í","Ó","Ú","Ý",
                "â","ê","î","ô","û","Â","Ê","Î","Ô","Û",
                "ã","ñ","õ","Ã","Ñ","Õ",
                "ä","ë","ï","ö","ü","ÿ","Ä","Ë","Ï","Ö","Ü","Ÿ",
                "å","Å","æ","Æ","œ","Œ","ç","Ç","ð","Ð","ø","Ø","ß"));

        for (int i = 0; i < name.length(); i++) {
            String c = String.valueOf(name.charAt(i));
            if (!allowed.contains(c)) {
                return false;
            }
        }
        return true;
    }

    //checks that an email entry has a valid format
    public static boolean emailFormatCheck(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    //checks that a string only contains numbers
    public static boolean typeCheckNumbers(String number) {
        return number.matches("[0-9]+");
    }

    //checks whether an integer lies inside a given range
    public static boolean rangeCheck(int i, int lower, int upper) {
        return (i >= lower) && (i <= upper);
    }

    //checks that a grade entry is valid
    public static boolean gradeCheck(String grade) {
        ArrayList<String> allowed = new ArrayList<>(Arrays.asList(
                "N/A","4","5","5+","6a","6a+","6b","6b+","6c","6c+","7a","7a+","7b","7b+","7c","7c+","8a","8a+","8b","8b+"));
        return allowed.contains(grade);
    }

    //checks that a session type entry is valid
    public static boolean sessionTypeCheck(String sessionType) {
        ArrayList<String> allowed = new ArrayList<>(Arrays.asList("open Session", "power hour"));
        return allowed.contains(sessionType);
    }

    //checks that a payment type entry is valid
    public static boolean paymentTypeCheck(String paymentType) {
        ArrayList<String> allowed = new ArrayList<>(Arrays.asList("adult", "concession", "member", "multipass"));
        return allowed.contains(paymentType);
    }

    //checks that a colour entry is valid
    public static boolean colourCheck(String colour) {
        ArrayList<String> allowed = new ArrayList<>(Arrays.asList("red","pink","orange","yellow","green","blue","purple","brown","white","black"));
        return allowed.contains(colour);
    }

    public static boolean validCharsCheck(String text) {
        return text.matches("\\A\\p{ASCII}*\\z");
    }
}