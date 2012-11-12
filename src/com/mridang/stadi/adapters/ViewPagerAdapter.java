package com.mridang.stadi.adapters;

import java.util.Calendar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.analytics.tracking.android.EasyTracker;
import com.mridang.stadi.R;
import com.mridang.stadi.events.Events;

/*
 * This class is the adapter for the pager which allows
 * you to page through weeks.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    /*
     * This keeps a list of titles for the pages
     */
    private Integer[] intWeeks = new Integer[52];
    /*
     * This keeps a list of titles for the pages
     */
    private Context ctxContext;    
    
    /*
     * @see android.support.v4.app.FragmentPagerAdapter#getTitle(FragmentManager)
     */
    public ViewPagerAdapter(FragmentManager fm, Context ctxContext) {

        super(fm);

        Calendar calCalendar = Calendar.getInstance();
        for (Integer intWeek = 1; intWeek <= 52; intWeek++) {
            intWeeks[intWeek - 1] = calCalendar.get(Calendar.WEEK_OF_YEAR);
            calCalendar.add(Calendar.DATE, 7);
        }
        
        this.ctxContext = ctxContext;

    }

    /*
     * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
     */
    @Override
    public CharSequence getPageTitle(int intPosition) {

        return this.ctxContext.getString(R.string.week).toUpperCase() + intWeeks[intPosition].toString().toUpperCase();

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

    	EasyTracker.getTracker().trackEvent("PageSwipes", "Weeks", "View Week", (long) intPosition);
        return Events.newInstance(intWeeks[intPosition]);

    }

}