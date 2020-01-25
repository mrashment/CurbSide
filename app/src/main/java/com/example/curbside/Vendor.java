package com.example.curbside;

public abstract class Vendor implements User {

    private User user;
    private String name;
    private String email;

    public Vendor(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public int getPermissions() {
        return this.user.getPermissions();
    }
    public void setPermissions(int i) {
        this.user.setPermissions(i);
    }

}
