package es.upm.trailblazer.map.path;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Polyline;

import android.content.Context;

import java.util.ArrayList;

public class PathTracker {

    private static final String MY_USER_AGENT = "PathTracker";
    private RoadManager roadManager;
    private ArrayList<GeoPoint> waypoints;

    public PathTracker (Context context) {
        this.roadManager = new OSRMRoadManager(context, MY_USER_AGENT);
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
    }

    public void addPoint (double latitude, double longitude){
        GeoPoint point = new GeoPoint(latitude, longitude);
        waypoints.add(point);
    }

    public Polyline getRouteOverlay (){
        Road road = roadManager.getRoad(waypoints);
        return RoadManager.buildRoadOverlay(road);
    }
}
