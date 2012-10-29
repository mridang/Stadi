package com.mridang.stadi.events.asyncloaders;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.mridang.stadi.events.enums.Category;
import com.mridang.stadi.events.helpers.Dates;
import com.mridang.stadi.events.structures.Event;
import com.mridang.stadi.helpers.Network;

/*
 * This class is used to scrape Stadissa
 */
public class Scraper extends AsyncTaskLoader<ArrayList<Event>> {

    /*
     * The URL of the scrape page
     */
    private final String SEARCH_URL = "http://www.stadissa.fi/index.php?date=%s";
    /*
     * This contains a cached copy of the results.
     */
    private ArrayList<Event> lstEvents;

    /*
     * This method initializes the scraper.
     *
     * @param  context  The context of the calling class
     */
    public Scraper(Context context) {

        super(context);

    }

    /*
     * This method is the method that scrapes the events.
     *
     * @param  intWeek The week number for which to fetch events
     * @return A list of events for the passed week
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Event> loadInBackground() {

        // Let's load the data from a serialized format if it already exists
        try {

            if(this.getContext().getFileStreamPath("w" + Integer.toString(this.getId())).exists()) {

                Log.d("scrapers.Stadissa", "Serialized data found. Loading data.");

                FileInputStream fisSerialize = this.getContext().openFileInput("w" + Integer.toString(this.getId()));
                ObjectInputStream oisSerialize = new ObjectInputStream(fisSerialize);
                this.lstEvents = (ArrayList<Event>) oisSerialize.readObject();
                oisSerialize.close();
                fisSerialize.close();

                return this.lstEvents;

            }

        } catch (Exception e) {
            Log.w("scrapers.Stadissa", "Unable to load serialized data", e);
        }

        Document objDocument;
        ArrayList<Event> objEvents = new ArrayList<Event>();
        Network objNetwork = new Network();
        String strUrl = String.format(SEARCH_URL, new SimpleDateFormat("yyyy-MM-dd").format(Dates.fetch(getId())));

        Log.d("scrapers.Stadissa", String.format("Week: #%s", getId()));
        Log.d("scrapers.Stadissa", String.format("URL: %s", strUrl));

        try {

            Log.d("scrapers.Stadissa", "Fetching page");
            objDocument = Jsoup.parse(objNetwork.doGet(strUrl, null), strUrl);

        } catch (Exception e) {
            Log.w("scrapers.Stadissa", "Error fetching and parsing page", e);
            return null;
        }

        String strName;
        Date datDate;
        String strLocation;
        Category catType;
        String strIdentifier;
        Pattern patRegex = Pattern.compile("\\s\\|\\s");

        for (Element eleDate : objDocument.select("div.calendarday")) {

            for (Element eleEvent : eleDate.select("div.calendarevent")) {

                Log.d("scrapers.Stadissa", "Found a row");

                try {

                    strName = eleEvent.select("a").text();

                    datDate = Dates.parse(eleDate.select("div.day").text(),
                                  eleDate.select("div.month").text(),
                                  eleDate.select("div.year").text(),
                                  eleEvent.select("div.calendareventtime").text().replaceAll("[^0-9]+", ""));

                    strLocation = eleEvent.select("a[title~=.*?|.*]").attr("title").split(patRegex.pattern())[1];

                    catType = Category.getEnum(objDocument
                                .select("div#calendarheader > div")
                                .get(eleEvent.parent().elementSiblingIndex())
                                .text());

                    strIdentifier = eleEvent.select("a").attr("abs:href").split("\\/")[4];

                    objEvents.add(new Event(catType, strName, strLocation, datDate, strIdentifier));

                } catch (Exception e) {
                    Log.w("scrapers.Stadissa", "Error parsing row", e);
                }

            }

        }

        Log.d("scrapers.Stadissa", String.format("Scraped %d rows", objEvents.size()));

        Collections.reverse(objEvents);

        this.lstEvents = objEvents;


        // Let's save the data in a serialized format if it doesn't exist
        try {

            if (!this.getContext().getFileStreamPath("w" + Integer.toString(this.getId())).exists()) {

                Log.d("scrapers.Stadissa", "Serialized data not found. Saving data.");

                FileOutputStream fosSerialize = this.getContext().openFileOutput("w" + Integer.toString(this.getId()), Context.MODE_PRIVATE);
                ObjectOutputStream oosSerialize = new ObjectOutputStream(fosSerialize);
                oosSerialize.writeObject(this.lstEvents);
                oosSerialize.close();
                fosSerialize.close();

            }

        } catch (Exception e) {
            Log.w("scrapers.Stadissa", "Unable to save serialized data", e);
        }

        return objEvents;

    }

    /*
     * @see android.support.v4.content.Loader#onStartLoading()
     */
    @Override
    protected void onStartLoading() {

        if (this.lstEvents != null) {
            deliverResult(this.lstEvents);
        }

        if (this.lstEvents == null) {
            forceLoad();
        }

    }

}