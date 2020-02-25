package com.example.curbside;

import java.util.ArrayList;

public class Company {

    private String name;
    private boolean legal;

    private User owner;
    private ArrayList<Truck> trucks;


    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
