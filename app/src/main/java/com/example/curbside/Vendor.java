package com.example.curbside;

public abstract class Vendor implements User {

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

    public String getName() {
        return this.user.getName();
    }
    public void setName(String name) {
        this.user.setName(name);
    }
    public String getEmail() {
        return this.user.getEmail();
    }
    public void setEmail(String email) {
        this.user.setEmail(email);
    }
    public int getPermissions() {
        return this.user.getPermissions();
    }
    public void setPermissions(int i) {
        this.user.setPermissions(i);
    }

}
