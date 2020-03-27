package com.example.curbside;

import java.util.ArrayList;

public abstract class Vendor extends User {

    private User user;


    public Vendor(User user) {
        this.user = user;
    }

    /**
     * turns on location for food truck, updating database
     *
     * @return 1 if successfully executed, -1 otherwise
     */
    public int broadcastTruckLocation() {
        int success = -1;

        return success;
    }

    @Override
    public String toString() {
        return user.toString();
    }

    @Override
    public int getId() {
        return user.getId();
    }

    @Override
    public void setId(int id) {
        user.setId(id);
    }

    @Override
    public Integer[] getFavIds() {
        return user.getFavIds();
    }

    @Override
    public void setFavIds(String[] stringIds) {
        user.setFavIds(stringIds);
    }

    @Override
    public ArrayList<Truck> getFavTrucks() {
        return user.getFavTrucks();
    }

    @Override
    public void setFavTrucks(ArrayList<Truck> favTrucks) {
        user.setFavTrucks(favTrucks);
    }

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public void setName(String name) {
        user.setName(name);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        user.setEmail(email);
    }

    @Override
    public int getRewards() {
        return user.getRewards();
    }

    @Override
    public void setRewards(int rewards) {
        user.setRewards(rewards);
    }

    @Override
    public int getPermissions() {
        return user.getPermissions();
    }

    @Override
    public void setPermissions(int permissions) {
        user.setPermissions(permissions);
    }

    @Override
    public int getCompanyID() {
        return user.getCompanyID();
    }

    @Override
    public void setCompanyID(int ID) {
        user.setCompanyID(ID);
    }
}
