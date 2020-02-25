package com.example.curbside;

import java.util.ArrayList;

public class Truck {

    private String name;
    private Company company;
    private ArrayList<FoodMenu> menus;
    private String hours;

    public Truck(String name, Company company) {
        this.name = name;
        this.company = company;
        this.hours = "9:00am-9:00pm";
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
