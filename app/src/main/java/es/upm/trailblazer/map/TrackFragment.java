package es.upm.trailblazer.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import es.upm.trailblazer.R;

public class TrackFragment extends Fragment {

    private MapView map;
    private MyLocationNewOverlay mLocationOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private RotationGestureOverlay mRotationGestureOverlay;
    private FloatingActionButton gpsActionButton, recordButton;
    private boolean recording;

    public TrackFragment() {
        recording = false;
    }


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

    }

    private void onClickListenerRecordButton(View v) {
        FloatingActionButton actionButton = (FloatingActionButton) v;
        if(recording){
            recording = false;
            actionButton.setImageResource(R.drawable.stop);
        }else {
            recording = true;
            actionButton.setImageResource(R.drawable.record);
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

        mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity()), map);
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