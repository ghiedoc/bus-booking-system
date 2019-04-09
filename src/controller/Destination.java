/*
 * Model class for the table in the home tab
 */
package controller;

/**
 *
 * @author Gillian
 */
public class Destination {
    private String bus_destination;
    private String bus_source;

    public Destination(String bus_destination, String bus_source) {
        this.bus_destination = bus_destination;
        this.bus_source = bus_source;
    }

    public String getBus_destination() {
        return bus_destination;
    }

    public void setBus_destination(String bus_destination) {
        this.bus_destination = bus_destination;
    }

    public String getBus_source() {
        return bus_source;
    }

    public void setBus_source(String bus_source) {
        this.bus_source = bus_source;
    }
    
    
}
