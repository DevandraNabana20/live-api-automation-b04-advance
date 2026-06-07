package utils;

import java.time.LocalDate;
import java.util.Random;

public class Utils {
    public static int getRandomTwoDigit(){
        return new Random().nextInt(90) + 10;
    }

    public static String getCategoryName(){
        return "category" + getRandomTwoDigit();
    }

    public static String getActivityTitle(){
        return "Activity_" + getRandomTwoDigit();
    }

    public static String getActivityDescription(){
        return "Activity Description " + getRandomTwoDigit() ;
    }

    public static String getActivityDate(){
        return LocalDate.now().plusDays(5).toString();
    }
    public static String getUpdatedActivityDate(){
        return LocalDate.now().plusDays(6).toString();
    }
}