package es.upm.trailblazer.map.path;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;

public class PathTracker {

    private ArrayList<GeoPoint> waypoints;
    private Polyline roadOverlay;
    private MapView mapView;


    public PathTracker(Context context, MapView mapView) {
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        this.mapView = mapView;
        this.roadOverlay = new Polyline(mapView);
        this.waypoints = new ArrayList<GeoPoint>();
    }

    public void onLocationChanged(Location location, Boolean recording) {
        if (recording) {
            addPoint(location.getLatitude(), location.getLongitude());
            deletePolylineOverlay();
            roadOverlay.setPoints(waypoints);
            this.mapView.getOverlays().add(roadOverlay);
            this.mapView.invalidate();
        }
    }

    public void removeRouteRecorded() {
        deletePolylineOverlay();
        waypoints = new ArrayList<GeoPoint>();
    }

    public void deletePolylineOverlay() {
        if (mapView.getOverlays().contains(roadOverlay)) {
            mapView.getOverlays().remove(roadOverlay);
        }
    }

    private void addPoint(double latitude, double longitude) {
        GeoPoint point = new GeoPoint(latitude, longitude);
        waypoints.add(point);
    }

    public ArrayList<GeoPoint> getRouteDone() {
        return waypoints;
    }
}
