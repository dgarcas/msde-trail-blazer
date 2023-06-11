package es.upm.trailblazer.map.path;

import android.content.Context;
import android.location.Location;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class TrailBlazerLocationConsumer extends MyLocationNewOverlay {

    private PathTracker pathTracker;
    private Boolean recording;
    private ArrayList<GeoPoint> waypoints;
    private RoadManager roadManager;
    Polyline roadOverlay;

    public TrailBlazerLocationConsumer(IMyLocationProvider myLocationProvider, MapView mapView,
                                       Context context) {
        super(myLocationProvider, mapView);
        pathTracker = new PathTracker(context);
        this.recording = false;
        this.waypoints = new ArrayList<GeoPoint>();
        this.roadOverlay = new Polyline(this.mMapView);
        roadManager = new OSRMRoadManager(context, "TrailBlazerLocationConsumer");
    }

    public Boolean getRecording() {
        return recording;
    }

    public void setRecording(Boolean recording) {
        this.recording = recording;
    }

    public void removeRouteRecorded(){
        deletePolylineOverlay();
        waypoints =  new ArrayList<GeoPoint>();
    }
    public void deletePolylineOverlay(){
        if(mMapView.getOverlays().contains(roadOverlay)){
            mMapView.getOverlays().remove(roadOverlay);
        }
    }
    @Override
    public void onLocationChanged(Location location, IMyLocationProvider source) {
        super.onLocationChanged(location, source);

        if (recording) {
            waypoints.add(new GeoPoint(location.getLatitude(), location.getLongitude()));
            if (waypoints.size() > 1) {
                deletePolylineOverlay();
                roadOverlay.setPoints(waypoints);
                this.mMapView.getOverlays().add(roadOverlay);
                this.mMapView.invalidate();
            }
        } else {
            this.mMapView.getOverlays();
        }
    }
}
