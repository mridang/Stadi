package com.mridang.stadissa.events.details.scrapers;

import java.net.URI;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.util.Log;

import com.mridang.stadissa.events.details.Detail;
import com.mridang.stadissa.events.details.helpers.Dates;
import com.mridang.stadissa.events.details.structures.Details;
import com.mridang.stadissa.helpers.Network;

/*
 * This class is used to scrape Stadissa
 */
public class Stadissa {

    /* The URL of the scrape page */
    private final String SCRAPE_URL = "http://www.stadissa.fi/tapahtumat/%s/";

    /*
     * Initializes this task
     *
     * @param objContext the instance of the calling Search class
     */
    public Stadissa(Detail objDetail) {

        super();

    }

    /*
     * This method is the method that fetches the event details.
     *
     * @param  intIdentifier  The unique identifier of the event
     * @return The details of the event that corresponds to the passed identifier
     */
    public Details doFetch(String strIdentifier) throws Exception {

        Document objDocument;
        Details objDetail = null;
        Network objNetwork = new Network();
        String strUrl = String.format(SCRAPE_URL, strIdentifier);

        Log.d("scrapers.Details", String.format("Identifier: #%s", strIdentifier));
        Log.d("scrapers.Details", String.format("URL: %s", strUrl));

        try {

            Log.d("scrapers.Details", "Fetching page");
            objDocument = Jsoup.parse(objNetwork.doGet(strUrl, null), strUrl);

        } catch (Exception e) {
            Log.w("scrapers.Details", "Error fetching and parsing page", e);
            return null;
        }

        try {

            String strName;
            try {
            	strName = objDocument.select("div#eventTitle").get(0).text();
            } catch (Exception e) {
            	strName = null;
            }

            String strPrice;
            try {
            	strPrice = objDocument.select("div#eventPrice").get(0).text().split(":\\s", 2)[1];
            } catch (Exception e) {
            	strPrice = null;
            }

            URI uriTickets;
            try {
            	 uriTickets = new URI(objDocument.select("div#eventTicket a").get(0).attr("abs:href"));
            } catch (Exception e) {
            	uriTickets = null;
            }

            URI uriImage;
            try {
            	uriImage = new URI(objDocument.select("div#eventImage img").get(0).attr("src"));
            } catch (Exception e) {
            	uriImage = null;
            }

            String strPlace;
            try {
            	strPlace = objDocument.select("div#eventPlaceTitle").get(0).text();
            } catch (Exception e) {
            	strPlace = null;
            }

            String strAddress;
            try {
            	strAddress = objDocument.select("div#eventPlaceAddress span").get(0).text();
            } catch (Exception e) {
            	strAddress = null;
            }

            String strCity;
            try {
            	strCity = objDocument.select("div#eventPlaceAddress span").get(1).text();
            } catch (Exception e) {
            	strCity = null;
            }

            String strInformation;
            try {
            	strInformation = objDocument.select("div#eventDescription div").get(0).html();
            } catch (Exception e) {
            	strInformation = null;
            }

            Date datDate;
            try {
            	datDate = Dates.parse(objDocument.select("div#eventOccurrences span.date").get(0).text()
                        , objDocument.select("div#eventOccurrences span.time").get(0).text());
            } catch (Exception e) {
            	try {
            		datDate = Dates.parse(objDocument.select("div#eventOccurrences span.date").get(0).text(), "klo 0:00");
            	} catch (Exception f) {
            		datDate = null;
            	}
            }

            URI uriWebpage;
            try {
                uriWebpage = URI.create(strUrl);
            } catch (Exception e) {
                uriWebpage = null;
            }

            objDetail = new Details(strName, strPlace, strPrice, strAddress, strCity, strInformation, uriImage, uriTickets, datDate, uriWebpage);

        } catch (Exception e) {

            Log.w("scrapers.Details", "Error parsing row", e);

        }

        return objDetail;

    }

}