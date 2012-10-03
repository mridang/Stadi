package com.mridang.stadissa.events.details.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * This class is used to help out with parsing dates from the custom
 * Finnish locale
 */
public class Dates {

    /*
     * This method parses a Date in the Finnish locale
     *
     * @params The values of the date
     * @return The parsed date
     */
    public static Date parse(String strDate, String strTime) throws Exception {

        try {

            Locale locLocale = new Locale("fi", "FI");
            DateFormat dftFormat = new SimpleDateFormat("d.M.yyyy 'klo' h:mm", locLocale);
            Date datDate = dftFormat.parse(strDate + " " + strTime);
            return datDate;

        } catch (Exception e) {

            throw e;

        }

    }

    /*
     * This print a date in the custom locale format
     *
     * @param  datDate  The date to print out
     * @return A string value containing the formatted date
     */
    public static String print(Date datDate) {

        String strDate;

        if (Locale.getDefault().getLanguage().equals("fi")) {

            Locale locLocale = new Locale("fi", "FI");
            DateFormat dftFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy 'klo' H:mm", locLocale);
            strDate = dftFormat.format(datDate);

        } else {

            Locale locLocale = new Locale("en", "FI");
            DateFormat dftFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy 'from' h:mm a", locLocale);
            strDate = dftFormat.format(datDate);

        }

        return strDate;

    }

    /*
     * This method fetches the start date for a particular week number
     *
     * @param  intWeek  The week number whose date to fetch.
     * @return The start date of the week.
     */
    public static Date fetch(Integer intWeek) throws Exception {

        try {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.WEEK_OF_YEAR, intWeek);
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            return calendar.getTime();

        } catch (Exception e) {

            throw e;

        }

    }

}