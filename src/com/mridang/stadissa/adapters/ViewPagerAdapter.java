package com.mridang.stadissa.adapters;

import java.util.Calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mridang.stadissa.events.Events;

/*
 * This class is the adapter for the pager which allows
 * you to page through weeks.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    /*
     * This keeps a list of titles for the pages
     */
    private static Integer[] intWeeks = new Integer[52];

    /*
     * @see android.support.v4.app.FragmentPagerAdapter#getTitle(FragmentManager)
     */
    public ViewPagerAdapter(FragmentManager fm) {

        super(fm);

        Calendar calCalendar = Calendar.getInstance();
        for (Integer intWeek = 1; intWeek <= 52; intWeek++) {
            intWeeks[intWeek - 1] = calCalendar.get(Calendar.WEEK_OF_YEAR);
            calCalendar.add(Calendar.DATE, 7);
        }

    }

    /*
     * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
     */
    @Override
    public CharSequence getPageTitle(int intPosition) {

        //TODO Get the string here
        return "WEEK #" + intWeeks[intPosition].toString().toUpperCase();

    }

    /*
     * @see android.support.v4.app.FragmentPagerAdapter#getCount()
     */
    @Override
    public int getCount() {

        return intWeeks.length;

    }

    /*
     * @see android.support.v4.app.getItem#getCount(int)
     */
    @Override
    public Fragment getItem(int intPosition) {

        return Events.newInstance(intWeeks[intPosition]);

    }

}