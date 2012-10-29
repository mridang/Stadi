package com.mridang.stadi.events.enums;

/*
 * This is a list of categories under which the events
 * are categorised.
 */
public enum Category {

    MUSIC, SPORT, THEATRE, OTHER;

    /*
     * This methods returns the enum corresponding to a string
     *
     * @param   strCategory  The string description of the event
     * @return  The enum associted with the string
     */
    public static Category getEnum(String strCategory) {

        //TODO Check why some records arent being categorized properly.
        if (strCategory.toUpperCase().equals("MUSIIKKI")) {
            return MUSIC;
        } else if (strCategory.toUpperCase().equals("URHEILU")) {
            return SPORT;
        } else if (strCategory.toUpperCase().equals("TEATTERI")) {
            return THEATRE;
        } else {
            return OTHER;
        }

    }

}