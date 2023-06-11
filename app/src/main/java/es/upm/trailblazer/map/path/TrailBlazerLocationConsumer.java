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
    private RoadManager roadManager ;

    public TrailBlazerLocationConsumer(MapView mapView, Context context) {

        super(mapView);
        pathTracker = new PathTracker(context);
        this.recording = false;
        this.waypoints = new ArrayList<GeoPoint>();
        roadManager = new OSRMRoadManager(context, "TrailBlazerLocationConsumer");
    }

    public TrailBlazerLocationConsumer(IMyLocationProvider myLocationProvider, MapView mapView,
                                       Context context) {
        super(myLocationProvider, mapView);
        pathTracker = new PathTracker(context);
        this.recording = false;
        this.waypoints = new ArrayList<GeoPoint>();
        roadManager = new OSRMRoadManager(context, "TrailBlazerLocationConsumer");
    }

    public Boolean getRecording() {
        return recording;
    }

    public void setRecording(Boolean recording) {
        this.recording = recording;
    }

    @Override
    public void onLocationChanged(Location location, IMyLocationProvider source) {
        super.onLocationChanged(location, source);

        if (recording) {
            waypoints.add(new GeoPoint(location.getLatitude(), location.getLongitude()));
            if(waypoints.size()>1) {
                Polyline roadOverlay = new Polyline(this.mMapView);
                roadOverlay.setPoints(waypoints);
                this.mMapView.getOverlays().add(roadOverlay);
                this.mMapView.invalidate();
            }
        } else {
            this.mMapView.getOverlays();
        }
    }
}
