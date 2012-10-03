package com.mridang.stadissa.events.details.overlays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.mridang.stadissa.R;

/*
 * This class is used to draw a custom marker on the map.
 */
public class Mappin extends com.google.android.maps.Overlay {

    /*
     * The location at which to display the marker
     */
    private final GeoPoint geoPoint;
    /*
     * The context of the activity containing the map
     */
    private final Context ctxContext;

    /*
     * Initializes this task
     *
     * @param  geoPoint  The location at which to display the marker
     */
    public Mappin(Context ctxContext, GeoPoint geoPoint) {

        super();

        this.geoPoint = geoPoint;
        this.ctxContext = ctxContext;

    }

    /*
     * @see com.google.android.maps.Overlay#onTouchEvent(android.view.MotionEvent, com.google.android.maps.MapView)
     */
    @Override
    public boolean onTouchEvent(MotionEvent mevEvent, MapView mvwMap) {

        System.out.println("clicked");
        return super.onTouchEvent(mevEvent, mvwMap);

    }

    /*
     * @see com.google.android.maps.Overlay#draw(android.graphics.Canvas,
     * com.google.android.maps.MapView, boolean, long)
     */
    @Override
    public boolean draw(Canvas canCanvas, MapView mvwMap, boolean booShadow, long lngWhen) {

        super.draw(canCanvas, mvwMap, booShadow);

        Point screenPts = new Point();
        mvwMap.getProjection().toPixels(this.geoPoint, screenPts);

        Bitmap bmp = BitmapFactory.decodeResource(this.ctxContext.getResources(), R.drawable.ic_location_marker);
        canCanvas.drawBitmap(bmp, screenPts.x, screenPts.y - 60, null);

        return true;

    }

}