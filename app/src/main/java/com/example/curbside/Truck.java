package com.example.curbside;

import java.util.ArrayList;

public class Truck {

    private String name;
    private ArrayList<FoodMenu> menus;

    public Truck(String name) {
        this.name = name;
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
}
