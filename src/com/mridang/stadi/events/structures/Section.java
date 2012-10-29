package com.mridang.stadi.events.structures;

import com.mridang.stadi.events.interfaces.Row;

/*
 * This class is used to indicate a category.
 */
public class Section implements Row {

    /*
     * The name of the category
     */
    private final String strName;

    /*
     * This initializes the instance variables
     *
     * @return
     */
    public Section(String strName) {

        this.strName = strName;

    }

    /*
     * This returns the name.
     *
     * @return  the name of the category
     */
    public String getName() {

        return this.strName;

    }

}
