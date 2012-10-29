package com.mridang.stadi.events.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mridang.stadi.events.helpers.Dates;
import com.mridang.stadi.events.interfaces.Row;
import com.mridang.stadi.events.structures.Event;
import com.mridang.stadi.events.structures.Section;

/*
 * This class is the manager for the results and contains the methods
 * for sorting and grouping the results
 */
public class EventsManager {

    /*
     * This sort and group the events by date
     *
     * @param  objResults         the list of results to sort
     * @return ArrayList<Row>     The list of events, sorted and grouped
     */
    public static ArrayList<Row> process(ArrayList<Event> lstEvents) {

        Iterator<Event> itrEvents = lstEvents.iterator();

        while (itrEvents.hasNext()) {
            Event objEvent = itrEvents.next();
            if (objEvent.getDate().before(new Date()))
                itrEvents.remove();
        }

        Collections.sort(lstEvents, new Comparator<Event>() {
            public int compare(Event objResultA, Event objResultB) {
                return objResultA.getDate().compareTo(objResultB.getDate());
            }
        });

        LinkedHashMap<String, ArrayList<Event>> mapResults = new LinkedHashMap<String, ArrayList<Event>>();

        for (Event objEvent : lstEvents) {
            if (mapResults.get(Dates.print(objEvent.getDate())) == null)
                mapResults.put(Dates.print(objEvent.getDate()), new ArrayList<Event>());
            mapResults.get(Dates.print(objEvent.getDate())).add(objEvent);
        }

        ArrayList<Row> lstRows = new ArrayList<Row>();

        for (Map.Entry<String, ArrayList<Event>> mapEvents : mapResults.entrySet()) {
            lstRows.add(new Section(mapEvents.getKey().toString()));
            for (Event objResult : mapEvents.getValue())
                lstRows.add(objResult);
        }

        return lstRows;

    }

}