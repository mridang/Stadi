package com.mridang.stadissa.events.structures;

import java.io.Serializable;
import java.util.Date;

import com.mridang.stadissa.events.enums.Category;
import com.mridang.stadissa.events.interfaces.Row;

/*
 * This class is used like a structure to contain a
 * result item. We can also serialise this.
 */
public class Event implements Row, Serializable {

    /*
     * A serialization identifier
     */
    private static final long serialVersionUID = 4185147217573208449L;
    /*
     * The name of the event
     */
    private final String strName;
    /*
     * The location of the event
     */
    private final String strLocation;
    /*
     * The date of the event
     */
    private final Date datDate;
    /*
     * The type of the event
     */
    private final Category catCategory;
    /*
     * The identifier of the event
     */
    private final String strIdentifier;

    /*
     * This returns the name.
     *
     * @return  the name of the event
     */
    public String getName() {

        return this.strName;

    }

    /*
     * This returns the location of the event
     *
     * @return  the location of the event
     */
    public String getLocation() {

        return this.strLocation;

    }

    /*
     * This returns the release date
     *
     * @return  the date of the release
     */
    public Date getDate() {

        return this.datDate;

    }

    /*
     * This initialises the instance variables
     *
     * @return
     */
    public Event(Category catCategory, String strName, String strLocation,
            Date datDate, String strIdentifier) {

        this.catCategory = catCategory;
        this.strName = strName;
        this.strLocation = strLocation;
        this.datDate = datDate;
        this.strIdentifier = strIdentifier;

    }

    /*
     * This returns the category of the event
     *
     * @return  the category of the event
     */
    public Category getCategory() {

        return this.catCategory;

    }

    /*
     * This returns the identifier of the event
     *
     * @return  the identifier of the event
     */
    public String getIdentifier() {

        return this.strIdentifier;

    }

}