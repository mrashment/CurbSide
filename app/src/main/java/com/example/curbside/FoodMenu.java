package com.example.curbside;

import java.util.ArrayList;

public class FoodMenu {

    private String name;
    private ArrayList<FoodItem> items;

    public FoodMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addItem(FoodItem item) {
        items.add(item);
    }
}
