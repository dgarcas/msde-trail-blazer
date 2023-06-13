package es.upm.trailblazer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class RouteResumeActivity extends AppCompatActivity {

    MapView mapView;
    Polyline polyline;
    TextView date, distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_resume);

        mapView = findViewById(R.id.map_view_resume);
        date = findViewById(R.id.data_value);
        distance = findViewById(R.id.distance_value);

        Intent intent = getIntent();
        ArrayList<GeoPoint> points = (ArrayList<GeoPoint>) intent.getSerializableExtra("polyline");

        Polyline polyline = new Polyline(mapView);
        polyline.setPoints(points);
        mapView.getOverlays().add(polyline);
        mapView.invalidate();

        this.polyline = polyline;
        centerMap(polyline.getPoints());
        distance.setText(String.valueOf(polyline.getDistance()));

    }
    private void centerMap(List<GeoPoint> points) {
        double maxLatitude = -90.0;
        double minLatitude = 90.0;
        double maxLongitude = -180.0;
        double minLongitude = 180.0;

        for (GeoPoint point : points) {
            double latitude = point.getLatitude();
            double longitude = point.getLongitude();

            if (latitude > maxLatitude) {
                maxLatitude = latitude;
            }

            if (latitude < minLatitude) {
                minLatitude = latitude;
            }

            if (longitude > maxLongitude) {
                maxLongitude = longitude;
            }

            if (longitude < minLongitude) {
                minLongitude = longitude;
            }
        }

        double centerLatitude = (maxLatitude + minLatitude) / 2;
        double centerLongitude = (maxLongitude + minLongitude) / 2;

        mapView.getController().setZoom(20);
        mapView.getController().setCenter(new GeoPoint(centerLatitude, centerLongitude));

    }


}