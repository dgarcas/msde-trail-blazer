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

    public TrailBlazerLocationConsumer(IMyLocationProvider myLocationProvider, MapView mapView,
                                       Context context) {
        super(myLocationProvider, mapView);
        pathTracker = new PathTracker(context, mMapView);
        recording = false;
    }

    public Boolean getRecording() {
        return recording;
    }

    public void setRecording(Boolean recording) {
        this.recording = recording;
    }

    public void removeRouteRecorded(){
        pathTracker.removeRouteRecorded();
    }
    @Override
    public void onLocationChanged(Location location, IMyLocationProvider source) {
        super.onLocationChanged(location, source);
        pathTracker.onLocationChanged(location, recording);
    }
}
