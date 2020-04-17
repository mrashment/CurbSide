package com.example.curbside;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodItem implements Serializable {

    private int id;
    private String name;
    private String description;
    private double price;
    private ArrayList<FoodItem> items;
    private String item_type;
    private Boolean favorite;

    public FoodItem(int id, String name, String description, double price, String item_type, Boolean favorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.item_type = item_type;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName()  {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
