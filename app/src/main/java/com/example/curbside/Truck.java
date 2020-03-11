package com.example.curbside;

import java.io.Serializable;
import java.util.ArrayList;

public class Truck implements Serializable {

    private String name;
    private String bio;
    private Company company;
    private ArrayList<FoodMenu> menus;
    private String hours;
    private double lat,lng,distance;


    public Truck(String name, Company company) {
        this.name = name;
        this.company = company;
        this.hours = "9:00am-9:00pm";
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
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
    public void addMenu(FoodMenu menu) {
        menus.add(menu);
    }
    public ArrayList<FoodMenu> getMenus() {
        return this.menus;
    }
}
