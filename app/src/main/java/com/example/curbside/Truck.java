package com.example.curbside;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Truck implements Serializable {

    private String name;
    private int id;
    private String bio;
    private Company company;
    private String hours;
    private String[] hoursFormatted;
    private Double lat,lng,distance;


    public Truck(String name, Company company) {
        this.name = name;
        this.company = company;
        this.hours = "9:00am-9:00pm";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getDistance() {
        if (distance != null) {
            DecimalFormat formatted = new DecimalFormat("####0.0");
            Double result = Double.valueOf(formatted.format(distance));
            return result;
        } else {
            return -1.0;
        }
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getHoursFormatted() {
        hoursFormatted = getHours().split("-");
        SimpleDateFormat formatted = new SimpleDateFormat("hh:mm");
        String[] hours = new String[2];
        hours[0] = formatted.format(hoursFormatted[0]);
        hours[1] = formatted.format(hoursFormatted[1]);
        String hoursResolved = hours[0] + " - " + hours[1];
        return hoursResolved;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
