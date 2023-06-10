package es.upm.trailblazer.map.requester.schemas;

public class Feature {
    private String type;
    private String id;
    Geometry GeometryObject;
    Properties PropertiesObject;


    // Getter Methods

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Geometry getGeometry() {
        return GeometryObject;
    }

    public Properties getProperties() {
        return PropertiesObject;
    }

    // Setter Methods

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGeometry(Geometry geometryObject) {
        this.GeometryObject = geometryObject;
    }

    public void setProperties(Properties propertiesObject) {
        this.PropertiesObject = propertiesObject;
    }
}

