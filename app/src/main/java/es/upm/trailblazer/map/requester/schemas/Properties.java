package es.upm.trailblazer.map.requester.schemas;

public class Properties {
    private String xid;
    private String name;
    private float dist;
    private float rate;
    private String osm;
    private String kinds;


    // Getter Methods

    public String getXid() {
        return xid;
    }

    public String getName() {
        return name;
    }

    public float getDist() {
        return dist;
    }

    public float getRate() {
        return rate;
    }

    public String getOsm() {
        return osm;
    }

    public String getKinds() {
        return kinds;
    }

    // Setter Methods

    public void setXid(String xid) {
        this.xid = xid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setOsm(String osm) {
        this.osm = osm;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }
}
