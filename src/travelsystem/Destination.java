/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelsystem;

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
