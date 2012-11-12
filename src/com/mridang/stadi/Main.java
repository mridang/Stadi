package com.mridang.stadi;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bugsense.trace.BugSenseHandler;
import com.google.analytics.tracking.android.EasyTracker;
import com.mridang.stadi.adapters.ViewPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

/*
 * This is the main activity that allows you to page
 * through weeks of events
 */
public class Main extends SherlockFragmentActivity {

    /*
     * @see android.app.Activity#onStart()
     */
    @Override
    public void onStart() {

    	super.onStart();
    	EasyTracker.getInstance().activityStart(this);

    }
	
    /*
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        BugSenseHandler.initAndStartSession(this, "f07273d6");
        setContentView(R.layout.main);

        ActionBar abrAction = getSupportActionBar();
        abrAction.setDisplayHomeAsUpEnabled(false);
        
        ViewPager vpaPager = (ViewPager) findViewById(R.id.viewpager);
        TitlePageIndicator tpiIndicator = (TitlePageIndicator) findViewById(R.id.indicator);

        vpaPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), this.getApplicationContext()));
        tpiIndicator.setViewPager(vpaPager);

    }

    /*
     * @see com.actionbarsherlock.app.SherlockActivity#onStop()
     */
    @Override
    public void onStop() {

      super.onStop();
      EasyTracker.getInstance().activityStop(this);

    }
    
}