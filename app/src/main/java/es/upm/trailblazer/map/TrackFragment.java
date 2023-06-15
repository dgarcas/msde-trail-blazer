package es.upm.trailblazer.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.upm.trailblazer.R;
import es.upm.trailblazer.RouteResumeActivity;
import es.upm.trailblazer.map.path.PathTracker;
import es.upm.trailblazer.map.path.Route;
import es.upm.trailblazer.map.path.TrailBlazerLocationConsumer;
import es.upm.trailblazer.map.requester.Requester;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackFragment extends Fragment {

    private MapView map;
    private TrailBlazerLocationConsumer mLocationOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private RotationGestureOverlay mRotationGestureOverlay;
    private FloatingActionButton gpsActionButton, recordButton;
    private Callback callback;
    private Requester requester;
    private GpsMyLocationProvider gpsMyLocationProvider;

    public TrackFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_track, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity().getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        gpsActionButton = getActivity().findViewById(R.id.floatingActionButton);
        map = getActivity().findViewById(R.id.map_view);
        recordButton = getActivity().findViewById(R.id.record_button);

        checkLocationPermissions();
        setMapConfigurations();

        gpsActionButton.setOnClickListener(v -> onClickListenerGPSButton());
        recordButton.setOnClickListener(v -> onClickListenerRecordButton(v));
        map.setOnTouchListener((v, event) -> setOnTouchListener());
        setPointsOfInterest();
    }

    private void setPointsOfInterest() {
        this.requester = new Requester();
        setCallback();
        try {
            requester.getPlaces(40.42589154256929, -3.7125353659071387, callback);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCallback() {
        FolderOverlay poiMarkers = new FolderOverlay(getContext());
        callback = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                JsonArray jsonArray = ((JsonObject) response.body())
                        .getAsJsonArray("features");
                Drawable poiIcon = getResources().getDrawable(R.drawable.location);

                jsonArray.forEach(jsonElement -> {
                    Marker poiMarker = new Marker(map);
                    poiMarker.setTitle(String.valueOf(((JsonObject) jsonElement)
                            .getAsJsonObject("properties").get("name").getAsString()));
                    poiMarker.setPosition(
                            new GeoPoint(
                                    ((JsonObject) jsonElement).getAsJsonObject("geometry")
                                            .getAsJsonArray("coordinates").get(1).getAsDouble(),
                                    ((JsonObject) jsonElement).getAsJsonObject("geometry")
                                            .getAsJsonArray("coordinates").get(0).getAsDouble()));
                    poiMarker.setIcon(poiIcon);
                    if (!poiMarker.getTitle().isEmpty()) {
                        poiMarkers.add(poiMarker);
                    }
                });
                map.getOverlays().add(poiMarkers);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println("callback!!!");
                Log.e("callback", t.toString());
            }
        };
    }

    private void onClickListenerRecordButton(View v) {
        FloatingActionButton actionButton = (FloatingActionButton) v;
        if (mLocationOverlay.getRecording()) {
            mLocationOverlay.setRecording(false);
            actionButton.setImageResource(R.drawable.record);
            ArrayList<GeoPoint> points = mLocationOverlay.getRouteDone();
            List<Float > speedRegistry = mLocationOverlay.getSpeedRegistry();
            mLocationOverlay.removeRouteRecorded();

            Route route = new Route(points, speedRegistry);
            Intent intent = new Intent(getActivity(), RouteResumeActivity.class);
            intent.putExtra("route", route);
            startActivity(intent);

        } else {
            mLocationOverlay.setRecording(true);
            actionButton.setImageResource(R.drawable.stop);
        }
    }

    private boolean setOnTouchListener() {
        gpsActionButton.setImageResource(R.drawable.gps_not_fixed);
        return false;
    }

    private void onClickListenerGPSButton() {
        if (mLocationOverlay.isFollowLocationEnabled()) {
            mLocationOverlay.disableFollowLocation();
            gpsActionButton.setImageResource(R.drawable.gps_not_fixed);
        } else {
            mLocationOverlay.enableFollowLocation();
            map.getController().setZoom(20);
            gpsActionButton.setImageResource(R.drawable.gps_fixed);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    private void setMapConfigurations() {
        final DisplayMetrics dm = this.getResources().getDisplayMetrics();


        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(20);

        gpsMyLocationProvider = new GpsMyLocationProvider(getActivity());
        mLocationOverlay = new TrailBlazerLocationConsumer(gpsMyLocationProvider, map, getContext());
        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableFollowLocation();

        map.getOverlays().add(this.mLocationOverlay);

        //map scale
        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(this.mScaleBarOverlay);


        //support for map rotation
        mRotationGestureOverlay = new RotationGestureOverlay(map);
        mRotationGestureOverlay.setEnabled(true);
        map.getOverlays().add(this.mRotationGestureOverlay);
    }

    private void checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

    }
}