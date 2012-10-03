package com.mridang.stadissa.events.adapters;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mridang.stadissa.R;
import com.mridang.stadissa.events.Events;
import com.mridang.stadissa.events.interfaces.Row;
import com.mridang.stadissa.events.managers.EventsManager;
import com.mridang.stadissa.events.structures.Event;
import com.mridang.stadissa.events.structures.Section;

/*
 * This is custom adapter for the releases listview.
 */
public class EventsAdapter extends BaseAdapter  {

    /*
     * The layout inflater for inflating the listview rows
     */
    private final LayoutInflater linInflater;
    /*
     * The array containing the rows
     */
    public ArrayList<Event> lstEvents;
    /*
     * The array containing the rows
     */
    public ArrayList<Row> lstItems;
    /*
     * The context of the calling class
     */
    private final Events objEvents;

    /*
     * Constructor
     */
    public EventsAdapter(Events objEvents, ArrayList<Event> lstEvents) {

        linInflater = LayoutInflater.from(objEvents.getActivity().getApplicationContext());
        this.objEvents = objEvents;
        this.lstEvents = lstEvents;
        this.lstItems = EventsManager.process(new ArrayList<Event>(this.lstEvents));

    }

    /*
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {

        return lstItems.size();

    }

    /*
     * @see android.widget.Adapter#getItem(int)
     */
    public Event getItem(int intPosition) {

        return (Event) lstItems.get(intPosition);

    }

    /*
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int intPosition) {

        return intPosition;

    }

    /*
     * @see android.widget.BaseAdapter#getViewTypeCount()
     */
    @Override
    public int getViewTypeCount() {

        return 2;

    }

    /*
     * @see android.widget.BaseAdapter#getItemViewType(int)
     */
    @Override
    public int getItemViewType(int intPosition) {

        return (this.lstItems.get(intPosition) instanceof Section) ? 0 : 1;

    }

    /*
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    public View getView(int intPosition, View vewView, ViewGroup vgrParent) {

        if (this.lstItems.get(intPosition) instanceof Section) {

            View vewSection = linInflater.inflate(R.layout.category, null);
            Section objSection = (Section) this.lstItems.get(intPosition);
            vewSection.setOnClickListener(null);
            vewSection.setOnLongClickListener(null);
            vewSection.setLongClickable(false);
            TextView tvwTitle = (TextView) vewSection.findViewById(R.id.text);
            tvwTitle.setText(objSection.getName());

            return vewSection;

        } else {

            View vewRow = linInflater.inflate(R.layout.row, null);
            Event objEvent = (Event) this.lstItems.get(intPosition);
            TextView tvwName = (TextView) vewRow.findViewById(R.id.name);
            tvwName.setText(objEvent.getName());
            TextView tvwLocation = (TextView) vewRow.findViewById(R.id.location);
            tvwLocation.setText(objEvent.getLocation());
            TextView tvwCategory = (TextView) vewRow.findViewById(R.id.category);
            tvwCategory.setText(this.objEvents.getString(objEvent.getCategory().name().toLowerCase()));
            vewRow.setTag(objEvent.getIdentifier());

            return vewRow;

        }

    }

}