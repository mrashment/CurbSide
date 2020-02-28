package com.example.curbside;

import java.io.Serializable;
import java.util.ArrayList;

public class Company implements Serializable {

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
