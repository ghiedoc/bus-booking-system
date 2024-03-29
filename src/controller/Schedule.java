/*
 * Model class for the tables.
 */
package controller;

/**
 *
 * @author Gillian
 */
public class Schedule {

    private String bus_no;
    private String bus_destination;
    private String bus_source;
    private String bus_time;
    private String bus_date;
    private String bus_type;
    private String bus_seat;
    private String bus_price;

    public Schedule(String bus_no, String bus_time, String bus_destination, String bus_seat, String bus_price, String bus_type) {
        this.bus_no = bus_no;
        this.bus_time = bus_time;
        this.bus_destination = bus_destination;
        this.bus_seat = bus_seat;
        this.bus_price = bus_price;
        this.bus_type = bus_type;
    }

    public Schedule(String bus_no, String bus_date, String bus_time, String bus_type, String bus_destination, String bus_seat, String bus_price) {
        this.bus_no = bus_no;
        this.bus_date = bus_date;
        this.bus_time = bus_time;
        this.bus_type = bus_type;
        this.bus_destination = bus_destination;
        this.bus_seat = bus_seat;
        this.bus_price = bus_price;
    }

    public Schedule(String bus_no, String bus_time, String bus_destination, String bus_seat, String bus_price) {
        this.bus_no = bus_no;
        this.bus_time = bus_time;
        this.bus_destination = bus_destination;
        this.bus_seat = bus_seat;
        this.bus_price = bus_price;
    }

    public Schedule(String bus_no, String bus_destination, String bus_source, String bus_time, String bus_date, String bus_type, String bus_seat, String bus_price) {
        this.bus_no = bus_no;
        this.bus_destination = bus_destination;
        this.bus_source = bus_source;
        this.bus_time = bus_time;
        this.bus_date = bus_date;
        this.bus_type = bus_type;
        this.bus_seat = bus_seat;
        this.bus_price = bus_price;

    }

    public Schedule(String bus_date) {
        this.bus_date = bus_date;
    }

    public String getBus_date() {
        return bus_date;
    }

    public void setBus_date(String bus_date) {
        this.bus_date = bus_date;
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public String getBus_time() {
        return bus_time;
    }

    public void setBus_time(String bus_time) {
        this.bus_time = bus_time;
    }

    public String getBus_destination() {
        return bus_destination;
    }

    public void setBus_destination(String bus_destination) {
        this.bus_destination = bus_destination;
    }

    public String getBus_seat() {
        return bus_seat;
    }

    public void setBus_seat(String bus_seat) {
        this.bus_seat = bus_seat;
    }

    public String getBus_price() {
        return bus_price;
    }

    public void setBus_price(String bus_price) {
        this.bus_price = bus_price;
    }

    public String getBus_type() {
        return bus_type;
    }

    public void setBus_type(String bus_type) {
        this.bus_type = bus_type;
    }

    public String getBus_source() {
        return bus_source;
    }

    public void setBus_source(String bus_source) {
        this.bus_source = bus_source;
    }

}
