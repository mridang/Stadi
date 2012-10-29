package com.mridang.stadi.events.details.structures;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

/*
 * This class is used like a structure to contain a
 * result's details. We can also serialise this.
 */
public class Details implements Serializable {

    /*
	 * A serialization identifier
	 */
	private static final long serialVersionUID = -1581596813419882381L;
	/*
     * The name of the event
     */
    private final String strName;
    /*
     * The price of the event
     */
    private final String strPrice;
    /*
     * The address of the event
     */
    private final String strAddress;
    /*
     * The address of the event
     */
    private final String strCity;
    /*
     * The information of the event
     */
    private final String strInformation;
    /*
     * The image of the event
     */
    private final URI uriImage;
    /*
     * The ticket of the event
     */
    private final URI uriTickets;
    /*
     * The date of the event
     */
    private final Date datDate;
    /*
     * The place of the event
     */
    private final String strPlace;
    /*
     * The webpage of the event
     */
    private final URI uriWebpage;

    /*
     * This returns the name of the event
     *
     * @return The name of the event
     */
    public String getName() {

        return this.strName;

    }

    /*
     * This returns the price of the event
     *
     * @return The price of the event
     */
    public String getPrice() {

        return this.strPrice;

    }

    /*
     * This returns the address of the event
     *
     * @return The address of the event
     */
    public String getAddress() {

        return this.strAddress;

    }

    /*
     * This returns the city of the event
     *
     * @return The city of the event
     */
    public String getCity() {

        return this.strCity;

    }

    /*
     * This returns the webpage of the event
     *
     * @return The webpage of the event
     */
    public URI getWebpage() {

        return this.uriWebpage;

    }

    /*
     * This initialises the instance variables
     *
     * @param  strName        The name of the event
     * @param  strPlace       The place of the event
     * @param  strPrice       The price of the event
     * @param  strAddress     The address of the event
     * @param  strCity        The city of the event
     * @param  strInformation The information about the event
     * @param  uriImage       The image of the event
     * @param  uriTickets     The tickets of the event
     * @param  datDate        The date of the event
     */
    public Details(String strName, String strPlace, String strPrice,
            String strAddress, String strCity, String strInformation,
            URI uriImage, URI uriTickets, Date datDate, URI uriWebpage) throws Exception {

        if (strName == null) {
            throw new Exception("Missing event name.");
        } else {
            this.strName = strName;
        }

        if (strPlace == null) {
            throw new Exception("Missing place name.");
        } else {
            this.strPlace = strPlace;
        }

        if (strPrice == null) {
            this.strPrice = ""; //Fetch localised string
        } else {
            this.strPrice = strPrice;
        }

        if (strAddress == null) {
            this.strAddress = ""; //Fetch localised string
        } else {
            this.strAddress = strAddress;
        }

        if (strCity == null) {
            throw new Exception("Missing city name.");
        } else {
            this.strCity = strCity;
        }

        if (strInformation == null) {
            this.strInformation = "";
        } else {
            this.strInformation = strInformation;
        }

        if (uriImage == null) {
            this.uriImage = null;
        } else {
            this.uriImage = uriImage;
        }

        if (uriTickets == null) {
            this.uriTickets = null;
        } else {
            this.uriTickets = uriTickets;
        }

        if (datDate == null) {
            throw new Exception("Missing event date");
        } else {
            this.datDate = datDate;
        }

        if (uriWebpage == null) {
            throw new Exception("Missing event webpage");
        } else {
            this.uriWebpage = uriWebpage;
        }

    }

    /*
     * This returns the information of the event
     *
     * @return The information about the event
     */
    public String getInformation() {

        return this.strInformation;

    }

    /*
     * This returns the image of the event
     *
     * @return The image of the event
     */
    public URI getImage() {

        return this.uriImage;

    }

    /*
     * This returns the tickets of the event
     *
     * @return The tickets of the event
     */
    public URI getTickets() {

        return this.uriTickets;

    }

    /*
     * This returns the date of the event
     *
     * @return The date of the event
     */
    public Date getDate() {

        return this.datDate;

    }

    /*
     * This returns the place of the event
     *
     * @return The place of the event
     */
    public String getPlace() {

        return this.strPlace;

    }

}