package com.example.curbside;

import android.view.Menu;

import java.util.ArrayList;

public class FoodMenu {

    private String name;
    private ArrayList<FoodItem> items;

    public FoodMenu(String name) {
        this.name = name;
    }
    public FoodMenu(FoodMenu menu) {
        this.items = menu.getItems();
    }

    public ArrayList<FoodItem> getItems() {
        return this.items;
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
    public void removeItem(FoodItem item) {
        items.remove(item);
    }
}
