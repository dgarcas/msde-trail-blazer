package es.upm.trailblazer.map.path;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Route implements Serializable {


    private ArrayList<GeoPoint> points;
    private List<Float> speedKmH;

    public Route(ArrayList<GeoPoint> points, List<Float> speedKmH) {
        this.points = points;
        this.speedKmH = speedKmH;
    }

    public ArrayList<GeoPoint> getPoints() {
        return points;
    }

    public List<Float> getSpeedKmH() {
        return speedKmH;
    }

    public void addSpeedKmH(float speed) {
        speedKmH.add(speed);
    }

    public void setSpeedKmH(List<Float> speedKmH) {
        this.speedKmH = speedKmH;
    }
}
