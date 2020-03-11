package com.example.curbside;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayList;

public class Company implements Serializable {

    private String name;
    private int id;

    public Company(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean legal;

    private User owner;
    private ArrayList<Truck> trucks;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
