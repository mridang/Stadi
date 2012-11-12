package com.mridang.stadi.events.details.asynctasks;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.mridang.stadi.R;
import com.mridang.stadi.events.details.Detail;
import com.mridang.stadi.events.details.helpers.Dates;
import com.mridang.stadi.events.details.overlays.Mappin;
import com.mridang.stadi.events.details.scrapers.Stadissa;
import com.mridang.stadi.events.details.structures.Details;

/*
 * This is the class used to list the events.
 * It is used a asynchronous activity so that it does not hold
 * up the main UI thread.
 */
public class Detailer extends AsyncTask<String, Integer, Details> {

    /*
     * The instance of the calling class
     */
    private Detail objDetail = null;
    /*
     * The time taken to execute
     */
    private Long lngTiming;
    
    /*
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Details doInBackground(String... intIdentifier) {

        Details objDetails = null;

        try {

            Log.d("asynctasks.Scraper", "Scraping detail");
            Stadissa objStadissa = new Stadissa(Detailer.this.objDetail);
            objDetails = objStadissa.doFetch(intIdentifier[0]);
            return objDetails;

        } catch (Exception e) {

            Log.w("asynctask.Scraper", "Error scraping events");

        }

        return objDetails;

    }

    /*
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {

        this.objDetail.showProgress();
        this.lngTiming = System.nanoTime();

    }

    /*
     * Initializes this task
     *
     * @param  objDetail    The instance of the calling Trend class
     * @param  enmCatergory  The category which should be scraped
     */
    public Detailer(Detail detail) {

        this.objDetail = detail;
        
    }

    /*
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(final Details objDetails) {

    	this.lngTiming = System.nanoTime() - this.lngTiming;
        this.objDetail.hideProgress();

        EasyTracker.getTracker().trackTiming("AsyncTasks", this.lngTiming, "Detailer", "Get Event");
        
        if (objDetails == null) {

            Toast.makeText(this.objDetail, R.string.error, Toast.LENGTH_LONG).show();

        } else {

        	TextView tvwName = (TextView) this.objDetail.findViewById(R.id.name);
        	tvwName.setText(objDetails.getName());
        	tvwName.setTag(objDetails.getName());

        	TextView tvwPrice = (TextView) this.objDetail.findViewById(R.id.price);
        	tvwPrice.setText(objDetails.getPrice());

        	TextView tvwAddress = (TextView) this.objDetail.findViewById(R.id.address);
        	tvwAddress.setText(objDetails.getAddress());

        	TextView tvwInformation = (TextView) this.objDetail.findViewById(R.id.description);
        	tvwInformation.setText(Html.fromHtml(objDetails.getInformation()));
        	tvwInformation.setTag(objDetails.getWebpage().toString());

        	TextView tvwDate = (TextView) this.objDetail.findViewById(R.id.date);
        	tvwDate.setText(Dates.print(objDetails.getDate()));
        	tvwDate.setTag(objDetails.getDate().getTime() + (objDetails.getDate().getTimezoneOffset() * -60000L));

        	TextView tvwPlace = (TextView) this.objDetail.findViewById(R.id.place);
        	tvwPlace.setText(objDetails.getPlace());
        	tvwPlace.setTag(objDetails.getPlace());

        	Button btnTickets = (Button) this.objDetail.findViewById(R.id.tickets);
        	btnTickets.setTag(objDetails.getTickets());
        	btnTickets.setOnClickListener(this.objDetail.oclTickets);

        	LinearLayout lltBottom = (LinearLayout) this.objDetail.findViewById(R.id.bottom);
        	lltBottom.setVisibility((objDetails.getTickets() == null) ? View.GONE : View.VISIBLE);

        	try {

        	    Geocoder geoFinder = new Geocoder(this.objDetail, Locale.getDefault());
        	    Address addLocation = geoFinder.getFromLocationName(objDetails.getAddress() + " " + objDetails.getCity(), 1).get(0);
        	    MapView mapLocation = (MapView) this.objDetail.findViewById(R.id.map);
        	    Integer intLatitude = (int)(addLocation.getLatitude() * 1000000);
        	    Integer intLongitude = (int)(addLocation.getLongitude() * 1000000);

        	    mapLocation.getController().setZoom(16);
        	    mapLocation.getController().setCenter(new GeoPoint(intLatitude, intLongitude));

                Drawable finalMarker = this.objDetail.getResources().getDrawable(R.drawable.ic_location_marker);
                finalMarker.setBounds(0, 0, finalMarker.getIntrinsicWidth(), finalMarker.getIntrinsicHeight());
                List<Overlay> listOfOverlays = mapLocation.getOverlays();
                listOfOverlays.add(new Mappin(finalMarker, new GeoPoint(intLatitude, intLongitude), this.objDetail.getApplicationContext(), objDetails.getPlace()));

                mapLocation.invalidate();

        	} catch (IOException e) {

        	    e.printStackTrace();

        	}

        }

    }

}