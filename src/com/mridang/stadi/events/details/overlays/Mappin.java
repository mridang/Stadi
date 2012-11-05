package com.mridang.stadi.events.details.overlays;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/*
 * This class is used to draw a custom marker on the map.
 */
public class Mappin extends Overlay {

    /*
     * The drawable resource of the marker
     */
    protected Drawable drwMarker;
    /*
     * The geopoint at which the marker is placed
     */
    protected GeoPoint gptCoordinates;
    /*
     * The context of the activity containing the map
     */
    protected Context ctxContext;
    /*
     * The name of the place
     */
    protected String strPlace;
    /*
     * The latitude at which the marker is placed
     */
    protected Double dblLatitude;
    /*
     * The longitude at which the marker is placed
     */
    protected Double dblLongitude;

    /*
     * Initializes this overlay
     *
     * @param  drwMarker       The drwable image to use as an overlay
     * @param  gptCoordinates  The location at which to display the marker
     */
    public Mappin(Drawable drwMarker, GeoPoint gptCoordinates, Context ctxContext, String strPlace) {

        this.drwMarker = drwMarker;
        this.gptCoordinates = gptCoordinates;
        this.ctxContext = ctxContext;
        this.strPlace = strPlace;
        this.dblLatitude = gptCoordinates.getLatitudeE6() / 1E6;
        this.dblLongitude = gptCoordinates.getLongitudeE6() / 1E6;

    }

    /*
     * @see com.google.android.maps.Overlay#draw(android.graphics.Canvas, com.google.android.maps.MapView, boolean)
     */
    @Override
    public void draw(Canvas canCanvas, MapView mapView, boolean booShadow) {

        super.draw(canCanvas, mapView, booShadow);

        Projection prjProjection = mapView.getProjection();

        Integer x;
        Integer y;

        if (!booShadow) {

            x = prjProjection.toPixels(gptCoordinates, null).x - (drwMarker.getIntrinsicWidth() / 2);
            y = prjProjection.toPixels(gptCoordinates, null).y - (drwMarker.getIntrinsicHeight());

        } else {

            Integer intSign = (SHADOW_X_SKEW > 0 ? 1 : -1);
            Float fltScaler = 1.1F - Math.abs(SHADOW_X_SKEW);
            x = (int) (prjProjection.toPixels(gptCoordinates, null).x - (intSign * (drwMarker.getIntrinsicWidth() * fltScaler)));
            y = (int) (prjProjection.toPixels(gptCoordinates, null).y - (drwMarker.getIntrinsicHeight() * SHADOW_Y_SCALE));

        }

        drawAt(canCanvas, drwMarker, x, y, booShadow);

    }

    /*
     * @see com.google.android.maps.Overlay#onTap(com.google.android.maps.GeoPoint, com.google.android.maps.MapView)
     */
    @Override
    public boolean onTap(GeoPoint gptLocation, MapView mapMap) {

        String strCoordinates = String.format("%f,%f", dblLatitude, dblLongitude);
        String strUri = String.format("geo:%s?z=14&q=%s(%s)", strCoordinates, strCoordinates, strPlace);
        Intent ittMap = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
        ittMap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.ctxContext.startActivity(ittMap);
        return false;

    }

}