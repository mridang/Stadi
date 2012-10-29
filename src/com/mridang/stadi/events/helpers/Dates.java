package com.mridang.stadi.events.helpers;

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
     * The locale that should be used for parsing dates
     */
    private final static Locale locLocale = new Locale("fi", "FI");

    /*
     * This method parses a Date in the Finnish locale
     *
     * @params The values of the date
     * @return The parsed date
     */
	public static Date parse(String strDay, String strMonth, String strYear, String strHour) throws Exception {

		try {

		    DateFormat dftFormat = new SimpleDateFormat("dd MMMM yyyy HH", locLocale);
		    Date datDate = dftFormat.parse(String.format("%s %sta %s %s", strDay, strMonth, strYear, strHour.isEmpty() ? "00" : strHour));
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
            DateFormat dftFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", locLocale);
            strDate = dftFormat.format(datDate);

        } else {

            Locale locLocale = new Locale("en", "FI");
            DateFormat dftFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", locLocale);
            strDate = dftFormat.format(datDate);

        }

        return strDate;

    }

    /*
     * The instance of the calendar for printing dates
     */
	private final static Calendar calCalendar = Calendar.getInstance();

	/*
	 * This method fetches the start date for a particular week number
	 *
	 * @param  intWeek  The week number whose date to fetch.
	 * @return The start date of the week.
	 */
    public static Date fetch(Integer intWeek) {

        try {

            calCalendar.clear();
            calCalendar.set(Calendar.WEEK_OF_YEAR, intWeek);
            calCalendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            return calCalendar.getTime();

        } catch (Exception e) {

            return null;

        }

    }

}