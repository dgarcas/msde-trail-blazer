package es.upm.trailblazer.map.requester.schemas;

import java.util.ArrayList;

public class Geometry {
    private String type;
    ArrayList<Object> coordinates = new ArrayList<Object>();


    // Getter Methods

    public String getType() {
        return type;
    }

    // Setter Methods

    public void setType(String type) {
        this.type = type;
    }
}
