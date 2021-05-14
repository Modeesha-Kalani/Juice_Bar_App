package com.example.fruzorest.util;

// Java program for the above approach

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GFG {

    // Function to print difference in
    // time start_date and end_date
    public static String findDifference(String start_date,
                                 String end_date) {

        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");

        // Try Block
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            long difference_In_Time = getTimeDifference(d2.getTime(),d1.getTime());

            long difference_In_Seconds = getTimeDifferenceInSeconds(difference_In_Time, 1000, 60);

            long difference_In_Minutes = getTimeDifferenceInMinutes(difference_In_Time, 1000, 60, 60);

            long difference_In_Hours = getTimeDifferenceInHours(difference_In_Time, 1000, 60, 60, 24);

//            // Calucalte time difference
//            // in milliseconds
//            long difference_In_Time
//                    = d2.getTime() - d1.getTime();
//
//            // Calucalte time difference in
//            // seconds, minutes, hours, years,
//            // and days
//            long difference_In_Seconds
//                    = (difference_In_Time
//                    / 1000)
//                    % 60;
//
//            long difference_In_Minutes
//                    = (difference_In_Time
//                    / (1000 * 60))
//                    % 60;
//
//            long difference_In_Hours
//                    = (difference_In_Time
//                    / (1000 * 60 * 60))
//                    % 24;


            return difference_In_Hours + "";

        }

        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static long getTimeDifference(long time1, long time2) {
        return time1 - time2;
    }

    public static long getTimeDifferenceInHours(long difference_in_time, int i, int i1, int i2, int i3) {
        return ((difference_in_time / (i * i1 * i2)) % i3);
    }

    public static long getTimeDifferenceInMinutes(long difference_in_time, int i, int i1, int i2) {
        return (difference_in_time / (i * i1) % i2);
    }

    public static long getTimeDifferenceInSeconds(long difference_in_time, int i, int i1) {
        return ((difference_in_time / i) % i1);
    }

}
