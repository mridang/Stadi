package com.mridang.stadi.events.details;

import java.net.URI;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockMapActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.bugsense.trace.BugSenseHandler;
import com.google.analytics.tracking.android.EasyTracker;
import com.mridang.stadi.R;
import com.mridang.stadi.events.details.asynctasks.Detailer;

public class Detail extends SherlockMapActivity {

    /*
     * The instance of the background asynchronous task
     */
    private Detailer objDetailer;

    /*
     * @see android.app.Activity#onStart()
     */
    @Override
    public void onStart() {

    	super.onStart();
    	EasyTracker.getInstance().activityStart(this);

    }
    
    /*
     * This shows a full screen loading animation
     */
    public void showProgress() {

        ScrollView svwScroll = (ScrollView) findViewById(R.id.scroll);
        svwScroll.setVisibility(View.GONE);

        ProgressBar pbrProgress = (ProgressBar) findViewById(R.id.progress);
        pbrProgress.setVisibility(View.VISIBLE);

    }

    /*
     * @see android.app.Activity.onCreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        BugSenseHandler.initAndStartSession(this, "f07273d6");
        setContentView(R.layout.detail);
        setTitle(this.getIntent().getStringExtra("EVENT_NAME"));

        ActionBar abrAction = getSupportActionBar();
        abrAction.setDisplayHomeAsUpEnabled(true);
        fetchDetails();

    }

    /*
     * This method scrapes the sites for the trending torrents
     */
    public void fetchDetails() {

        this.objDetailer = new Detailer(this);
        this.objDetailer.execute(this.getIntent().getStringExtra("EVENT_ID"));

    }

    /*
     * This is the onClick listener for the tickets button
     */
    public OnMenuItemClickListener oclShare = new MenuItem.OnMenuItemClickListener() {

        /*
         * @see android.view.MenuItem.OnMenuItemClickListener#onMenuItemClick(android.view.MenuItem)
         */
        public boolean onMenuItemClick(MenuItem mitItem) {

        	EasyTracker.getTracker().trackEvent("OnClicks", "Share", "Share Event", null);
            Intent ittShare = new Intent(Intent.ACTION_SEND).setType("text/plain");
            ittShare.putExtra(Intent.EXTRA_TEXT, (String) findViewById(R.id.description).getTag());
            Detail.this.startActivity(Intent.createChooser(ittShare, getString(R.string.share)));
            return false;

        }

    };

    /*
     * @see com.google.android.maps.MapActivity#isLocationDisplayed()
     */
    @Override
    protected boolean isLocationDisplayed() {

        return false;

    }

    /*
     * This is the onClick listener for the tickets button
     */
    public OnMenuItemClickListener oclCalendar = new MenuItem.OnMenuItemClickListener() {

        /*
         * @see android.view.MenuItem.OnMenuItemClickListener#onMenuItemClick(android.view.MenuItem)
         */
        @TargetApi(14)
		public boolean onMenuItemClick(MenuItem mitItem) {

        	if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        		EasyTracker.getTracker().trackEvent("OnClicks", "Calendar", "Add Event", null);
	            Intent ittAdd = new Intent(Intent.ACTION_INSERT);
	            ittAdd.setData(Events.CONTENT_URI);
                ittAdd.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, (Long) findViewById(R.id.date).getTag());
	            ittAdd.putExtra(Events.TITLE, (String) findViewById(R.id.name).getTag());
	            ittAdd.putExtra(Events.DESCRIPTION, (String) findViewById(R.id.description).getTag());
	            ittAdd.putExtra(Events.EVENT_LOCATION, (String) findViewById(R.id.place).getTag());
	            ittAdd.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
	            ittAdd.putExtra(Events.ALL_DAY, ((Long) findViewById(R.id.date).getTag()) % 86400000L == 0);
	            startActivity(ittAdd);
        	}
        	if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        		EasyTracker.getTracker().trackEvent("OnClicks", "Calendar", "Add Event", null);
        		Intent ittAdd = new Intent(Intent.ACTION_EDIT);
        		ittAdd.setType("vnd.android.cursor.item/event");
        		ittAdd.putExtra("beginTime", (Long) findViewById(R.id.date).getTag());
        		//intent.putExtra("rrule", "FREQ=YEARLY");
        		//intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        		ittAdd.putExtra("title", (String) findViewById(R.id.name).getTag());
        		ittAdd.putExtra("description", (String) findViewById(R.id.description).getTag());
        		ittAdd.putExtra("eventLocation", (String) findViewById(R.id.place).getTag());
        		ittAdd.putExtra("availability", 1);
        		ittAdd.putExtra("allDay", ((Long) findViewById(R.id.date).getTag()) % 86400000L == 0);
        		startActivity(ittAdd);
        	}
			return false;

        }

    };

    /*
     * @see com.google.android.maps.MapActivity#isRouteDisplayed()
     */
    @Override
    protected boolean isRouteDisplayed() {

        return false;

    }

    /*
     * This is the onClick listener for the tickets button
     */
    public View.OnClickListener oclTickets = new View.OnClickListener() {

        /*
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        public void onClick(View vewView) {

        	EasyTracker.getTracker().trackEvent("OnClicks", "Tickets", "Buy Tickets", null);
            String strTickets = ((URI) vewView.getTag()).toString();
            Intent ittTickets = new Intent(Intent.ACTION_VIEW, Uri.parse(strTickets));
            Detail.this.startActivity(ittTickets);
            return;

        }

    };

    /*
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    public boolean onCreateOptionsMenu(Menu mnuMenu) {

    	getSupportMenuInflater().inflate(R.menu.detail, mnuMenu);
        mnuMenu.findItem(R.id.share).setOnMenuItemClickListener(oclShare);
        mnuMenu.findItem(R.id.calendar).setOnMenuItemClickListener(oclCalendar);
        return true;

    }

    /*
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem mitItem) {

		switch (mitItem.getItemId()) {
			case android.R.id.home: {
				onBackPressed();
				return true;
			}
		}

        return(super.onOptionsItemSelected(mitItem));
    }

    /*
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy () {

        super.onDestroy();

        if (this.objDetailer != null) {
            if (this.objDetailer.getStatus() != Status.FINISHED) {
                this.objDetailer.cancel(true);
            }
        }

    }

    /*
     * This hides the full screen loading animation
     */
    public void hideProgress() {

        ProgressBar pbrProgress = (ProgressBar) findViewById(R.id.progress);
        pbrProgress.setVisibility(View.GONE);

        ScrollView svwScroll = (ScrollView) findViewById(R.id.scroll);
        svwScroll.setVisibility(View.VISIBLE);

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