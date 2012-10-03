package com.mridang.stadissa;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.bugsense.trace.BugSenseHandler;
import com.mridang.stadissa.adapters.ViewPagerAdapter;
import com.mridang.stadissa.widgets.TitlePageIndicator;

/*
 * This is the main activity that allows you to page
 * through weeks of events
 */
public class Main extends FragmentActivity {

    /*
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        BugSenseHandler.initAndStartSession(this, "f07273d6");
        setContentView(R.layout.main);

        ViewPager vpaPager = (ViewPager) findViewById(R.id.viewpager);
        TitlePageIndicator tpiIndicator = (TitlePageIndicator) findViewById(R.id.indicator);

        vpaPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tpiIndicator.setViewPager(vpaPager);

    }

}