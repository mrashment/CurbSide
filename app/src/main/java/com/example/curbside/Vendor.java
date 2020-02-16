package com.example.curbside;

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

}
