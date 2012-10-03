package com.mridang.stadissa.events;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mridang.stadissa.R;
import com.mridang.stadissa.events.adapters.EventsAdapter;
import com.mridang.stadissa.events.asyncloaders.Scraper;
import com.mridang.stadissa.events.details.Detail;
import com.mridang.stadissa.events.structures.Event;

/*
 * This is a fragment that will list the events for
 * a given week number.
 */
public class Events extends Fragment implements LoaderCallbacks<ArrayList<Event>> {

    /*
     * The week number for which events are being fetched
     */
    private Integer intWeek;

    /*
     * This is a custom static constructor that will accept
     * a week number and return a fragment with the events for the week.
     *
     * @param  intWeek  The week number for which to fetch events
     * @return An instance of the current Fragment
     */
    public static Events newInstance(Integer intWeek) {

        Events pageFragment = new Events();
        pageFragment.intWeek = intWeek;
        pageFragment.setArguments(new Bundle());
        return pageFragment;

    }

    /*
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.events, null);

    }

    /*
     * @see android.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(this.intWeek, savedInstanceState, this);

    }

    /*
     * @see android.support.v4.app.LoaderManager.LoaderCallbacks#onCreateLoader(int, android.os.Bundle)
     */
    public Loader<ArrayList<Event>> onCreateLoader(int intLoader, Bundle bndBundle) {

        return new Scraper(getActivity().getApplicationContext());

    }

    /*
     * @see android.support.v4.app.LoaderManager.LoaderCallbacks#onLoadFinished(android.support.v4.content.Loader, java.lang.Object)
     */
    public void onLoadFinished(Loader<ArrayList<Event>> ldrEvents, final ArrayList<Event> lstEvents) {

        if (lstEvents.size() == 0) {

            Toast.makeText(this.getActivity().getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();

        } else {

            ListView lvwEvents = (ListView) getView().findViewById(R.id.events);
            final EventsAdapter objAdapter = new EventsAdapter(this, lstEvents);
            lvwEvents.setAdapter(objAdapter);

            lvwEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                    Intent ittDetail = new Intent(Events.this.getActivity().getApplicationContext(), Detail.class);
                    ittDetail.putExtra(Detail.strIdentifier, ((Event) objAdapter.lstItems.get(position)).getIdentifier());
                    ittDetail.putExtra("EVENT_NAME", ((Event) objAdapter.lstItems.get(position)).getName());
                    Events.this.startActivity(ittDetail);

                }

            });

            ProgressBar pbrProgress = (ProgressBar) getView().findViewById(R.id.progress);
            pbrProgress.setVisibility(View.GONE);

            LinearLayout lltLinear = (LinearLayout) getView().findViewById(R.id.scroll);
            lltLinear.setVisibility(View.VISIBLE);

        }

    }

    /*
     * @see android.support.v4.app.LoaderManager.LoaderCallbacks#onLoaderReset(android.support.v4.content.Loader)
     */
    public void onLoaderReset(Loader<ArrayList<Event>> ldrEvents) {

        return;

    }

    /*
     * @see android.support.v4.app.Fragment#onDestroy()
     */
    @Override
    public void onDestroy() {

        super.onDestroy();

        getLoaderManager().getLoader(this.intWeek).abandon();

    }

    /*
     * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    /*
     * This is a method that will return the localised string
     * for a string
     *
     * @param  strName  The name of the string to localise
     * @return A localised string
     */
    public String getString(String strName) {

        Context ctxContext = this.getActivity().getApplicationContext();
        String strPackage = ctxContext.getPackageName();
        Integer intResource = ctxContext.getResources().getIdentifier(strName, "string", strPackage);
        return ctxContext.getResources().getString(intResource);

    }

}