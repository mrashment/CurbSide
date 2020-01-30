package com.example.curbside;

public abstract class Vendor implements User {

    private User user;

    public Vendor(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return this.user.getName();
    }

    @Override
    public void setName(String name) {
        this.user.setName(name);
    }

    @Override
    public String getEmail() {
        return this.user.getEmail();
    }

    @Override
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
