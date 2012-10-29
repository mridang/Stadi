package com.mridang.stadi.events.details.overlays;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/*
 * This class is used to draw a custom marker on the map.
 */
public class Mappin extends Overlay {

    protected Drawable drwMarker;
    protected GeoPoint gptCoordinates;

    /*
     * Initializes this overlay
     *
     * @param  drwMarker       The drwable image to use as an overlay
     * @param  gptCoordinates  The location at which to display the marker
     */
    public Mappin(Drawable drwMarker, GeoPoint gptCoordinates) {

        this.drwMarker = drwMarker;
        this.gptCoordinates = gptCoordinates;

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

}